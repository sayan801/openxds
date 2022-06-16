alter table documententries modify column uniqueid varchar(128) not null;
alter table folders modify column uniqueid varchar(128) not null;
alter table submissionsets modify column uniqueid varchar(128) not null;