--
-- Table and index structure for table `submissionsets`
--
create table if not exists `submissionsetcontent` (
  `id` int(11) not null auto_increment,
  `xml` longtext,
  `creation_time` datetime(6) default current_timestamp,
  `last_update_time` datetime(6) default current_timestamp on update current_timestamp,
  constraint submissionsetcontent_pk primary key (id)
) engine = InnoDB collate 'utf8_bin';

create table if not exists `submissionsets` (
  `id` int(11) not null auto_increment,
  `entryuuid` varchar(64) not null,
  `patientid` varchar(32) not null,
  `patientid_assigningauthorityid` varchar(32) not null,
  `patientid_assigningauthoritytype` varchar(16) not null,
  `uniqueid` varchar(64) not null,
  `migration_uniqueid_fix` int(11) not null default 0,
  `submissionsetcontentid` int(11) not null unique,
  `creation_time` datetime(6) default current_timestamp,
  `last_update_time` datetime(6) default current_timestamp on update current_timestamp,
  constraint submissionset_pk primary key (id),
  constraint submissionset_entryuuid_uk unique key (entryuuid),
  constraint submissionset_uniqueid_uk unique key (uniqueid, migration_uniqueid_fix),
  constraint submissionset_fk_content foreign key (submissionsetcontentid) references submissionsetcontent(id) on delete cascade
) engine = InnoDB collate 'utf8_bin';

