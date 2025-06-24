# 📱 Agenda de Contatos em Java

Aplicação desktop para gerenciamento de contatos telefônicos, desenvolvida em Java com interface gráfica Swing, utilizando banco de dados MySQL para persistência. O projeto aplica conceitos de Orientação a Objetos, padrão MVC, DAO e operações CRUD (Create, Read, Update, Delete).

---

## Tecnologias Utilizadas

- Java 17+
- Swing (Interface gráfica)
- MySQL (Banco de dados)
- JDBC (Conexão com banco)
- IntelliJ IDEA
- Padrões MVC, DAO e CRUD

---

## Funcionalidades

- Adicionar contatos (nome, telefone, e-mail)
- Listar todos os contatos cadastrados
- Editar contatos existentes
- Remover múltiplos contatos com seleção por checkbox
- Interface com tema escuro, inspirada em agendas de celular Samsung
- Atualização automática da lista após operações

---

## Estrutura do Projeto

src/

├── controller/

│ └── ContatoController.java # Controla a lógica entre View e DAO

├── dao/

│ ├── ConexaoDB.java # Gerencia conexão com MySQL

│ └── ContatoDAO.java # CRUD com banco de dados

├── model/

│ └── Contato.java # Representa o contato (modelo)

├── view/

│ └── AgendaView.java # Interface gráfica Swing

└── Main.java # Inicializa aplicação

---

## Explicação das Classes

- **Contato.java:**  
  Representa a entidade contato com atributos `id`, `nome`, `telefone` e `email`.

- **ConexaoDB.java:**  
  Responsável por abrir conexão com o banco MySQL usando JDBC.

- **ContatoDAO.java:**  
  Realiza as operações CRUD no banco de dados, utilizando PreparedStatement para segurança.

- **ContatoController.java:**  
  Faz a mediação entre a interface e a camada DAO, aplicando regras de negócio.

- **AgendaView.java:**  
  Interface gráfica construída com Swing, apresenta os contatos em uma tabela com checkbox, campos para adicionar/editar, e botões para as operações.

- **Main.java:**  
  Classe principal que inicia a interface da aplicação.

---

## Operações CRUD

| Operação | Método Controller     | Descrição da Query SQL                            |
| -------- | -------------------- | ------------------------------------------------ |
| Create   | `adicionar(Contato)` | `INSERT INTO contatos (nome, telefone, email) VALUES (?, ?, ?)` |
| Read     | `listar()`           | `SELECT * FROM contatos ORDER BY nome`           |
| Update   | `atualizar(Contato)` | `UPDATE contatos SET nome=?, telefone=?, email=? WHERE id=?` |
| Delete   | `remover(int id)`    | `DELETE FROM contatos WHERE id=?`                 |

Desenvolvido por Júlia Carlos e Daniel Pereira com ☕ e 💻.
