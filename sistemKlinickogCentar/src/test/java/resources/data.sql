
insert into `pacijent` (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`) values ( 'Marko', 'Marković','m@gmail.com','11111111','Grad1','Adresa1','Drzava1','111111111',"3324",0);
insert into `pacijent` (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`) values ('Milan', 'Milanović','mm@gmail.com','22222222','Grad1','Adresa1','Drzava1','111111111',"344",1);
insert into `pacijent` (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`) values ( 'Ivana', 'Novaković','i@gmail.com','33333333','Grad1','Adresa1','Drzava1','111111111',"555",2);


insert into `pregled` (`Id`,Cena`, `DatumVreme`,`TrajanjePregleda`,`IdTipa`,`IdSale`,`IdLekara`,`IdKlinike`,`IdPacijenta`,`Odradjen`,`Rezervisan`) values (1L,500,'2020-02-28T00:00:00',60,1L,1L,1L,1L,null,false,false);
insert into `pregled` (`Id`,`Cena`, `DatumVreme`,`TrajanjePregleda`,`IdTipa`,`IdSale`,`IdLekara`,`IdKlinike`,`IdPacijenta`,`Odradjen`,`Rezervisan`) values (2L,700,'2020-03-28T13:00:00',90,1L,1L,1L,1L,null,false,false);
insert into `pregled` (`Id`,`Cena`, `DatumVreme`,`TrajanjePregleda`,`IdTipa`,`IdSale`,`IdLekara`,`IdKlinike`,`IdPacijenta`,`Odradjen`,`Rezervisan`) values (3L,1500,'2020-04-28T14:00:00',120,2L,2L,2L,2L,null,false,false);
insert into `pregled` (`Id`,`Cena`, `DatumVreme`,`TrajanjePregleda`,`IdTipa`,`IdSale`,`IdLekara`,`IdKlinike`,`IdPacijenta`,`Odradjen`,`Rezervisan`) values (4L,2000,'2020-05-28T15:00:00',30,3L,3L,3L,1L,null,false,false);
insert into `pregled` (`Id`,`Cena`, `DatumVreme`,`TrajanjePregleda`,`IdTipa`,`IdSale`,`IdLekara`,`IdKlinike`,`IdPacijenta`,`Odradjen`,`Rezervisan`) values (5l,3000,'2020-06-28T16:00:00',60,2L,3L,2L,2L,2L,false,true);
