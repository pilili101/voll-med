alter table consultas add column cancelada tinyint not null,
add column motivo_cancelacion varchar(20);
update consultas set cancelada = 0;
