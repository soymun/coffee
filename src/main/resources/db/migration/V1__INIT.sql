create table if not exists dc_command
(
    id   integer PRIMARY KEY,
    name varchar(255)
);

create table if not exists logging_command
(
    id         bigserial PRIMARY KEY,
    message    text,
    command_id integer
        constraint dc_command_fk references dc_command (id),
    time       timestamptz
);

create table if not exists coffee_type_log
(
    id          bigserial PRIMARY KEY,
    coffee_type varchar(255),
    command_id  integer
        constraint command_fk references logging_command (id)
);

insert into dc_command(id, name)
VALUES (1, 'Готовка кофе'),
       (2, 'Очистка кофе-машины'),
       (3, 'Перезагрузка кофе-машины'),
       (4, 'Отключение кофе-машины'),
       (5, 'Включение кофе-машины');