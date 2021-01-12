drop table if exists FAHRZEUG;
drop table if exists PARKPLATZ;


create table if not exists PARKPLATZ(
    ParkplatzNummer INTEGER AUTO_INCREMENT,
    EtageNummer INTEGER NOT NULL ,
    Typ varchar(20) NOT NULL,
    PRIMARY KEY (ParkplatzNummer)
);


create table if not exists FAHRZEUG(
    KennenZeichen varchar (20),
    ParkplatzNummer INTEGER NOT NULL UNIQUE,
    PRIMARY KEY (KennenZeichen),
    FOREIGN KEY (ParkplatzNummer) references PARKPLATZ(ParkplatzNummer)
);
