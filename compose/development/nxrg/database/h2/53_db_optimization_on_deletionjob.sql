-- SDS-5671 + SDS-5672 modified for because of h2

alter table documententries add column deletetrigger_time datetime(6);

alter table documententries modify `creation_time` datetime(6) default current_timestamp not null;

alter table deleted_documententries modify `uniqueid` varchar(128) not null;

create index if not exists documententries_type_code_type_scheme_deletetrigger on documententries (typecode_codename,typecode_schemename, deletetrigger_time);

drop index if exists documententries_servicestarttime_creation_time on documententries;