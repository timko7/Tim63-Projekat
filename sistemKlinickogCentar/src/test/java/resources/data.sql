
insert into `pacijent` (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`) values ( 'Marko', 'Marković','m@gmail.com','11111111','Grad1','Adresa1','Drzava1','111111111','3324',0);
insert into `pacijent` (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`) values ('Milan', 'Milanović','mglukicy@gmail.com','22222222','Grad1','Adresa1','Drzava1','111111111','344',0);
insert into `pacijent` (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`) values ( 'Ivana', 'Novaković','i@gmail.com','33333333','Grad1','Adresa1','Drzava1','111111111','555',0);

insert into `tipPregleda` (`id`,`NazivTipa`, `Slobodan`,`IdKlinike`,`Cena`) values (1L,'S1',true,1L,600);

insert into `pregled` ( `Id`, `Cena` , `DatumVreme`, `TrajanjePregleda`, `IdTipa`, `IdSale` , `IdLekara`, `IdKlinike` , `IdPacijenta`, `Odradjen`, `Rezervisan`, `verzija`) values (1L,500,'2020-02-28T00:00:00',60,1L,1L,1L,1L,null,false,false,0L);
insert into `pregled` (`Id`,`Cena`, `DatumVreme`,`TrajanjePregleda`,`IdTipa`,`IdSale`,`IdLekara`,`IdKlinike`,`IdPacijenta`,`Odradjen`,`Rezervisan`, `verzija`) values (2L,700,'2020-03-28T13:00:00',90,1L,1L,1L,1L,null,false,false,0L);
insert into `pregled` (`Id`,`Cena`, `DatumVreme`,`TrajanjePregleda`,`IdTipa`,`IdSale`,`IdLekara`,`IdKlinike`,`IdPacijenta`,`Odradjen`,`Rezervisan`, `verzija`) values (3L,1500,'2020-04-28T14:00:00',120,2L,2L,2L,2L,null,false,false,0L);
insert into `pregled` (`Id`,`Cena`, `DatumVreme`,`TrajanjePregleda`,`IdTipa`,`IdSale`,`IdLekara`,`IdKlinike`,`IdPacijenta`,`Odradjen`,`Rezervisan`,`verzija`) values (4L,2000,'2020-05-28T15:00:00',30,3L,3L,3L,1L,null,false,false,0L);
insert into `pregled` (`Id`,`Cena`, `DatumVreme`,`TrajanjePregleda`,`IdTipa`,`IdSale`,`IdLekara`,`IdKlinike`,`IdPacijenta`,`Odradjen`,`Rezervisan`,`verzija`) values (5L,3000,'2020-06-28T16:00:00',60,2L,3L,2L,2L,2L,false,true,0L);

insert into `sala` (`id`,`Naziv`, `Slobodna`,`IdKlinike`) values (1L,'S1',true,1L);


insert into `zaktaniPregledi` (`Id`,`Cena`, `DatumVreme`,`TrajanjePregleda`,`IdTipa`,`IdSale`,`IdLekara`,`IdKlinike`,`IdPacijenta`,`Odradjen`,`Rezervisan`,`Odobren`,`version`) values (1L,500,'2020-06-28T16:00:00',60,2L,null,1L,2L,2L,false,false,false,0L);
insert into `zaktaniPregledi` (`Id`,`Cena`, `DatumVreme`,`TrajanjePregleda`,`IdTipa`,`IdSale`,`IdLekara`,`IdKlinike`,`IdPacijenta`,`Odradjen`,`Rezervisan`,`Odobren`,`version`) values (2L,700,'2020-06-28T16:00:00',60,2L,null,1L,2L,2L,false,false,false,0L);
insert into `zaktaniPregledi` (`Id`,`Cena`, `DatumVreme`,`TrajanjePregleda`,`IdTipa`,`IdSale`,`IdLekara`,`IdKlinike`,`IdPacijenta`,`Odradjen`,`Rezervisan`,`Odobren`,`version`) values (3L,1500,'2020-06-28T16:00:00',60,2L,null,2L,2L,1L,false,false,false,0L);
insert into `zaktaniPregledi` (`Id`,`Cena`, `DatumVreme`,`TrajanjePregleda`,`IdTipa`,`IdSale`,`IdLekara`,`IdKlinike`,`IdPacijenta`,`Odradjen`,`Rezervisan`,`Odobren`,`version`) values (4L,2000,'2020-06-28T16:00:00',60,2L,null,2L,1L,1L,false,false,false,0L);
insert into `zaktaniPregledi` (`Id`,`Cena`, `DatumVreme`,`TrajanjePregleda`,`IdTipa`,`IdSale`,`IdLekara`,`IdKlinike`,`IdPacijenta`,`Odradjen`,`Rezervisan`,`Odobren`,`version`) values (5L,3000,'2020-06-28T16:00:00',60,2L,null,2L,1L,1L,false,false,false,0L);

insert into `lekar` (`Id`, `Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`IdKlinike`,`IdTipa`,`Slobodan`,`RadnoVremeOd`,`RadnoVremeDo`,`SrednjaOcena`,`PrviPutLogovan`) values ( 1L,'Ivana', 'Novakovic','i@gmail.com','33333333','Grad1','Adresa1','Drzava1','111111111','555',1,1L,1L,true,1,7,0,true);
insert into `lekar` ( `Id`,`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`IdKlinike`,`IdTipa`,`Slobodan`,`RadnoVremeOd`,`RadnoVremeDo`,`SrednjaOcena`,`PrviPutLogovan`) values (2L,'Mika', 'Novakovic','a@gmail.com','33333333','Grad1','Adresa1','Drzava1','111111111','555',1,1L,1L,true,12,20,0,true);
insert into `lekar` ( `Id`,`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`IdKlinike`,`IdTipa`,`Slobodan`,`RadnoVremeOd`,`RadnoVremeDo`,`SrednjaOcena`,`PrviPutLogovan`) values (3L,'Ivana', 'Novakovic','b@gmail.com','33333333','Grad1','Adresa1','Drzava1','111111111','555',1,2L,2L,true,13,21,0,false);
insert into `lekar` ( `Id`,`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`IdKlinike`,`IdTipa`,`Slobodan`,`RadnoVremeOd`,`RadnoVremeDo`,`SrednjaOcena`,`PrviPutLogovan`) values (4L,'Ivana', 'Novakovic','v@gmail.com','33333333','Grad1','Adresa1','Drzava1','111111111','555',1,2L,2L,true,8,16,0,false);

insert into `kalendar` ( `Id`,`Datum`, `Od`, `Do`,`IdLekara`) values (1L,'2020-06-28 01:00:00', '2020-06-28 01:00:00','2020-06-28 01:30:00',1L);
insert into `kalendar` ( `Id`,`Datum`, `Od`, `Do`,`IdLekara`) values (2L,'2020-06-28 02:00:00', '2020-06-28 02:00:00','2020-06-28 02:30:00',1L);
insert into `kalendar` ( `Id`,`Datum`, `Od`, `Do`,`IdLekara`) values (3L,'2020-06-28 12:00:00', '2020-06-28 12:00:00','2020-06-28 12:30:00',2L);
insert into `kalendar` ( `Id`,`Datum`, `Od`, `Do`,`IdLekara`) values (4L,'2020-06-28 13:00:00', '2020-06-28 13:00:00','2020-06-28 13:30:00',2L);

insert into `zahtevOdsustvo` ( `Id`,`DatumPocetka`, `DatumZavrsetka`, `IdLekara`,`IdKlinike`,`Obradjen`,`Prihvacen`,`version`) values (1L,'2020-06-28', '2020-06-30',1L,1L,true,true,0L);
insert into `zahtevOdsustvo` ( `Id`,`DatumPocetka`, `DatumZavrsetka`, `IdLekara`,`IdKlinike`,`Obradjen`,`Prihvacen`,`version`) values (2L,'2020-06-20', '2020-06-25',2L,1L,true,true,0L);
insert into `zahtevOdsustvo` ( `Id`,`DatumPocetka`, `DatumZavrsetka`, `IdLekara`,`IdKlinike`,`Obradjen`,`Prihvacen`,`version`) values (3L,'2020-06-28', '2020-06-30',3L,1L,true,true,0L);
insert into `zahtevOdsustvo` ( `Id`,`DatumPocetka`, `DatumZavrsetka`, `IdLekara`,`IdKlinike`,`Obradjen`,`Prihvacen`,`version`) values (4L,'2020-06-28', '2020-06-30',4L,1L,true,true,0L);

insert into `ocena` ( `Id`,`Ocena`, `IdLekara`) values (1L,3,1L);
insert into `ocena` ( `Id`,`Ocena`, `IdLekara`) values (2L,3,1L);
insert into `ocena` ( `Id`,`Ocena`, `IdLekara`) values (3L,5,1L);
insert into `ocena` ( `Id`,`Ocena`, `IdLekara`) values (4L,5,2L);
insert into `ocena` ( `Id`,`Ocena`, `IdLekara`) values (5L,4,2L);
