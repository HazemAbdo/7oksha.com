create table foundsites (
 `URL` varchar(500) NOT NULL,
  `HASH_VALUE` int NOT NULL unique,
  PRIMARY KEY (`URL`)
);
create table indexer(
`term` varchar(50) NOT NULL,
`DocNum` int NOT NULL,
`indx` int NOT NULL,
`wordrank` int,
primary key(`term`,`DocNum`,`indx`),
foreign key ( `DocNum`) references foundsites(`Hash_Value`) on delete cascade on update cascade 
);