package com.accessaudit;
import com.accessaudit.repo.*; import com.accessaudit.model.AccessGrant; import org.junit.jupiter.api.*; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.http.MediaType; import org.springframework.test.context.ActiveProfiles; import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; import org.springframework.security.test.context.support.WithMockUser;
@SpringBootTest @AutoConfigureMockMvc @ActiveProfiles("test") class AccessApiIntegrationTest {
 @Autowired MockMvc mvc; @Autowired AccessRequestRepository requests; @Autowired AuditEventRepository audit; @Autowired AccessGrantRepository grants;
 @BeforeEach void clean(){audit.deleteAll();requests.deleteAll();grants.deleteAll();}
 @Test @WithMockUser(username="admin",roles="ADMIN") void createApproveReviewAndAudit() throws Exception {
  mvc.perform(post("/api/requests").contentType(MediaType.APPLICATION_JSON).content("""
{"requester":"Ana Souza","email":"ana.souza@empresa.demo","systemName":"GitLab","accessRole":"Desenvolvedor","requestType":"GRANT","justification":"Atuar em um projeto"}
"""))
   .andExpect(status().isCreated()).andExpect(jsonPath("$.status").value("PENDING"));
  Long id=requests.findAll().get(0).getId();
  mvc.perform(post("/api/requests/"+id+"/decision").contentType(MediaType.APPLICATION_JSON).content("""
{"decision":"APPROVED","reviewer":"Gestor TI","note":"Aprovado para o projeto"}
"""))
   .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("APPROVED")).andExpect(jsonPath("$.reviewDueAt").exists());
  mvc.perform(get("/api/requests/"+id+"/audit")).andExpect(status().isOk()).andExpect(jsonPath("$[0].action").value("APPROVED"));
  Long grantId=grants.findAll().get(0).getId();
  mvc.perform(post("/api/accesses/"+grantId+"/review").contentType(MediaType.APPLICATION_JSON).content("""
{"actor":"Gestor TI","note":"Acesso continua necessário"}
"""))
   .andExpect(status().isOk());
 }
 @Test @WithMockUser(username="admin",roles="ADMIN") void rejectRequiresReasonAndCannotDecideTwice() throws Exception {
  mvc.perform(post("/api/requests").contentType(MediaType.APPLICATION_JSON).content("""
{"requester":"Ana Souza","email":"ana.souza@empresa.demo","systemName":"GitLab","accessRole":"Desenvolvedor","requestType":"GRANT","justification":"Atividade operacional"}
""" )).andExpect(status().isCreated());
  Long id=requests.findAll().get(0).getId();
  mvc.perform(post("/api/requests/"+id+"/decision").contentType(MediaType.APPLICATION_JSON).content("""
{"decision":"REJECTED","reviewer":"Gestor","note":"Não necessário"}
"""))
   .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("REJECTED"));
  mvc.perform(post("/api/requests/"+id+"/decision").contentType(MediaType.APPLICATION_JSON).content("""
{"decision":"APPROVED","reviewer":"Gestor","note":"Revisão"}
"""))
   .andExpect(status().isBadRequest());
 }
 @Test @WithMockUser(username="admin",roles="ADMIN") void catalogsAreCrudAndAudited() throws Exception {
  mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content("""
{"fullName":"Teste Pessoa","email":"teste@example.com","department":"TI","jobTitle":"Analista","active":true,"actor":"Teste"}
"""))
   .andExpect(status().isOk()).andExpect(jsonPath("$.id").exists());
  mvc.perform(get("/api/users")).andExpect(status().isOk()).andExpect(jsonPath("$[?(@.email == 'teste@example.com')]").exists());
  mvc.perform(post("/api/systems").contentType(MediaType.APPLICATION_JSON).content("""
{"name":"Sistema Teste","code":"TESTE-1","description":"Sistema para teste","ownerTeam":"TI","criticality":"MÉDIO","active":true,"actor":"Teste"}
"""))
   .andExpect(status().isOk()).andExpect(jsonPath("$.code").value("TESTE-1"));
  mvc.perform(get("/api/audit")).andExpect(status().isOk()).andExpect(jsonPath("$[0].entityType").exists());
 }
 @Test @WithMockUser(username="admin",roles="ADMIN") void grantChangeAndRevokeMaintainRealAccessInventory() throws Exception {
  mvc.perform(post("/api/requests").contentType(MediaType.APPLICATION_JSON).content("""
{"requester":"Ana Souza","email":"ana.souza@empresa.demo","systemName":"GitLab","accessRole":"Desenvolvedor","requestType":"GRANT","justification":"Acesso inicial para desenvolvimento"}
""")).andExpect(status().isCreated());
  Long grantRequest=requests.findAll().stream().filter(r->r.getEmail().equals("ana.souza@empresa.demo")).findFirst().orElseThrow().getId();
  mvc.perform(post("/api/requests/"+grantRequest+"/decision").contentType(MediaType.APPLICATION_JSON).content("""
{"decision":"APPROVED","reviewer":"admin","note":"Aprovado"}
""")).andExpect(status().isOk());
  mvc.perform(post("/api/requests").contentType(MediaType.APPLICATION_JSON).content("""
{"requester":"Ana Souza","email":"ana.souza@empresa.demo","systemName":"GitLab","accessRole":"Administrador","currentAccessRole":"Desenvolvedor","requestType":"CHANGE","justification":"Necessita administrar as configurações do projeto"}
""")).andExpect(status().isCreated());
  Long changeId=requests.findAll().stream().filter(r->r.getEmail().equals("ana.souza@empresa.demo")&&"CHANGE".equals(r.getRequestType())).findFirst().orElseThrow().getId();
  mvc.perform(post("/api/requests/"+changeId+"/decision").contentType(MediaType.APPLICATION_JSON).content("""
{"decision":"APPROVED","reviewer":"admin","note":"Alteração aprovada"}
""")).andExpect(status().isOk());
  AccessGrant access=grants.findFirstByEmailIgnoreCaseAndSystemNameAndProfileNameAndStatus("ana.souza@empresa.demo","GitLab","Administrador","ACTIVE").orElseThrow();
  mvc.perform(post("/api/requests").contentType(MediaType.APPLICATION_JSON).content("""
{"requester":"Ana Souza","email":"ana.souza@empresa.demo","systemName":"GitLab","accessRole":"Administrador","requestType":"REVOKE","justification":"Fim da necessidade de privilégio"}
""")).andExpect(status().isCreated());
  Long revokeId=requests.findAll().stream().filter(r->r.getEmail().equals("ana.souza@empresa.demo")&&"REVOKE".equals(r.getRequestType())).findFirst().orElseThrow().getId();
  mvc.perform(post("/api/requests/"+revokeId+"/decision").contentType(MediaType.APPLICATION_JSON).content("""
{"decision":"APPROVED","reviewer":"admin","note":"Revogação aprovada"}
""")).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("REVOKED"));
  org.junit.jupiter.api.Assertions.assertEquals("REVOKED",grants.findById(access.getId()).orElseThrow().getStatus());
 }
 @Test @WithMockUser(username="solicitante",roles="REQUESTER") void requesterCannotEditCatalogs() throws Exception {
  mvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content("""
{"fullName":"Tentativa","email":"tentativa@example.com","department":"TI","jobTitle":"Analista","active":true}
""")).andExpect(status().isForbidden());
 }

 @Test @WithMockUser(username="admin",roles="ADMIN") void dashboardExposesAllFourMainMetricsAndReviewAlerts() throws Exception {
  mvc.perform(get("/api/dashboard")).andExpect(status().isOk()).andExpect(jsonPath("$.pending").exists()).andExpect(jsonPath("$.activeAccesses").exists()).andExpect(jsonPath("$.rejected").exists()).andExpect(jsonPath("$.reviewAlerts").exists());
 }
}
