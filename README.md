-- Script para criar o Banco de Dados:
-- Table: public.cidade

-- DROP TABLE IF EXISTS public.cidade;

CREATE TABLE IF NOT EXISTS public.cidade
(
    cidadeid integer NOT NULL DEFAULT nextval('cidade_cidadeid_seq'::regclass),
    nome character varying(100) COLLATE pg_catalog."default" NOT NULL,
    populacao integer,
    area numeric(10,2),
    estadoid integer NOT NULL,
    CONSTRAINT cidade_pkey PRIMARY KEY (cidadeid),
    CONSTRAINT cidade_nome_key UNIQUE (nome),
    CONSTRAINT cidade_estadoid_fkey FOREIGN KEY (estadoid)
        REFERENCES public.estado (estadoid) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.cidade
    OWNER to postgres;

-- Table: public.estado

-- DROP TABLE IF EXISTS public.estado;

CREATE TABLE IF NOT EXISTS public.estado
(
    estadoid integer NOT NULL DEFAULT nextval('estado_estadoid_seq'::regclass),
    nome character varying(100) COLLATE pg_catalog."default" NOT NULL,
    sigla character(2) COLLATE pg_catalog."default" NOT NULL,
    regiao character varying(50) COLLATE pg_catalog."default",
    CONSTRAINT estado_pkey PRIMARY KEY (estadoid),
    CONSTRAINT estado_nome_key UNIQUE (nome),
    CONSTRAINT estado_sigla_key UNIQUE (sigla)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.estado
    OWNER to postgres;

-- Table: public.evento

-- DROP TABLE IF EXISTS public.evento;

CREATE TABLE IF NOT EXISTS public.evento
(
    eventoid integer NOT NULL DEFAULT nextval('evento_eventoid_seq'::regclass),
    nome character varying(150) COLLATE pg_catalog."default" NOT NULL,
    descricao text COLLATE pg_catalog."default",
    datainicio date,
    datafim date,
    precoingresso numeric(10,2),
    cidadeid integer NOT NULL,
    CONSTRAINT evento_pkey PRIMARY KEY (eventoid),
    CONSTRAINT evento_nome_key UNIQUE (nome),
    CONSTRAINT evento_cidadeid_fkey FOREIGN KEY (cidadeid)
        REFERENCES public.cidade (cidadeid) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE RESTRICT
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.evento
    OWNER to postgres;
