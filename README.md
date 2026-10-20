<div align="center">

# 🔐 AccessAudit

### 🛡️ Governança de Identidades, Controle de Acessos e Auditoria

**Uma aplicação para organizar solicitações de acesso, aprovações, revisões periódicas e rastreabilidade em ambientes de TI.**

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge\&logo=openjdk\&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring\_Boot-3.5.5-6DB33F?style=for-the-badge\&logo=springboot\&logoColor=white" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/Spring\_Security-6DB33F?style=for-the-badge\&logo=springsecurity\&logoColor=white" alt="Spring Security" />
  <img src="https://img.shields.io/badge/PostgreSQL-16-4169E1?style=for-the-badge\&logo=postgresql\&logoColor=white" alt="PostgreSQL" />
  <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge\&logo=html5\&logoColor=white" alt="HTML5" />
  <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge\&logo=css3\&logoColor=white" alt="CSS3" />
  <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge\&logo=javascript\&logoColor=black" alt="JavaScript" />
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge\&logo=docker\&logoColor=white" alt="Docker" />
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge\&logo=apachemaven\&logoColor=white" alt="Maven" />
</p>

</div>

\---

## 📌 Sobre o projeto

O **AccessAudit** é uma aplicação desenvolvida em Java para demonstrar conceitos de **Identity Governance and Administration (IGA)** e Governança de TI. A plataforma centraliza o catálogo de usuários, sistemas e perfis de acesso, registra solicitações e decisões e mantém uma trilha de auditoria para apoiar o acompanhamento dos acessos.

O projeto foi desenvolvido para portfólio e simula processos comuns em equipes de TI, como concessão, alteração e revogação de acessos, aprovação por responsáveis e revisão periódica de permissões.

## 🎯 Problema que resolve

Em muitos ambientes, solicitações e permissões podem ficar espalhadas por planilhas, mensagens e processos manuais. Isso dificulta saber quem solicitou um acesso, quem aprovou, quais permissões estão ativas e quando elas precisam ser revisadas.

O AccessAudit reúne essas informações em um único lugar, oferecendo mais organização, visibilidade e rastreabilidade do processo.

## ✨ Funcionalidades

### 📊 Dashboard executivo

* Visão geral das solicitações e pendências.
* Indicadores de acessos ativos/aprovados e solicitações rejeitadas.
* Alertas de revisões de acesso vencidas.
* Solicitações recentes e atividades de auditoria.

### 🗂️ Gestão de catálogo

* Cadastro e manutenção de usuários do diretório.
* Cadastro de sistemas e responsáveis.
* Cadastro de perfis, permissões, criticidade e status.
* Desativação lógica de registros para preservar o histórico.

### 📝 Solicitações e aprovações

* Solicitações de concessão, alteração e revogação de acesso.
* Registro de justificativas.
* Aprovação ou rejeição por usuários autorizados.
* Atualização do inventário de acessos após decisões aprovadas.
* Registro de eventos relevantes para auditoria.

### 🔍 Revisão de acessos

* Identificação de acessos com revisão vencida.
* Registro da conclusão da revisão.
* Renovação do ciclo de revisão após sua conclusão.

### 🔑 Autenticação e autorização

* Autenticação por sessão HTTP.
* Controle de permissões por papel: `ADMIN`, `APPROVER` e `REQUESTER`.
* Validação de ações autorizadas no backend.

### 🧾 Auditoria e relatórios

* Histórico de eventos relacionados a solicitações e catálogos.
* Consulta à trilha de auditoria.
* Exportação dos eventos de auditoria para CSV pela interface.

## 🧰 Tecnologias utilizadas

|Tecnologia|Utilização|
|-|-|
|☕ Java 21|Linguagem principal|
|🍃 Spring Boot 3.5.5|Estrutura da aplicação Backend|
|🌐 Spring Web|Construção da API REST|
|🔐 Spring Security|Autenticação e autorização|
|🗃️ Spring Data JPA / Hibernate|Persistência e mapeamento de dados|
|🐘 PostgreSQL 16|Banco de dados principal|
|🎨 HTML5, CSS3 e JavaScript|Interface web|
|🐳 Docker e Docker Compose|Containerização e execução local|
|🧪 H2, JUnit e MockMvc|Apoio aos testes automatizados|
|📦 Maven|Gerenciamento de dependências e build|

## 🏗️ Arquitetura

A aplicação segue uma separação por responsabilidades:

* **API:** recebe as requisições HTTP e disponibiliza os endpoints.
* **Services:** concentra as regras de negócio e os fluxos de acesso.
* **Models:** representa as entidades do domínio.
* **Repositories:** realiza as operações de persistência.
* **Security:** configura autenticação, sessão e autorização.
* **Frontend:** fornece a interface web para interação com a aplicação.

## 📁 Estrutura do projeto

```text
accessaudit/
├── Dockerfile
├── docker-compose.yml
├── INICIAR-WINDOWS.bat
├── pom.xml
├── scripts/
│   └── smoke-test.py
└── src/
    ├── main/
    │   ├── java/com/accessaudit/
    │   │   ├── api/          # Controllers e endpoints REST
    │   │   ├── model/        # Entidades da aplicação
    │   │   ├── repo/         # Repositórios de dados
    │   │   ├── security/     # Configurações de segurança
    │   │   └── service/      # Regras de negócio
    │   └── resources/
    │       ├── application.properties
    │       └── static/       # HTML, CSS e JavaScript
    └── test/
        ├── java/            # Testes automatizados
        └── resources/       # Configuração dos testes
```

## 🚀 Como executar

### 🐳 Opção 1 — Windows com Docker (recomendada)

**Pré-requisitos:** Docker Desktop instalado e em execução. É necessária conexão com a internet para baixar imagens e construir a aplicação na primeira execução.

1. Baixe ou clone este repositório.
2. Extraia os arquivos, se necessário, e abra a pasta do projeto.
3. Execute `INICIAR-WINDOWS.bat` ou abra um terminal na raiz e rode:

```bash
docker compose up --build
```

4. Aguarde a inicialização da aplicação e do banco de dados.
5. Acesse [http://localhost:8080](http://localhost:8080).

O PostgreSQL fica disponível na porta `5433` do computador host, enquanto a aplicação se conecta ao banco pela porta `5432` dentro da rede Docker.

Para encerrar, pressione `Ctrl+C` e execute:

```bash
docker compose down
```

> \*\*Atenção:\*\* `docker compose down -v` também remove o volume do banco de dados e apaga permanentemente os dados locais armazenados nele.

### 💻 Opção 2 — Execução local sem Docker

**Pré-requisitos:** JDK 21, Maven 3.9+ e PostgreSQL 16.

Configure o banco de dados e as variáveis de ambiente abaixo conforme seu ambiente:

* `SPRING\_DATASOURCE\_URL`
* `SPRING\_DATASOURCE\_USERNAME`
* `SPRING\_DATASOURCE\_PASSWORD`

Na raiz do projeto, execute:

```bash
mvn test
mvn spring-boot:run
```

## 🧪 Contas de demonstração

|Perfil|Usuário|Senha|
|-|-|-|
|Administrador|`admin`|`AccessAudit@2026`|
|Aprovador|`aprovador`|`Aprova@2026`|
|Solicitante|`solicitante`|`Solicita@2026`|

As contas demonstrativas são criadas automaticamente quando ainda não há contas cadastradas.

> \*\*Segurança:\*\* essas credenciais são destinadas somente ao ambiente local de demonstração. Não exponha a aplicação publicamente com as credenciais padrão.

## 👥 Perfis de acesso

|Papel|Responsabilidades principais|
|-|-|
|`ADMIN`|Gerenciar usuários do diretório, sistemas e perfis; aprovar ou rejeitar solicitações; revogar e revisar acessos.|
|`APPROVER`|Aprovar ou rejeitar solicitações, revogar acessos e realizar revisões.|
|`REQUESTER`|Consultar informações disponíveis e registrar solicitações de acesso.|

## ✅ Testes

Para executar a suíte de testes automatizados:

```bash
mvn test
```

O projeto inclui testes para inicialização, autenticação, autorização, APIs de acesso, decisões, revisões, catálogos, auditoria e indicadores do dashboard.

Com a aplicação em execução, também é possível rodar o teste de fumaça do fluxo principal:

```powershell
python .\\scripts\\smoke-test.py
```

Esse script cria registros de teste no banco, aprova uma solicitação, consulta a auditoria e conclui uma revisão. Os registros gerados podem precisar ser removidos manualmente depois.

## 🔌 Principais endpoints da API

|Área|Endpoints de exemplo|
|-|-|
|Autenticação|`POST /api/auth/login`, `GET /api/auth/me`, `POST /api/auth/logout`|
|Dashboard|`GET /api/dashboard`|
|Solicitações|`GET /api/requests`, `POST /api/requests`, `POST /api/requests/{id}/decision`|
|Acessos e revisões|`GET /api/accesses`, `GET /api/reviews`, `POST /api/accesses/{id}/review`|
|Catálogos|`/api/users`, `/api/systems`, `/api/profiles`|
|Auditoria|`GET /api/audit` e endpoints de auditoria de solicitações|

Consulte os controllers em `src/main/java/com/accessaudit/api/` para verificar os detalhes de cada endpoint e seus parâmetros.

## ⚠️ Limitações e segurança

O AccessAudit é um **projeto de portfólio para execução local**, não uma solução corporativa pronta para produção. Nesta versão, o sistema demonstra fluxos de governança e auditoria, mas **não provisiona nem remove permissões diretamente** em Active Directory, Microsoft Entra ID, bancos de dados, GitLab ou outros sistemas externos.

Antes de qualquer implantação pública ou uso real, é necessário, entre outras medidas:

* substituir as credenciais e segredos de demonstração;
* configurar HTTPS e proteção CSRF adequada;
* limitar tentativas de autenticação e revisar a gestão de sessões;
* adotar migrações controladas do banco de dados;
* revisar permissões, logs, tratamento de erros e configurações de segurança;
* executar testes de segurança e validar os requisitos do ambiente.

## 🎓 Competências demonstradas

* Desenvolvimento de APIs REST com Java e Spring Boot.
* Organização de código por camadas e responsabilidades.
* Modelagem de dados e persistência com PostgreSQL/JPA.
* Autenticação e autorização baseada em papéis.
* Implementação de regras de negócio para solicitações e aprovações.
* Registro de eventos de auditoria e revisão periódica de acessos.
* Containerização e testes automatizados.



* ## \## 👨‍💻 Autor
* ## 
* ## \*\*Matheus Marks\*\*
* ## 
* ## Projeto desenvolvido com foco em Governança de TI, gestão de identidades, controle de acessos e auditoria de permissões.
* ## 
* ## O AccessAudit busca centralizar e organizar o ciclo de vida das solicitações de acesso, incluindo aprovações, revisões periódicas e rastreabilidade das ações, contribuindo para a padronização de processos e o fortalecimento dos controles de segurança da informação.



* ## \---
* ## 
* ## \## ⭐ Apoie o projeto

## 

* ## Se este projeto foi útil para você, considere deixar uma ⭐ no repositório. 

