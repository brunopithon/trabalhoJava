CREATE TABLE reserva (
    id int auto_increment primary key,
    data_inicio datetime not null,
    data_fim datetime not null,
    id_acomodacao int not null,
    id_hospede int not null,
    FOREIGN KEY (id_acomodacao) REFERENCES acomodacao (id),
    FOREIGN KEY (id_hospede) REFERENCES hospede (id)
);