create table avenger (
    id uuid not null,
    nick varchar(36) not null,
    person varchar(128) not null,
    description varchar(128),
    history text,
    primary key (id)
)

alter table avenger add constraint UK_5r88eemotwgru6k8ilqb2lidh unique (nick)