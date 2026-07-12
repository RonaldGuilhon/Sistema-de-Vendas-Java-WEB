-- ============================================
-- Sistema de Vendas - Script de Criação do Banco
-- Banco: PostgreSQL
-- ============================================

-- Criar banco de dados
-- DROP DATABASE IF EXISTS vendas;
CREATE DATABASE vendas
    WITH OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TEMPLATE = template0;

\c vendas

-- ============================================
-- Tabela: tb_fornecedores
-- ============================================
CREATE SEQUENCE IF NOT EXISTS tb_fornecedores_for_codigo_seq;

CREATE TABLE tb_fornecedores (
    for_codigo      BIGINT NOT NULL DEFAULT nextval('tb_fornecedores_for_codigo_seq'),
    for_descricao   VARCHAR(50) NOT NULL,
    CONSTRAINT pk_fornecedores PRIMARY KEY (for_codigo)
);

-- ============================================
-- Tabela: tb_funcionarios
-- ============================================
CREATE SEQUENCE IF NOT EXISTS tb_funcionarios_fun_codigo_seq;

CREATE TABLE tb_funcionarios (
    fun_codigo   BIGINT NOT NULL DEFAULT nextval('tb_funcionarios_fun_codigo_seq'),
    fun_nome     VARCHAR(50) NOT NULL,
    fun_cpf      VARCHAR(14) NOT NULL UNIQUE,
    fun_senha    VARCHAR(64) NOT NULL,
    fun_funcao   VARCHAR(50) NOT NULL,
    CONSTRAINT pk_funcionarios PRIMARY KEY (fun_codigo)
);

-- ============================================
-- Tabela: tb_produtos
-- ============================================
CREATE SEQUENCE IF NOT EXISTS tb_produtos_pro_codigo_seq;

CREATE TABLE tb_produtos (
    pro_codigo                       BIGINT NOT NULL DEFAULT nextval('tb_produtos_pro_codigo_seq'),
    pro_descricao                    VARCHAR(50) NOT NULL,
    pro_preco                        NUMERIC(7,2) NOT NULL,
    pro_quantidade                   INTEGER NOT NULL,
    pro_versao                       BIGINT DEFAULT 0,
    tb_fornecedores_for_codigo       BIGINT NOT NULL,
    CONSTRAINT pk_produtos PRIMARY KEY (pro_codigo),
    CONSTRAINT fk_produtos_fornecedor
        FOREIGN KEY (tb_fornecedores_for_codigo)
        REFERENCES tb_fornecedores (for_codigo)
);

-- ============================================
-- Tabela: tb_vendas
-- ============================================
CREATE SEQUENCE IF NOT EXISTS tb_vendas_ven_codigo_seq;

CREATE TABLE tb_vendas (
    ven_codigo                       BIGINT NOT NULL DEFAULT nextval('tb_vendas_ven_codigo_seq'),
    ven_horario                      TIMESTAMP NOT NULL,
    ven_valor_total                  NUMERIC(7,2) NOT NULL,
    tb_funcionarios_fun_codigo       BIGINT NOT NULL,
    CONSTRAINT pk_vendas PRIMARY KEY (ven_codigo),
    CONSTRAINT fk_vendas_funcionario
        FOREIGN KEY (tb_funcionarios_fun_codigo)
        REFERENCES tb_funcionarios (fun_codigo)
);

-- ============================================
-- Tabela: tb_itens
-- ============================================
CREATE SEQUENCE IF NOT EXISTS tb_itens_ite_codigo_seq;

CREATE TABLE tb_itens (
    ite_codigo                       BIGINT NOT NULL DEFAULT nextval('tb_itens_ite_codigo_seq'),
    ite_quantidade                   INTEGER NOT NULL,
    ite_valor_parcial                NUMERIC(7,2) NOT NULL,
    tb_produtos_pro_codigo           BIGINT NOT NULL,
    tb_vendas_ven_codigo             BIGINT NOT NULL,
    CONSTRAINT pk_itens PRIMARY KEY (ite_codigo),
    CONSTRAINT fk_itens_produto
        FOREIGN KEY (tb_produtos_pro_codigo)
        REFERENCES tb_produtos (pro_codigo),
    CONSTRAINT fk_itens_venda
        FOREIGN KEY (tb_vendas_ven_codigo)
        REFERENCES tb_vendas (ven_codigo)
);

-- ============================================
-- Dados iniciais: Administrador padrão
-- Senha: admin123 (SHA-256)
-- ============================================
INSERT INTO tb_funcionarios (fun_nome, fun_cpf, fun_senha, fun_funcao)
VALUES (
    'Administrador',
    '000.000.000-00',
    '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9',
    'Administrador'
);
