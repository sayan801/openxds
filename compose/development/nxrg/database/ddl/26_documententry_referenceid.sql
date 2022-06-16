--
-- Table and index structure for table `documententry_referenceid`
--
create table if not exists `documententry_referenceid` (
  `id` int(11) not null auto_increment,
  `documententry_entryuuid` varchar(64) not null,
  `reference_id` varchar(64) not null,
  `assigningauthority_id` varchar(64),
  `assigningauthority_type` varchar(64),
  `typecode` varchar(64) not null,
  `homecommunityid_id` varchar(64),
  `homecommunityid_type` varchar(64),
  `creation_time` datetime(6) default current_timestamp,
  `last_update_time` datetime(6) default current_timestamp on update current_timestamp,

  constraint documententry_referenceid_pk primary key (id),

  constraint documententry_referenceid_documententry_fk foreign key (documententry_entryuuid) references documententries(entryuuid) on delete cascade
) engine = InnoDB collate 'utf8_bin';

create unique index if not exists `documententry_referenceid_documententry_entryuuid_id`
on documententry_referenceid(documententry_entryuuid, id);