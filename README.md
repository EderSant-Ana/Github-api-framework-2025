# 📚 GitHub API Automation Framework

Este projeto é um framework de automação de testes focado na API do GitHub, desenvolvido em **Java**. Ele utiliza o **Rest Assured** para chamadas HTTP e o **TestNG** para orquestração e gerenciamento de testes.

O foco desta solução é a **robustez e estabilidade**, utilizando ferramentas que se integram de forma direta com o ciclo de vida do Maven para evitar conflitos de diretório (como os que você enfrentou).

---

## 🔑 Tecnologias Principais

| Tecnologia | Função |
| :--- | :--- |
| **Java** | Linguagem de desenvolvimento. |
| **Maven** | Gerenciador de dependências e automação de build. |
| **TestNG** | Framework de testes, orquestração e anotações. |
| **Rest Assured** | Biblioteca para testes e validação de APIs REST (a mais aceita no mercado). |
| **ReportNG** | Gerador de relatórios HTML leve e estável (usado como substituto ao report nativo do TestNG). |

---

## ⚙️ Configuração e Dependências

### Pré-requisitos

Para rodar o projeto, você precisa ter instalado:

1.  **Java Development Kit (JDK) 11 ou superior**
2.  **Apache Maven** (configurado no PATH ou usando a instalação embutida do Eclipse)
3.  **Eclipse IDE** (com plugins TestNG e Maven, se aplicável).

### Notas sobre o ReportNG

A geração do relatório **ReportNG** é configurada diretamente no **`pom.xml`** através do **Maven Surefire Plugin**.

* Ele substitui o relatório padrão do TestNG por um HTML mais limpo.
* Essa configuração garante que o relatório seja sempre gerado corretamente, sem a necessidade de comandos adicionais ou configurações complexas de diretório (como o Allure CLI).

---

## 🚀 Como Rodar o Projeto

A execução é feita através do Maven, que lê o arquivo **`testng.xml`** localizado em `src/test/resources/`.

### 1. Executar os Testes

Você deve sempre executar o goal `clean test` para garantir um build limpo e a execução da sua suite.

**No Eclipse (Recomendado):**

1.  Clique com o botão direito no projeto (`github-api-framework`).
2.  Vá em **Run As** $\rightarrow$ **Maven Build...**
3.  No campo **Goals**, digite: `clean test`
4.  Clique em **Run**.

### 2. Visualizar o Relatório (ReportNG)

O relatório HTML é gerado automaticamente pelo Maven/ReportNG no diretório de saída dos testes.

1.  Após a execução, navegue até o diretório: **`target/surefire-reports/html/`**
2.  Abra o arquivo **`index.html`** no seu navegador web de preferência.

Este arquivo é o relatório final de execução, contendo o status de todos os testes (Passou/Falhou) e logs.

---

## 📁 Estrutura do Projeto

| Diretório/Arquivo | Conteúdo |
| :--- | :--- |
| `src/test/java/` | Classes de teste (e.g., `RepositoryTests.java`) e a lógica do cliente de API. |
| `src/test/resources/` | Arquivo **`testng.xml`** (define a suite de testes) e arquivos de dados. |
| `pom.xml` | Gerencia todas as dependências e a configuração do **ReportNG** via Surefire Plugin. |
| `target/surefire-reports/html/` | **Local onde o relatório final (`index.html`) do ReportNG é gerado.** |
