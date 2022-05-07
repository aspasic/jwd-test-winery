INSERT INTO korisnik (id, e_mail, korisnicko_ime, lozinka, ime, prezime, uloga)
              VALUES (1,'miroslav@maildrop.cc','miroslav','$2y$12$NH2KM2BJaBl.ik90Z1YqAOjoPgSd0ns/bF.7WedMxZ54OhWQNNnh6','Miroslav','Simic','ADMIN');
INSERT INTO korisnik (id, e_mail, korisnicko_ime, lozinka, ime, prezime, uloga)
              VALUES (2,'tamara@maildrop.cc','tamara','$2y$12$DRhCpltZygkA7EZ2WeWIbewWBjLE0KYiUO.tHDUaJNMpsHxXEw9Ky','Tamara','Milosavljevic','KORISNIK');
INSERT INTO korisnik (id, e_mail, korisnicko_ime, lozinka, ime, prezime, uloga)
              VALUES (3,'petar@maildrop.cc','petar','$2y$12$i6/mU4w0HhG8RQRXHjNCa.tG2OwGSVXb0GYUnf8MZUdeadE4voHbC','Petar','Jovic','KORISNIK');

INSERT INTO vinarija (name, year) VALUES ('Bonko Zvogdan', 1999);
INSERT INTO vinarija (name, year) VALUES ('Tamjanika', 2002);
INSERT INTO vinarija (name, year) VALUES ('Vinarija', 2001);

INSERT INTO tip (name) VALUES ('Sovinjon');
INSERT INTO tip (name) VALUES ('Miraval');
INSERT INTO tip (name) VALUES ('Tip vina 3');

INSERT INTO vino (name, description, year, price, stock, type_id, winery_id) 
				VALUES ('Vino 1', 'Lepo je, ali je pivo lepse', 2004, 3000, 0, 1, 1);
INSERT INTO vino (name, description, year, price, stock, type_id, winery_id) 
				VALUES ('Vino 2', 'Nije lose, al skupo', 2006, 6000, 100, 2, 2);
INSERT INTO vino (name, description, year, price, stock, type_id, winery_id) 
				VALUES ('Vino 3','Bogat ukus', 2005, 2000, 500, 2, 3);
INSERT INTO vino (name, description, year, price, stock, type_id, winery_id) 
				VALUES ('Vino 4','Vocna aroma', 2000, 1000, 700, 3, 1);
