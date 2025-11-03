# 🗓️ AppEventos

Aplicação desenvolvida em **Java (JavaFX)** para o gerenciamento de eventos.  
O sistema permite **cadastrar, visualizar, editar, excluir e pesquisar eventos**, além de manter informações sobre **cidades e estados** relacionados a cada evento.

---

## 🧱 Estrutura do Projeto

O projeto segue o padrão **MVC (Model–View–Controller)** com o uso do **DAO (Data Access Object)** para acesso ao banco de dados PostgreSQL.

---

AppEventos/

├── src/

│   └── main/

│       ├── java/br/unicentro/appeventos/

│       │   ├── controller/        # Camada de controle (JavaFX Controllers)

│       │   ├── dao/               # Camada de acesso ao banco (DAOs e interfaces)

│       │   ├── main/              # Classe Main para inicialização

│       │   └── model/             # Modelos de dados (Entidades)

│       └── resources/br/unicentro/appeventos/view/

│           ├── TelaCadastrarEvento.fxml

│           ├── TelaEditarEvento.fxml

│           ├── TelaInicial.fxml

│           ├── TelaPesquisarEvento.fxml

│           └── TelaVisualizarEvento.fxml



---

## 🧩 Funcionalidades

- ✅ **Cadastrar Evento:** insere um novo evento informando nome, descrição, data, preço e cidade.  
- 🔍 **Pesquisar Evento:** permite consultar eventos existentes.  
- ✏️ **Editar Evento:** atualiza dados de um evento já cadastrado.  
- ❌ **Excluir Evento:** remove um evento do banco de dados.  
- 🏙️ **Gerenciar Cidades e Estados:** usados como base para o cadastro de eventos.

---

## 🗃️ Banco de Dados

O sistema utiliza **PostgreSQL**.  
As tabelas principais são:

- `estado` – contém informações dos estados brasileiros.  
- `cidade` – relaciona-se a um estado e armazena dados de população e área.  
- `evento` – armazena os eventos, com vínculo a uma cidade.

### Exemplo simplificado das relações:


### Script de Criação (simplificado)
```sql

-- 1. Criação do banco de dados 
-- CREATE DATABASE eventos;

-- =====================================================
-- 2. TABELA ESTADO
-- =====================================================

CREATE SEQUENCE IF NOT EXISTS estado_estadoid_seq START 1;

CREATE TABLE IF NOT EXISTS public.estado (
    estadoid INTEGER NOT NULL DEFAULT nextval('estado_estadoid_seq'),
    nome VARCHAR(100) NOT NULL,
    sigla CHAR(2) NOT NULL,
    regiao VARCHAR(50),
    CONSTRAINT estado_pkey PRIMARY KEY (estadoid),
    CONSTRAINT estado_nome_key UNIQUE (nome),
    CONSTRAINT estado_sigla_key UNIQUE (sigla)
);

ALTER TABLE IF EXISTS public.estado OWNER TO postgres;

-- =====================================================
-- 3. TABELA CIDADE
-- =====================================================

CREATE SEQUENCE IF NOT EXISTS cidade_cidadeid_seq START 1;

CREATE TABLE IF NOT EXISTS public.cidade (
    cidadeid INTEGER NOT NULL DEFAULT nextval('cidade_cidadeid_seq'),
    nome VARCHAR(100) NOT NULL,
    populacao INTEGER,
    area NUMERIC(10,2),
    estadoid INTEGER NOT NULL,
    CONSTRAINT cidade_pkey PRIMARY KEY (cidadeid),
    CONSTRAINT cidade_nome_key UNIQUE (nome),
    CONSTRAINT cidade_estadoid_fkey FOREIGN KEY (estadoid)
        REFERENCES public.estado (estadoid)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

ALTER TABLE IF EXISTS public.cidade OWNER TO postgres;

-- =====================================================
-- 4. TABELA EVENTO
-- =====================================================

CREATE SEQUENCE IF NOT EXISTS evento_eventoid_seq START 1;

CREATE TABLE IF NOT EXISTS public.evento (
    eventoid INTEGER NOT NULL DEFAULT nextval('evento_eventoid_seq'),
    nome VARCHAR(150) NOT NULL,
    descricao TEXT,
    datainicio DATE,
    datafim DATE,
    precoingresso NUMERIC(10,2),
    cidadeid INTEGER NOT NULL,
    CONSTRAINT evento_pkey PRIMARY KEY (eventoid),
    CONSTRAINT evento_nome_key UNIQUE (nome),
    CONSTRAINT evento_cidadeid_fkey FOREIGN KEY (cidadeid)
        REFERENCES public.cidade (cidadeid)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

ALTER TABLE IF EXISTS public.evento OWNER TO postgres;

```

## ⚙️ Tecnologias Utilizadas
- Java 
- JavaFX (interface gráfica)
- PostgreSQL (banco de dados)
- JDBC (conexão com o banco)
- Padrão MVC + DAO

## 🧠 Arquitetura
Model:
  - Contém as classes Evento, Cidade e Estado.

DAO:
  - Define interfaces (IEvento, ICidade) e implementações (EventoDAO, CidadeDAO)
    responsáveis pelas operações no banco de dados.

Controller:
  - Manipula as telas FXML e coordena as ações do usuário.

View:
  - Arquivos .fxml que definem as interfaces gráficas.

## ▶️ Execução do Projeto
1. Configure o PostgreSQL e execute o script de criação das tabelas.
2. Ajuste os parâmetros de conexão no arquivo Conexao.java (usuário, senha e nome do banco).
3. Compile e execute a classe Main.java.
4. Utilize as telas para cadastrar, visualizar, editar e excluir eventos.


## 👨‍💻 Autores
Projeto desenvolvido por:
- Ruan Pablo Martins
- Guilherme Roberto Nodochetcko

Disciplina: Programação Orientada a Objetos II – UNICENTRO
Ano: 2025


