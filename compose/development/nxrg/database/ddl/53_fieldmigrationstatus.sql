create table if not exists `fieldmigrationstatus` (
  `id` int(11) not null auto_increment,
  `migrationid` varchar(64) not null,
  `progressid` int(11) not null,
  `targetid` int(11) not null,
  `migration_start_time` datetime(6),
  `migration_end_time` datetime(6),
  `last_update_time` datetime(6) default current_timestamp on update current_timestamp,
  constraint fieldmigrationstatus_pk primary key (id)
) engine = InnoDB collate 'utf8_bin';
