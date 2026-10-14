# AccessAudit — Identity Governance & Access Audit

Plataforma demonstrativa de governança de identidades e acessos (IGA), construída para portfólio. O sistema gerencia o catálogo de pessoas, sistemas e perfis, solicitações de acesso, decisões, revisões periódicas e trilha de auditoria.

> **Escopo e segurança:** este projeto é uma aplicação de portfólio para ambiente local. Possui autenticação de sessão e papéis para demonstrar autorização no servidor, mas ainda não é uma solução corporativa de IAM pronta para produção. Não provisiona permissões diretamente no Active Directory, Entra ID, bancos, GitLab ou outros sistemas. Antes de exposição externa, troque as credenciais de demonstração, habilite proteção CSRF, limite tentativas de login, configure HTTPS, migrações de banco, gestão de segredos e testes de segurança.

## Stack

- Java 21
- Spring Boot 3.5.5
- Spring Web, Spring Data JPA e Spring Security
- PostgreSQL 16
- HTML, CSS e JavaScript puro
- Docker Compose
- H2 e MockMvc para testes

## Funcionalidades

### 1. Dashboard executivo
- Solicitações totais e pendentes.
- Acessos aprovados/ativos e solicitações rejeitadas.
- Alertas de revisão de acesso vencida.
- Solicitações recentes, fila de atenção e atividade de auditoria.

### 2. Gestão de identidades e catálogo
- CRUD de usuários do diretório (nome, e-mail, departamento, cargo e status).
- CRUD de sistemas (código, descrição, responsável, criticidade e status).
- CRUD de perfis (sistema associado, permissões, risco e status).
- Desativação lógica para preservar histórico.
- Validação de e-mail único e código de sistema único.
- Solicitações vinculam perfil ativo ao sistema correto.

### 3. Fluxo de solicitação e aprovação
- Solicitações de concessão (`GRANT`), alteração (`CHANGE`) e revogação (`REVOKE`).
- Justificativa obrigatória; alteração exige perfil atual e novo perfil diferente.
- Aprovação/rejeição por usuário autenticado com papel `ADMIN` ou `APPROVER`.
- Rejeição exige justificativa com pelo menos cinco caracteres.
- A concessão aprovada cria um registro de acesso ativo no inventário.
- A alteração aprovada atualiza o perfil do acesso existente; a revogação aprovada desativa o acesso correspondente.
- Solicitações aprovadas programam revisão em 90 dias.
- A revogação e a revisão registram eventos de auditoria.

### 4. Revisões e auditoria
- Fila de revisões vencidas.
- Conclusão de revisão renova o ciclo para 90 dias.
- Eventos de criação, decisão, revogação, revisão e alterações nos catálogos.
- Exportação da trilha para CSV no frontend.

### 5. Autenticação e papéis
- Login com sessão HTTP no backend.
- `ADMIN`: gerencia usuários do diretório, sistemas e perfis; pode aprovar/rejeitar, revogar e revisar.
- `APPROVER`: pode aprovar/rejeitar, revogar e revisar.
- `REQUESTER`: pode consultar os dados e registrar solicitações.
- O responsável registrado para decisões/cadastros é obtido da sessão autenticada, não confiado ao nome enviado pelo navegador.

## Executar no Windows (recomendado)

Requisitos: Docker Desktop instalado e iniciado, conexão com a internet na primeira construção.

1. Extraia o ZIP.
2. Abra a pasta `accessaudit`.
3. Execute `INICIAR-WINDOWS.bat`, ou abra PowerShell nessa pasta e rode:

```powershell
docker compose up --build
```

4. Aguarde o build Maven e o banco ficarem prontos.
5. Acesse `http://localhost:8080`.

O PostgreSQL é exposto na porta `5433` do Windows para evitar conflito com instalações locais. Dentro do Compose, a aplicação se conecta ao serviço `db` na porta `5432`.

Parar os serviços: `Ctrl+C` e depois `docker compose down`. Para apagar também os dados locais do banco, use `docker compose down -v` — **isso remove permanentemente os dados desse volume**.

## Contas de demonstração

| Papel | Usuário | Senha |
|---|---|---|
| Administrador | `admin` | `AccessAudit@2026` |
| Aprovador | `aprovador` | `Aprova@2026` |
| Solicitante | `solicitante` | `Solicita@2026` |

As contas são criadas automaticamente apenas quando a tabela de contas está vazia. Elas são credenciais de demonstração, não use essas senhas em um ambiente público. O sistema não oferece uma tela de gestão de contas de login nem fluxo de recuperação de senha nesta versão.

## Executar sem Docker

Requisitos: JDK 21, Maven 3.9+ e PostgreSQL 16. Crie o banco `accessaudit`, usuário `accessaudit` e senha `accessaudit_dev`, ou configure as variáveis `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` e `SPRING_DATASOURCE_PASSWORD`.

```bash
mvn test
mvn spring-boot:run
```

Para desenvolvimento sem PostgreSQL, pode-se configurar H2 manualmente; a suíte de testes usa H2 em memória.

## Testes

Na raiz do projeto:

```bash
mvn test
```

Os testes incluem inicialização do contexto, login e sessão, proteção de endpoints, criação/aprovação/revisão, trilha de auditoria, catálogos e métricas do dashboard.

Com o sistema em execução, também é possível fazer um smoke test do fluxo básico:

```powershell
python .\scripts\smoke-test.py
```

O smoke test cria uma solicitação de teste, aprova, consulta auditoria e conclui uma revisão. Ele deixa registros de teste no banco; remova-os pela interface se desejar.

## Endpoints principais

### Sessão
- `POST /api/auth/login` — `{"username":"admin","password":"..."}`
- `GET /api/auth/me`
- `POST /api/auth/logout`

### Solicitações
- `GET /api/dashboard`
- `GET /api/requests`
- `POST /api/requests`
- `POST /api/requests/{id}/decision`
- `POST /api/requests/{id}/revoke`
- `GET /api/reviews` — acessos ativos com revisão vencida
- `GET /api/accesses` — inventário de acessos concedidos
- `POST /api/accesses/{id}/review`
- `GET /api/requests/{id}/audit`

### Catálogos
- `GET/POST /api/users`, `PUT/DELETE /api/users/{id}`
- `GET/POST /api/systems`, `PUT/DELETE /api/systems/{id}`
- `GET/POST /api/profiles`, `PUT/DELETE /api/profiles/{id}`
- `GET /api/audit`

`DELETE` nos catálogos é uma desativação lógica, para preservar a trilha e referências históricas.

## Estrutura

```text
accessaudit/
├── .editorconfig
├── .gitignore
├── .dockerignore
├── Dockerfile
├── docker-compose.yml
├── pom.xml
├── INICIAR-WINDOWS.bat
├── scripts/smoke-test.py
└── src/
    ├── main/java/com/accessaudit/
    │   ├── api/          # API REST e sessão
    │   ├── model/        # Entidades JPA
    │   ├── repo/         # Repositórios
    │   ├── security/     # Spring Security e sessão
    │   └── service/      # Regras de negócio
    ├── main/resources/static/ # Frontend
    └── test/             # Testes de integração
```
