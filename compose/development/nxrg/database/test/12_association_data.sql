insert ignore into associationcontent(id, xml) values(1, '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><Association associationType="urn:oasis:names:tc:ebxml-regrep:AssociationType:HasMember" sourceObject="c80bb5e1-1b5a-4240-9a41-6336870e5763" targetObject="c80bb5e1-1b5a-4240-9a41-6336870e5763" xmlns="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:ns2="urn:ihe:iti:xds-b:2007"/>');

insert ignore into associations (entryuuid, association_type, source_association, sourceuuid_documententry, sourceuuid_folder, sourceuuid_submissionset, target_association, targetuuid_documententry, targetuuid_folder, targetuuid_submissionset, associationcontentid)
values ('33333333-3333-3333-3333-333333333333', 'HAS_MEMBER', null, 'c80bb5e1-1b5a-4240-9a41-6336870e5763', null, null, null, 'c80bb5e1-1b5a-4240-9a41-6336870e5763', null, null, 1);

insert ignore into associationcontent(id) values(2);
insert ignore into associations (entryuuid, association_type, source_association, sourceuuid_documententry, sourceuuid_folder, sourceuuid_submissionset, target_association, targetuuid_documententry, targetuuid_folder, targetuuid_submissionset, associationcontentid)
values ('33333333-3333-3333-3333-333333333302', 'HAS_MEMBER', null, 'c80bb5e1-1b5a-4240-9a41-6336870e0012', null, null, null, 'c80bb5e1-1b5a-4240-9a41-6336870e0012', null, null, 2);

insert ignore into associationcontent(id) values(3);
insert ignore into associations (entryuuid, association_type, source_association, sourceuuid_documententry, sourceuuid_folder, sourceuuid_submissionset, target_association, targetuuid_documententry, targetuuid_folder, targetuuid_submissionset, associationcontentid)
values ('33333333-3333-3333-3333-333333333303', 'HAS_MEMBER', null, null, null, '00000000-0000-0000-0000-000000000003', null, null, null, '00000000-0000-0000-0000-000000000003', 3);

insert ignore into associationcontent(id) values(4);
insert ignore into associations (entryuuid, association_type, source_association, sourceuuid_documententry, sourceuuid_folder, sourceuuid_submissionset, target_association, targetuuid_documententry, targetuuid_folder, targetuuid_submissionset, associationcontentid)
values ('33333333-3333-3333-3333-333333333304', 'HAS_MEMBER', null, null, null, '00000000-0000-0000-0000-000000000004', null, null, null, '00000000-0000-0000-0000-000000000004', 4);
