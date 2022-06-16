

ALTER TABLE documententries
ADD repository_uniqueid varchar(64);

insert ignore into fieldmigrationstatus (migrationid, progressid, targetid) values ('documententry-repositoryid-add', 0, (select IFNULL(MAX(id), 0) from documententries));