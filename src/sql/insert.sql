--tabella film
INSERT INTO FILM (TITOLO, ID_GENERE, URL_TRAILER, DURATA, TRAMA, URL_LOCANDINA, ATTORI, REGISTA, FRASE) 
	VALUES ('Fast And Furious 7', 4, 'https://www.youtube.com/watch?v=JZNmeWqb9rI', 127, 'Dopo aver ferito gravemente Owen Shaw ed eliminato la sua squadra, Dominic Toretto, Brian Conner, e il resto dei membri della famiglia possono finalmente tornare negli Stati Uniti e vivere una vita normale. Dom tenta di aiutare Letty nel riguadagnare la sua memoria, mentre Brian, ormai diventato padre, inizia a prendere dimestichezza con la sua vita da genitore. Intanto a Londra, il fratello maggiore di Owen, Deckard Shaw, irrompe violentemente e fa una sanguinosa strage di poliziotti interno dell ospedale in cui è stato portato il fratello in coma, giurandogli che lo vendicherà, eliminando Dominic e tutta la sua famiglia.', 'http://www.imdb.com/title/tt2820852/', 'Vin Diesel,Paul Walker', 'James Wan', 'Deckard Shaw cerca vendetta contro Dominic Toretto e la sua famiglia per il fratello');
INSERT INTO FILM (TITOLO, ID_GENERE, URL_TRAILER, DURATA, TRAMA, URL_LOCANDINA, ATTORI, REGISTA, FRASE) 
	VALUES ('Titanic', 5, '', 194, '', '', NULL, NULL, 'Sto volando, sto volando Jack');
INSERT INTO FILM (TITOLO, ID_GENERE, URL_TRAILER, DURATA, TRAMA, URL_LOCANDINA, ATTORI, REGISTA, FRASE) 
	VALUES ('The Avengers', 4, '', 137, '', '', NULL, NULL, 'Io ho un esercito! E noi abbiamo un Hulk!');
INSERT INTO FILM (TITOLO, ID_GENERE, URL_TRAILER, DURATA, TRAMA, URL_LOCANDINA, ATTORI, REGISTA, FRASE) 
	VALUES ('Dear John', 5, '', 105, '', '', NULL, NULL, 'Ma infondo, cosa è un anno dopo due settimane così...');
INSERT INTO FILM (TITOLO, ID_GENERE, URL_TRAILER, DURATA, TRAMA, URL_LOCANDINA, ATTORI, REGISTA, FRASE) 
	VALUES ('Shark Tale', 4, '', 86, '', '', NULL, NULL, 'Tadaaa! Sebastaian il delfino smacchia balene!');
INSERT INTO FILM (TITOLO, ID_GENERE, URL_TRAILER, DURATA, TRAMA, URL_LOCANDINA, ATTORI, REGISTA, FRASE) 
	VALUES ('Playing It Cool', 5, '', 91, '', '', NULL, NULL, 'Un secondo per innamorarsi, una vita per stare insieme');
INSERT INTO FILM (TITOLO, ID_GENERE, URL_TRAILER, DURATA, TRAMA, URL_LOCANDINA, ATTORI, REGISTA, FRASE) 
	VALUES ('LEGO Movie', 2, '', 102, '', '', NULL, NULL, 'Overpriced Cofee?');
INSERT INTO FILM (TITOLO, ID_GENERE, URL_TRAILER, DURATA, TRAMA, URL_LOCANDINA, ATTORI, REGISTA, FRASE) 
	VALUES ('Rush', 4, '', 123, '', '', NULL, NULL, 'Più sei vicino alla morte, più ti senti vivo');
INSERT INTO FILM (TITOLO, ID_GENERE, URL_TRAILER, DURATA, TRAMA, URL_LOCANDINA, ATTORI, REGISTA, FRASE) 
	VALUES ('Lo Squalo', 4, '', 124, '', '', NULL, NULL, 'Serve una barca più grande...');
INSERT INTO FILM (TITOLO, ID_GENERE, URL_TRAILER, DURATA, TRAMA, URL_LOCANDINA, ATTORI, REGISTA, FRASE) 
	VALUES ('Wolf Children', 2, '', 117, '', '', NULL, NULL, 'Cosa vuoi? Vivere come un umano o come un lupo?');
 
 
 --tabella genere
INSERT INTO GENERE (DESCRIZIONE) 
	VALUES ('fantasy');
INSERT INTO GENERE (DESCRIZIONE) 
	VALUES ('drammatico');
INSERT INTO GENERE (DESCRIZIONE) 
	VALUES ('fantascienza');
INSERT INTO GENERE (DESCRIZIONE) 
	VALUES ('azione');
INSERT INTO GENERE (DESCRIZIONE) 
	VALUES ('romantico');
 
 --tabella posto
 INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, ESISTE, OCCUPATO) 
	VALUES (1, 0, 0, true, false);
INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, ESISTE, OCCUPATO) 
	VALUES (1, 0, 1, true, false);
INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, ESISTE, OCCUPATO) 
	VALUES (1, 0, 2, true, false);
INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, ESISTE, OCCUPATO) 
	VALUES (1, 0, 3, true, false);
INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, ESISTE, OCCUPATO) 
	VALUES (1, 1, 0, true, false);
INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, ESISTE, OCCUPATO) 
	VALUES (1, 1, 1, true, false);
INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, ESISTE, OCCUPATO) 
	VALUES (1, 1, 2, true, false);
INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, ESISTE, OCCUPATO) 
	VALUES (2, 0, 0, true, false);
INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, ESISTE, OCCUPATO) 
	VALUES (2, 0, 1, true, false);
INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, ESISTE, OCCUPATO) 
	VALUES (2, 1, 0, true, false);
INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, ESISTE, OCCUPATO) 
	VALUES (2, 1, 1, true, false);
INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, ESISTE, OCCUPATO) 
	VALUES (2, 1, 2, true, false);
INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, ESISTE, OCCUPATO) 
	VALUES (1, 1, 3, true, false);
INSERT INTO POSTO (ID_SALA, RIGA, COLONNA, ESISTE, OCCUPATO) 
	VALUES (2, 0, 2, true, false);

--tabella prenotazione

--tabella prezzo
INSERT INTO PREZZO (TIPO, PREZZO) 
	VALUES ('normale', 8.0);
INSERT INTO PREZZO (TIPO, PREZZO) 
	VALUES ('ridotto', 4.0);
INSERT INTO PREZZO (TIPO, PREZZO) 
	VALUES ('studente', 5.0);
INSERT INTO PREZZO (TIPO, PREZZO) 
	VALUES ('militare', 6.0);
INSERT INTO PREZZO (TIPO, PREZZO) 
	VALUES ('disabile', 5.0);

--tabella ruolo
INSERT INTO RUOLO (RUOLO) 
	VALUES ('admin');
INSERT INTO RUOLO (RUOLO) 
	VALUES ('user');
INSERT INTO RUOLO (RUOLO) 
	VALUES ('verificare');

--tabella sala
INSERT INTO SALA (DESCRIZIONE) 
	VALUES ('Piscina');
INSERT INTO SALA (DESCRIZIONE) 
	VALUES ('DriveIn');
INSERT INTO SALA (DESCRIZIONE) 
	VALUES ('Sweet');
INSERT INTO SALA (DESCRIZIONE) 
	VALUES ('Nerd');
INSERT INTO SALA (DESCRIZIONE) 
	VALUES ('Sala con 50 posti a sedere');

--tabella spettacolo
INSERT INTO SPETTACOLO (ID_FILM, DATA_ORA, ID_SALA) 
	VALUES (1, '2016-02-21 13:20:00.0', 2);
INSERT INTO SPETTACOLO (ID_FILM, DATA_ORA, ID_SALA) 
	VALUES (1, '2016-02-20 20:00:00.0', 3);
INSERT INTO SPETTACOLO (ID_FILM, DATA_ORA, ID_SALA) 
	VALUES (1, '2016-02-20 19:00:00.0', 3);
INSERT INTO SPETTACOLO (ID_FILM, DATA_ORA, ID_SALA) 
	VALUES (2, '2016-11-20 00:00:00.0', 1);
INSERT INTO SPETTACOLO (ID_FILM, DATA_ORA, ID_SALA) 
	VALUES (2, '2016-11-20 00:00:00.0', 3);
INSERT INTO SPETTACOLO (ID_FILM, DATA_ORA, ID_SALA) 
	VALUES (2, '2015-12-20 00:00:00.0', 1);
INSERT INTO SPETTACOLO (ID_FILM, DATA_ORA, ID_SALA) 
	VALUES (3, '2016-11-20 15:15:00.0', 4);
INSERT INTO SPETTACOLO (ID_FILM, DATA_ORA, ID_SALA) 
	VALUES (4, '2016-11-20 00:00:00.0', 3);
INSERT INTO SPETTACOLO (ID_FILM, DATA_ORA, ID_SALA) 
	VALUES (4, '2016-11-20 00:00:00.0', 4);
INSERT INTO SPETTACOLO (ID_FILM, DATA_ORA, ID_SALA) 
	VALUES (5, '2016-11-20 00:00:00.0', 1);
INSERT INTO SPETTACOLO (ID_FILM, DATA_ORA, ID_SALA) 
	VALUES (9, '2016-11-20 00:00:00.0', 2);
INSERT INTO SPETTACOLO (ID_FILM, DATA_ORA, ID_SALA) 
	VALUES (7, '2015-12-04 18:08:00.0', 2);
INSERT INTO SPETTACOLO (ID_FILM, DATA_ORA, ID_SALA) 
	VALUES (10, '2015-12-04 15:57:00.0', 2);

--tabella utente
INSERT INTO UTENTE (EMAIL, PASSWORD, CREDITO, ID_RUOLO, VERIFICATO, CODICE_ATTIVAZIONE) 
	VALUES ('admin@cineland.it', 'admin', 0.0, 1, true, 0.0);
INSERT INTO UTENTE (EMAIL, PASSWORD, CREDITO, ID_RUOLO, VERIFICATO, CODICE_ATTIVAZIONE) 
	VALUES ('email@falso.com', 'password', 0.0, 2, true, 0.0);
INSERT INTO UTENTE (EMAIL, PASSWORD, CREDITO, ID_RUOLO, VERIFICATO, CODICE_ATTIVAZIONE) 
	VALUES ('mail@mail.com', 'password', 0.0, 2, true, 0.0);
INSERT INTO UTENTE (EMAIL, PASSWORD, CREDITO, ID_RUOLO, VERIFICATO, CODICE_ATTIVAZIONE) 
	VALUES ('gmail@gmail.com', 'passeword', 0.0, 2, true, 353.0);
INSERT INTO UTENTE (EMAIL, PASSWORD, CREDITO, ID_RUOLO, VERIFICATO, CODICE_ATTIVAZIONE) 
	VALUES ('prova@prova.ot', 'cerco', 0.0, 2, false, 1450.0);
INSERT INTO UTENTE (EMAIL, PASSWORD, CREDITO, ID_RUOLO, VERIFICATO, CODICE_ATTIVAZIONE) 
	VALUES ('p@l', 'l', 0.0, 2, false, 2155.0);
INSERT INTO UTENTE (EMAIL, PASSWORD, CREDITO, ID_RUOLO, VERIFICATO, CODICE_ATTIVAZIONE) 
	VALUES ('provami@test.gmail.it', 'passwordNonSicura', 0.0, 2, true, 3128.0);
