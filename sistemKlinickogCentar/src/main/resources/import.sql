
insert into `klinicki_centar`.`klinika` (`Id`,`Ime`, `Adresa`, `Opis`,  `srednja_ocena`) values (1,'Klinika1', 'Adresa1', 'Klinika opsteg karaktera', 0);
insert into `klinicki_centar`.`klinika` (`Id`,`Ime`, `Adresa`, `Opis`,  `srednja_ocena`) values (2,'Klinika2', 'Adresa2', 'Specijalizovana klinika', 0);
insert into `klinicki_centar`.`klinika` (`Id`,`Ime`, `Adresa`, `Opis`,  `srednja_ocena`) values (3,'Klinika3', 'Adresa3', 'Klinika se nalazi u Novom Sadu', 0);

insert into `klinicki_centar`.`admin_klinike` (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`id_klinike`,`Uloga`,`Prvi_Put_Logovan`) values ('Marko', 'Marković','m@gmail.com','11111111','Grad1','Adresa1','Drzava1','111111111',1,2,true);
insert into `klinicki_centar`.`admin_klinike`  (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`id_klinike`,`Uloga`,`Prvi_Put_Logovan`) values ('Pera', 'Peric','p@gmail.com','22222222','Grad1','Adresa1','Drzava1','222222222',1,2,true);
insert into `klinicki_centar`.`admin_klinike`  (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`id_klinike`,`Uloga`,`Prvi_Put_Logovan`) values ('Zika', 'Zikic','z@gmail.com','33333333','Grad1','Adresa1','Drzava1','222222222',2,2,true);
insert into `klinicki_centar`.`admin_klinike`  (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`id_klinike`,`Uloga`,`Prvi_Put_Logovan`) values ('Mika', 'Mikic','mm@gmail.com','44444444','Grad1','Adresa1','Drzava1','222222222',3,2,true);

insert into `klinicki_centar`.`sala` (`Naziv`, `Slobodna`, `Id_Klinike`) values ('Sala1', true,1);
insert into `klinicki_centar`.`sala`  (`Naziv`, `Slobodna`, `Id_Klinike`) values ('Sala2',true,1);
insert into `klinicki_centar`.`sala` ( `Naziv`, `Slobodna`, `Id_Klinike`) values ('Sala3',true,1);
insert into `klinicki_centar`.`sala` (`Naziv`, `Slobodna`, `Id_Klinike`) values ('Sala4',true,2);
insert into `klinicki_centar`.`sala`  (`Naziv`, `Slobodna`, `Id_Klinike`) values ('Sala5',true,2);
insert into `klinicki_centar`.`sala` (`Naziv`, `Slobodna`, `Id_Klinike`) values ('Sala6',true,3);
insert into `klinicki_centar`.`sala` (`Naziv`, `Slobodna`, `Id_Klinike`) values ('Sala7',true,3);
insert into `klinicki_centar`.`sala` (`Naziv`, `Slobodna`, `Id_Klinike`) values ('Sala8',true,3);

insert into `klinicki_centar`.`tip_pregleda` (`Naziv_Tipa`, `Slobodan`, `Id_Klinike`,`Cena`) values ('Tip1',true,1,500);
insert into `klinicki_centar`.`tip_pregleda` (`Naziv_Tipa`, `Slobodan`, `Id_Klinike`,`Cena`) values ('Tip2',true,1,600);
insert into `klinicki_centar`.`tip_pregleda`(`Naziv_Tipa`, `Slobodan`, `Id_Klinike`,`Cena`) values ('Tip3',true,2,700);
insert into `klinicki_centar`.`tip_pregleda` (`Naziv_Tipa`, `Slobodan`, `Id_Klinike`,`Cena`) values ('Tip4',true,2,1000);
insert into `klinicki_centar`.`tip_pregleda` (`Naziv_Tipa`, `Slobodan`, `Id_Klinike`,`Cena`) values ('Tip5',true,3,2000);
insert into `klinicki_centar`.`tip_pregleda` (`Naziv_Tipa`, `Slobodan`, `Id_Klinike`,`Cena`) values ('Tip5',true,3,2000);


insert into `klinicki_centar`.`lekar` ( `Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`Id_Klinike`,`Id_Tipa`,`Slobodan`,`Radno_Vreme_Od`,`Radno_Vreme_Do`,`Srednja_Ocena`,`Prvi_Put_Logovan`) values ('Aca', 'Acic','b@gmail.com','55555555','Grad1','Adresa1','Drzava1','111111111','9999',1,1,1,true,1,9,0,true);
insert into `klinicki_centar`.`lekar` ( `Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`Id_Klinike`,`Id_Tipa`,`Slobodan`,`Radno_Vreme_Od`,`Radno_Vreme_Do`,`Srednja_Ocena`,`Prvi_Put_Logovan`) values ('Borko', 'Borkovic','c@gmail.com','66666666','Grad1','Adresa1','Drzava1','111111111','8888',1,1,1,true,2,10,0,true);
insert into `klinicki_centar`.`lekar` ( `Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`Id_Klinike`,`Id_Tipa`,`Slobodan`,`Radno_Vreme_Od`,`Radno_Vreme_Do`,`Srednja_Ocena`,`Prvi_Put_Logovan`) values ('Sava', 'Savic','d@gmail.com','77777777','Grad1','Adresa1','Drzava1','111111111','7777',1,1,2,true,13,21,0,true);
insert into `klinicki_centar`.`lekar` ( `Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`Id_Klinike`,`Id_Tipa`,`Slobodan`,`Radno_Vreme_Od`,`Radno_Vreme_Do`,`Srednja_Ocena`,`Prvi_Put_Logovan`) values ('Sanja', 'Sanjic','e@gmail.com','88888888','Grad1','Adresa1','Drzava1','111111111','6666',1,2,3,true,3,11,0,true);
insert into `klinicki_centar`.`lekar` ( `Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`Id_Klinike`,`Id_Tipa`,`Slobodan`,`Radno_Vreme_Od`,`Radno_Vreme_Do`,`Srednja_Ocena`,`Prvi_Put_Logovan`) values ('Violeta', 'Markovic','f@gmail.com','99999999','Grad1','Adresa1','Drzava1','111111111','5555',1,2,4,true,11,19,0,true);
insert into `klinicki_centar`.`lekar` ( `Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`Id_Klinike`,`Id_Tipa`,`Slobodan`,`Radno_Vreme_Od`,`Radno_Vreme_Do`,`Srednja_Ocena`,`Prvi_Put_Logovan`) values ('Goran', 'Markovic','g@gmail.com','aaaaaaaa','Grad1','Adresa1','Drzava1','111111111','4444',1,3,5,true,16,23,0,true);
insert into `klinicki_centar`.`lekar` ( `Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`,`Id_Klinike`,`Id_Tipa`,`Slobodan`,`Radno_Vreme_Od`,`Radno_Vreme_Do`,`Srednja_Ocena`,`Prvi_Put_Logovan`) values ('Branko', 'Brankovic','h@gmail.com','bbbbbbbb','Grad1','Adresa1','Drzava1','111111111','3333',1,3,5,true,17,23,0,true);

insert into `klinicki_centar`.`pacijent` (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`) values ('Milica', 'Lukic','mglukic@gmail.com','1111aaaa','Grad1','Adresa1','Drzava1','123456789','11111',0);
insert into `klinicki_centar`.`pacijent` (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`) values ('Sanja', 'Stojadinovic','s@gmail.com','1111bbbb','Grad1','Adresa1','Drzava1','123456789','22222',0);
insert into `klinicki_centar`.`pacijent` (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`) values ('Mateja', 'Kljucevic','mmm@gmail.com','1111cccc','Grad1','Adresa1','Drzava1','123456789','33333',0);
insert into `klinicki_centar`.`pacijent` (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`) values ('Nina', 'Milanovic','nm@gmail.com','1111nnnn','Grad1','Adresa1','Drzava1','123456789','44444',0);
insert into `klinicki_centar`.`pacijent` (`Ime`, `Prezime`, `Email`,`Password`,`Grad`,`Adresa`,`Drzava`,`Telefon`,`Broj_Osiguranika`,`Uloga`) values ('Aleksandra', 'Savatic','as@gmail.com','1111kkkk','Grad1','Adresa1','Drzava1','123456789','55555',0);


insert into `klinicki_centar`.`pregled` (`Cena` , `Datum_Vreme`, `Trajanje_Pregleda`, `Id_Tipa`, `Id_Sale` , `Id_Lekara`, `Id_Klinike` , `Id_Pacijenta`, `Odradjen`, `Rezervisan`,`verzija`) values (500,'2020-02-28T00:00:00',60,1,1,1,1,null,false,false,0);
insert into `klinicki_centar`.`pregled` (`Cena` , `Datum_Vreme`, `Trajanje_Pregleda`, `Id_Tipa`, `Id_Sale` , `Id_Lekara`, `Id_Klinike` , `Id_Pacijenta`, `Odradjen`, `Rezervisan`,`verzija`) values (500,'2020-03-01T00:00:00',60,1,1,1,1,null,false,false,0);
insert into `klinicki_centar`.`pregled` (`Cena` , `Datum_Vreme`, `Trajanje_Pregleda`, `Id_Tipa`, `Id_Sale` , `Id_Lekara`, `Id_Klinike` , `Id_Pacijenta`, `Odradjen`, `Rezervisan`,`verzija`) values (700,'2020-04-01T00:00:00',45,3,4,3,2,null,false,false,0);
insert into `klinicki_centar`.`pregled` (`Cena` , `Datum_Vreme`, `Trajanje_Pregleda`, `Id_Tipa`, `Id_Sale` , `Id_Lekara`, `Id_Klinike` , `Id_Pacijenta`, `Odradjen`, `Rezervisan`,`verzija`) values (1000,'2020-05-01T00:00:00',15,4,4,5,2,null,false,false,0);
insert into `klinicki_centar`.`pregled` (`Cena` , `Datum_Vreme`, `Trajanje_Pregleda`, `Id_Tipa`, `Id_Sale` , `Id_Lekara`, `Id_Klinike` , `Id_Pacijenta`, `Odradjen`, `Rezervisan`,`verzija`) values (2000,'2020-06-01T00:00:00',25,5,8,6,3,null,false,false,0);




