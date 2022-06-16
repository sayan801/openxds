--
-- Table and index structure for table `documententries`
--
create table if not exists `documententrycontent` (
  `id` int(11) not null auto_increment,
  `xml` longtext,
  `creation_time` datetime(6) default current_timestamp,
  `last_update_time` datetime(6) default current_timestamp on update current_timestamp,
  constraint documententrycontent_pk primary key (id)
) engine = InnoDB collate 'utf8_bin';

create table if not exists `documententries` (
  `id` int(11) not null auto_increment,
  `entryuuid` varchar(64) not null,
  `logicaluuid` varchar(64) not null,
  `version` int(11) not null,
  `patientid` varchar(32) not null,
  `patientid_assigningauthorityid` varchar(32) not null,
  `patientid_assigningauthoritytype` varchar(16) not null,
  `availabilitystatus` varchar(64) not null,
  `documententrytype` varchar(64) not null,
  `uniqueid` varchar(64) not null,
  `classcode_codename` varchar(64) not null,
  `classcode_schemename` varchar(64) not null,
  `typecode_codename` varchar(64) not null,
  `typecode_schemename` varchar(64) not null,
  `practicesettingcode_codename` varchar(64) not null,
  `practicesettingcode_schemename` varchar(64) not null,
  `creationtime` datetime(6),
  `servicestarttime` datetime(6),
  `servicestoptime` datetime(6),
  `healthcarefacilitytypecode_codename` varchar(64) not null,
  `healthcarefacilitytypecode_schemename` varchar(64) not null,
  `formatcode_codename` varchar(64) not null,
  `formatcode_schemename` varchar(64) not null,
  `documententrycontentid` int(11) not null unique,
  `creation_time` datetime(6) default current_timestamp,
  `last_update_time` datetime(6) default current_timestamp on update current_timestamp,
  `migration_pid` int(11) not null default 0,
  constraint documententry_pk primary key (id),
  constraint documententry_entryuuid_uk unique key (entryuuid),
  constraint documententry_fk_content foreign key (documententrycontentid) references documententrycontent(id) on delete cascade
) engine = InnoDB collate 'utf8_bin';
