create table usuarios(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome varchar(255),
    email varchar(255),
    login varchar(255),
    senha varchar(255),
    ultima_alteracao timestamp
);

insert into usuarios (nome, email, login, senha, ultima_alteracao) values ('Daniel Santello', 'daniel@grupotwo.com.br', 'danielsantello', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', current_timestamp());
