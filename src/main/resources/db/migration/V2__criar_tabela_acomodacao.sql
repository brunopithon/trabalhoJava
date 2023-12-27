CREATE TABLE acomodacao (
    id int auto_increment primary key,
    nome varchar(150) not null,
    localizacao varchar(150) not null,
    num_registro int not null,
    qtd_de_quartos int not null,
    id_anfitriao int,
    FOREIGN KEY (id_anfitriao) REFERENCES anfitriao (id)
);