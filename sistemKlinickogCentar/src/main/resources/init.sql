

insert into klinika ( ime, adresa, opis, brojac_pacijenata, srednja_ocena) values ('Klinika1', 'Adresa1', 'Klinika opsteg karaktera', 0,0);
insert into klinika ( ime, adresa, opis, brojac_pacijenata, srednja_ocena) values ('Klinika2', 'Adresa2', 'Specijalizovana klinika', 0,0);
insert into klinika ( ime, adresa, opis, brojac_pacijenata, srednja_ocena) values ('Klinika3', 'Adresa3', 'Klinika se nalazi u Novom Sadu', 0,0);

insert into admin_klinike (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`idKlinike`,`Uloga`,`PrviPutLogovan`) values ('Marko', 'Marković','m@gmail.com','11111111','Grad1','Adresa1','Drzava1','111111111',1L,2,true);
insert into admin_klinike (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`idKlinike`,`Uloga`,`PrviPutLogovan`) values ('Pera', 'Peric','p@gmail.com','22222222','Grad1','Adresa1','Drzava1','222222222',1L,2,true);
insert into admin_klinike (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`idKlinike`,`Uloga`,`PrviPutLogovan`) values ('Zika', 'Zikic','z@gmail.com','33333333','Grad1','Adresa1','Drzava1','222222222',2L,2,true);
insert into admin_klinike (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`idKlinike`,`Uloga`,`PrviPutLogovan`) values ('Mika', 'Mikic','mm@gmail.com','44444444','Grad1','Adresa1','Drzava1','222222222',3L,2,true);

insert into sala (Naziv, slobodna, idKlinike) values ('Sala1',true,1L);
insert into sala (Naziv, slobodna, idKlinike) values ('Sala2',true,1L);
insert into sala (Naziv, slobodna, idKlinike) values ('Sala3',true,1L);
insert into sala (Naziv, slobodna, idKlinike) values ('Sala4',true,2L);
insert into sala (Naziv, slobodna, idKlinike) values ('Sala5',true,2L);
insert into sala (Naziv, slobodna, idKlinike) values ('Sala6',true,3L);
insert into sala (Naziv, slobodna, idKlinike) values ('Sala7',true,3L);
insert into sala (Naziv, slobodna, idKlinike) values ('Sala8',true,3L);

insert into tipPregleda (NazivTipa, Slobodan, IdKlinike,Cena) values ('Tip1',true,1L,500);
insert into tipPregleda (NazivTipa, Slobodan, IdKlinike,Cena) values ('Tip2',true,1L,600);
insert into tipPregleda (NazivTipa, Slobodan, IdKlinike,Cena) values ('Tip3',true,2L,700);
insert into tipPregleda (NazivTipa, Slobodan, IdKlinike,Cena) values ('Tip4',true,2L,1000);
insert into tipPregleda (NazivTipa, Slobodan, IdKlinike,Cena) values ('Tip5',true,3L,2000);


insert into lekar ( `Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`IdKlinike`,`IdTipa`,`Slobodan`,`RadnoVremeOd`,`RadnoVremeDo`,`SrednjaOcena`,`PrviPutLogovan`) values ('Aca', 'Acic','b@gmail.com','55555555','Grad1','Adresa1','Drzava1','111111111','9999',1,1L,1L,true,1,9,0,true);
insert into lekar ( `Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`IdKlinike`,`IdTipa`,`Slobodan`,`RadnoVremeOd`,`RadnoVremeDo`,`SrednjaOcena`,`PrviPutLogovan`) values ('Borko', 'Borkovic','c@gmail.com','66666666','Grad1','Adresa1','Drzava1','111111111','8888',1,1,1L,1L,true,2,10,0,true);
insert into lekar ( `Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`IdKlinike`,`IdTipa`,`Slobodan`,`RadnoVremeOd`,`RadnoVremeDo`,`SrednjaOcena`,`PrviPutLogovan`) values ('Sava', 'Savic','d@gmail.com','77777777','Grad1','Adresa1','Drzava1','111111111','7777',1,1,1L,2L,true,13,21,0,true);
insert into lekar ( `Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`IdKlinike`,`IdTipa`,`Slobodan`,`RadnoVremeOd`,`RadnoVremeDo`,`SrednjaOcena`,`PrviPutLogovan`) values ('Sanja', 'Sanjic','e@gmail.com','88888888','Grad1','Adresa1','Drzava1','111111111','6666',1,1,2L,3L,true,3,11,0,true);
insert into lekar ( `Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`IdKlinike`,`IdTipa`,`Slobodan`,`RadnoVremeOd`,`RadnoVremeDo`,`SrednjaOcena`,`PrviPutLogovan`) values ('Violeta', 'Markovic','f@gmail.com','99999999','Grad1','Adresa1','Drzava1','111111111','5555',1,1,2L,4L,true,11,19,0,true);
insert into lekar ( `Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`IdKlinike`,`IdTipa`,`Slobodan`,`RadnoVremeOd`,`RadnoVremeDo`,`SrednjaOcena`,`PrviPutLogovan`) values ('Goran', 'Markovic','g@gmail.com','aaaaaaaa','Grad1','Adresa1','Drzava1','111111111','4444',1,1,3L,5L,true,16,23,0,true);
insert into lekar ( `Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`IdKlinike`,`IdTipa`,`Slobodan`,`RadnoVremeOd`,`RadnoVremeDo`,`SrednjaOcena`,`PrviPutLogovan`) values ('Branko', 'Brankovic','h@gmail.com','bbbbbbbb','Grad1','Adresa1','Drzava1','111111111','3333',1,1,3L,5L,true,17,23,0,true);

insert into pacijent (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`) values ('Milica', 'Lukic','mglukic@gmail.com','1111aaaa','Grad1','Adresa1','Drzava1','123456789','11111',0);
insert into pacijent (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`) values ('Sanja', 'Stojadinovic','s@gmail.com','1111bbbb','Grad1','Adresa1','Drzava1','123456789','22222',0);
insert into pacijent (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`) values ('Mateja', 'Kljucevic','mmm@gmail.com','1111cccc','Grad1','Adresa1','Drzava1','123456789','33333',0);
insert into pacijent (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`) values ('Nina', 'Milanovic','nm@gmail.com','1111nnnn','Grad1','Adresa1','Drzava1','123456789','44444',0);
insert into pacijent (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`) values ('Aleksandra', 'Savatic','as@gmail.com','1111kkkk','Grad1','Adresa1','Drzava1','123456789','55555',0);


insert into pregled (`Cena` , `DatumVreme`, `TrajanjePregleda`, `IdTipa`, `IdSale` , `IdLekara`, `IdKlinike` , `IdPacijenta`, `Odradjen`, `Rezervisan`) values (500,'2020-02-28T00:00:00',60,1L,1L,1L,1L,null,false,false);
insert into pregled (`Cena` , `DatumVreme`, `TrajanjePregleda`, `IdTipa`, `IdSale` , `IdLekara`, `IdKlinike` , `IdPacijenta`, `Odradjen`, `Rezervisan`) values (500,'2020-03-01T00:00:00',60,1L,1L,1L,1L,null,false,false);
insert into pregled (`Cena` , `DatumVreme`, `TrajanjePregleda`, `IdTipa`, `IdSale` , `IdLekara`, `IdKlinike` , `IdPacijenta`, `Odradjen`, `Rezervisan`) values (700,'2020-04-01T00:00:00',45,3L,4L,3L,2L,null,false,false);
insert into pregled (`Cena` , `DatumVreme`, `TrajanjePregleda`, `IdTipa`, `IdSale` , `IdLekara`, `IdKlinike` , `IdPacijenta`, `Odradjen`, `Rezervisan`) values (1000,'2020-05-01T00:00:00',15,4L,4L,5L,2L,null,false,false);
insert into pregled (`Cena` , `DatumVreme`, `TrajanjePregleda`, `IdTipa`, `IdSale` , `IdLekara`, `IdKlinike` , `IdPacijenta`, `Odradjen`, `Rezervisan`) values (2000,'2020-06-01T00:00:00',25,5L,8L,6L,3L,null,false,false);






