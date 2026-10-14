package com.accessaudit;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.http.MediaType; import org.springframework.mock.web.MockHttpSession; import org.springframework.test.context.ActiveProfiles; import org.springframework.test.web.servlet.MockMvc; import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc @ActiveProfiles("test") class AuthApiIntegrationTest {
 @Autowired MockMvc mvc;
 @Test void loginCreatesAuthenticatedSession() throws Exception {
  MvcResult result=mvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content("""
{"username":"admin","password":"AccessAudit@2026"}
""")).andExpect(status().isOk()).andExpect(jsonPath("$.role").value("ADMIN")).andReturn();
  MockHttpSession session=(MockHttpSession)result.getRequest().getSession(false);
  mvc.perform(get("/api/auth/me").session(session)).andExpect(status().isOk()).andExpect(jsonPath("$.username").value("admin"));
 }
 @Test void protectedEndpointsRequireLogin() throws Exception { mvc.perform(get("/api/dashboard")).andExpect(status().isUnauthorized()); }
}
