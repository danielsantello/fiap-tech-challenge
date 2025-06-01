create table if not exists usuarios(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome varchar(255),
    email varchar(255),
    login varchar(255),
    senha varchar(255),
    ultima_alteracao timestamp
);

insert into usuarios (nome, email, login, senha, ultima_alteracao)
select 'Administrador', 'admin@dalq.com.br', 'admin', SHA2('123456', 256), current_timestamp()
 where not exists (select 1 from usuarios);

create table if not exists enderecos(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT not null,
    logradouro varchar(255),
    numero varchar(255),
    complemento varchar(255),
    bairro varchar(255),
    cidade varchar(255),
    estado varchar(2),
    cep varchar(8),
    ultima_alteracao timestamp
);

insert into enderecos (usuario_id, logradouro, numero, complemento, bairro, cidade, estado, cep, ultima_alteracao)
select 1, 'Av. Paulista', '100', 'Sala 202', 'Bela Vista', 'São Paulo', 'SP', '01310000', current_timestamp()
 where not exists (select 1 from enderecos);
