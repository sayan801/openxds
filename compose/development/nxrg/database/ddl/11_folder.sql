--
-- Table and index structure for table `folders`
--
create table if not exists `foldercontent` (
  `id` int(11) not null auto_increment,
  `xml` longtext,
  `creation_time` datetime(6) default current_timestamp,
  `last_update_time` datetime(6) default current_timestamp on update current_timestamp,
  constraint foldercontent_pk primary key (id)
) engine = InnoDB collate 'utf8_bin';

create table if not exists `folders` (
  `id` int(11) not null auto_increment,
  `entryuuid` varchar(64) not null unique,
  `logicaluuid` varchar(64) not null,
  `version` int(11) not null,
  `patientid` varchar(32) not null,
  `patientid_assigningauthorityid` varchar(32) not null,
  `patientid_assigningauthoritytype` varchar(16) not null,
  `availabilitystatus` varchar(64) not null,
  `uniqueid` varchar(64) not null,
  `lastupdatetime` datetime(6) not null,
  `foldercontentid` int(11) not null unique,
  `creation_time` datetime(6) default current_timestamp,
  `last_update_time` datetime(6) default current_timestamp on update current_timestamp,

  constraint folder_pk primary key (id),

  constraint folder_fk_content foreign key (foldercontentid) references foldercontent(id) on delete cascade
) engine = InnoDB collate 'utf8_bin';
