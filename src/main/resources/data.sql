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
