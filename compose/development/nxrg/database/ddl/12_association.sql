--
-- Table and index structure for table `associations`
--
create table if not exists `associationcontent` (
  `id` int(11) not null auto_increment,
  `xml` longtext,
  `creation_time` datetime(6) default current_timestamp,
  `last_update_time` datetime(6) default current_timestamp on update current_timestamp,
  constraint associationcontent_pk primary key (id)
) engine = InnoDB collate 'utf8_bin';

create table if not exists `associations` (
  `id` int(11) not null auto_increment,
  `entryuuid` varchar(64) not null,
  `association_type` varchar(32) not null,
  `source_association` int(11),
  `sourceuuid_documententry` varchar(64),
  `sourceuuid_folder` varchar(64),
  `sourceuuid_submissionset` varchar(64),
  `target_association` int(11),
  `targetuuid_documententry` varchar(64),
  `targetuuid_folder` varchar(64),
  `targetuuid_submissionset` varchar(64),
  `associationcontentid` int(11) not null unique,
  `creation_time` datetime(6) default current_timestamp,
  `last_update_time` datetime(6) default current_timestamp on update current_timestamp,
  `migration_pid` int(11) not null default 0,

  constraint association_pk primary key (id),

  constraint association_fk_source_association foreign key (source_association) references associations(id),
  constraint association_fk_source_documententry foreign key (sourceuuid_documententry) references documententries(entryuuid),
  constraint association_fk_source_folder foreign key (sourceuuid_folder) references folders(entryuuid),
  constraint association_fk_source_submissionset foreign key (sourceuuid_submissionset) references submissionsets(entryuuid),
  constraint association_fk_target_association foreign key (target_association) references associations(id),
  constraint association_fk_target_documententry foreign key (targetuuid_documententry) references documententries(entryuuid),
  constraint association_fk_target_folder foreign key (targetuuid_folder) references folders(entryuuid),
  constraint association_fk_target_submissionset foreign key (targetuuid_submissionset) references submissionsets(entryuuid),

  constraint association_fk_content foreign key (associationcontentid) references associationcontent(id) on delete cascade
) engine = InnoDB collate 'utf8_bin';
