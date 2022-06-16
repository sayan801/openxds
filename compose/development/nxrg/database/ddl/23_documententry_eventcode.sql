--
-- Table and index structure for table `documententry_eventcode`
--
create table if not exists `documententry_eventcode` (
  `id` int(11) not null auto_increment,
  `documententry_entryuuid` varchar(64) not null,
  `code_name` varchar(64) not null,
  `scheme_name` varchar(64) not null,
  `creation_time` datetime(6) default current_timestamp,
  `last_update_time` datetime(6) default current_timestamp on update current_timestamp,

  constraint documententry_eventcode_pk primary key (id),

  constraint documententry_eventcode_documententry_fk foreign key (documententry_entryuuid) references documententries(entryuuid) on delete cascade
) engine = InnoDB collate 'utf8_bin';

create unique index if not exists `documententry_eventcode_documententry_entryuuid_id`
on documententry_eventcode(documententry_entryuuid, id);