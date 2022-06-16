--
-- Table and index structure for table `documententry_author`
--
create table if not exists `documententry_author` (
  `id` int(11) not null auto_increment,
  `documententry_entryuuid` varchar(64) not null,
  `family_name` varchar(64) not null,
  `given_name` varchar(64),
  `further_given_names` varchar(64),
  `creation_time` datetime(6) default current_timestamp,
  `last_update_time` datetime(6) default current_timestamp on update current_timestamp,

  constraint documententry_author_pk primary key (id),

  constraint documententry_author_documententry_fk foreign key (documententry_entryuuid) references documententries(entryuuid) on delete cascade
) engine = InnoDB collate 'utf8_bin';

create unique index if not exists `documententry_author_documententry_entryuuid_id`
on documententry_author(documententry_entryuuid, id);