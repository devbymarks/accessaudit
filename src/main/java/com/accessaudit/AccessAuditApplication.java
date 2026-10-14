package com.accessaudit;

import com.accessaudit.model.AccessProfile;
import com.accessaudit.model.AdminAccount;
import com.accessaudit.repo.AdminAccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.accessaudit.model.DirectoryUser;
import com.accessaudit.model.ManagedSystem;
import com.accessaudit.repo.AccessProfileRepository;
import com.accessaudit.repo.DirectoryUserRepository;
import com.accessaudit.repo.ManagedSystemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccessAuditApplication {
    public static void main(String[] args) { SpringApplication.run(AccessAuditApplication.class, args); }

    @Bean CommandLineRunner seedCatalogs(DirectoryUserRepository users, ManagedSystemRepository systems, AccessProfileRepository profiles, AdminAccountRepository accounts, PasswordEncoder encoder) {
        return args -> {
            if (accounts.count() == 0) {
                accounts.save(new AdminAccount("admin", encoder.encode("AccessAudit@2026"), "Administrador AccessAudit", "admin@empresa.demo", "ADMIN", true));
                accounts.save(new AdminAccount("aprovador", encoder.encode("Aprova@2026"), "Carla Mendes", "carla.mendes@empresa.demo", "APPROVER", true));
                accounts.save(new AdminAccount("solicitante", encoder.encode("Solicita@2026"), "Ana Souza", "ana.souza@empresa.demo", "REQUESTER", true));
            }
            if (users.count() == 0) {
                users.save(new DirectoryUser("Administrador AccessAudit", "admin@empresa.demo", "Segurança", "Administrador de Plataforma", true));
                users.save(new DirectoryUser("Ana Souza", "ana.souza@empresa.demo", "Tecnologia", "Analista de Sistemas", true));
                users.save(new DirectoryUser("Bruno Lima", "bruno.lima@empresa.demo", "Infraestrutura", "Analista de Infraestrutura", true));
                users.save(new DirectoryUser("Carla Mendes", "carla.mendes@empresa.demo", "Segurança", "Analista de Segurança", true));
            }
            if (systems.count() == 0) {
                systems.save(new ManagedSystem("GitLab", "GITLAB", "Repositórios e pipelines de desenvolvimento", "Engenharia", "ALTO", true));
                systems.save(new ManagedSystem("PostgreSQL BI", "PG-BI", "Banco analítico de relatórios", "Dados", "CRÍTICO", true));
                systems.save(new ManagedSystem("VPN Corporativa", "VPN", "Acesso remoto à rede corporativa", "Infraestrutura", "CRÍTICO", true));
                systems.save(new ManagedSystem("Service Desk", "SDESK", "Gestão de chamados e ativos", "Operações", "MÉDIO", true));
            }
            if (profiles.count() == 0) {
                profiles.save(new AccessProfile("Leitor", "Consulta sem alteração de dados", "PostgreSQL BI", "SELECT, VIEW", "BAIXO", true));
                profiles.save(new AccessProfile("Desenvolvedor", "Acesso a repositórios e pipelines", "GitLab", "REPOSITORY_READ, REPOSITORY_WRITE, PIPELINE_RUN", "MÉDIO", true));
                profiles.save(new AccessProfile("Administrador", "Administração completa do sistema", "GitLab", "ADMIN, USER_MANAGEMENT, SETTINGS", "CRÍTICO", true));
                profiles.save(new AccessProfile("Acesso remoto", "Conexão VPN corporativa", "VPN Corporativa", "VPN_CONNECT", "ALTO", true));
                profiles.save(new AccessProfile("Agente", "Atendimento de chamados", "Service Desk", "TICKET_READ, TICKET_UPDATE", "BAIXO", true));
            }
        };
    }
}
