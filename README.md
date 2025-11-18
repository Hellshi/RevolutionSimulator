# 👥 Sistema de Gerenciamento de Usuários (JavaFX + MVC)
Diagrama de Classes
<img width="2951" height="2032" alt="UpdatedDiagram drawio" src="https://github.com/user-attachments/assets/e2a428ea-7f2b-4041-9161-eb2961e0af87" />

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-25.0.1-blue?style=for-the-badge)

Um sistema completo de CRUD (Create, Read, Update, Delete) para gerenciamento de funcionários, desenvolvido para praticar conceitos de **Orientação a Objetos**, **Arquitetura MVC** e interfaces gráficas com **JavaFX**.

O projeto utiliza persistência de dados em arquivos **JSON** locais, sem necessidade de banco de dados externo.

---

## 🚀 Funcionalidades

- **Cadastro:** Adiciona usuários com Nome, Salário e Cargo.
- **Listagem:** Tabela com formatação monetária automática (R$).
- **Edição:** Permite alterar dados de usuários existentes.
- **Exclusão:** Botão de remoção integrado na linha da tabela.
- **Persistência:** Dados salvos automaticamente em `src/Model/DB/User.json`.

---

## 📚 Dependências (Libs)

Este projeto não utiliza gerenciadores como Maven ou Gradle. As bibliotecas devem ser adicionadas manualmente ao Classpath da IDE.

**Versão do SDK:** JavaFX 25.0.1

Arquivos `.jar` obrigatórios para o funcionamento:

- `javafx.base.jar`
- `javafx.controls.jar`
- `javafx.fxml.jar`
- `javafx.graphics.jar`

> **Nota:** Estas bibliotecas fazem parte do [JavaFX SDK (GluonHQ)](https://gluonhq.com/products/javafx/). Certifique-se de baixar a versão compatível com seu Sistema Operacional (Windows/Linux/Mac).

---

## 🛠️ Tecnologias Utilizadas

- **Java JDK 25.0.1**
- **JavaFX SDK 25.0.1**
- **IntelliJ IDEA**

---

## 📦 Estrutura do Projeto

```text
src/
├── Controller/
│   ├── Users/          # Regras de Negócio e gerenciamento do JSON
│   └── View/           # Controle da Tela (Interação com botões/tabela)
├── Model/
│   ├── Data/           # Manipulação direta do arquivo (Storage)
│   ├── Repository/     # Lógica de CRUD genérica
│   └── User/           # Entidades e Objetos
├── View/
│   └── user_view.fxml  # Interface Gráfica (XML)
├── Main.java           # Inicialização do JavaFX
└── Launcher.java       # Classe Wrapper para execução (fix modules)
```
