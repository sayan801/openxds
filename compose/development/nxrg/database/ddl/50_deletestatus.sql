create table if not exists `deleted_documententries` (
  `id` int(11) not null auto_increment,
  `entryuuid` varchar(64) not null,
  `uniqueid` varchar(64) not null,
  `deletion_status` varchar(64) not null,
  `deletion_attempts` int(11) not null,
  `creation_time` datetime(6) default current_timestamp,
  constraint deleted_documententries_pk primary key (id)
);
