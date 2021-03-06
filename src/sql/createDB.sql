DROP TABLE prenotazione;
DROP TABLE spettacolo;
DROP TABLE posto;
DROP TABLE utente;
DROP TABLE film;
DROP TABLE ruolo;
DROP TABLE sala;
DROP TABLE genere;
DROP TABLE prezzo;
DROP TABLE password_dimenticata;


--tabella film
CREATE TABLE FILM (
    ID_FILM INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
    TITOLO VARCHAR(100), 
    ID_GENERE INTEGER, 
    URL_TRAILER VARCHAR(255), 
    DURATA INTEGER, 
    TRAMA VARCHAR(10000),   
    URL_LOCANDINA VARCHAR(255), 
    ATTORI VARCHAR(100), 
    REGISTA VARCHAR(100), 
    FRASE VARCHAR(10000), 
    PRIMARY KEY (ID_FILM)
);

--tabella genere
CREATE TABLE GENERE (
    ID_GENERE INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), 
    DESCRIZIONE VARCHAR(50), 
    PRIMARY KEY (ID_GENERE)
);

--tabella posto
CREATE TABLE POSTO (
	ID_POSTO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), 
	ID_SALA INTEGER, 
	RIGA INTEGER, 
	COLONNA INTEGER, 
	ESISTE BOOLEAN, --per indicare se è rotto o no
	OCCUPATO BOOLEAN DEFAULT false  NOT NULL, 
	PRIMARY KEY (ID_POSTO)
);
--tabella prenotazione
CREATE TABLE PRENOTAZIONE (
	ID_PRENOTAZIONE INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), 
	ID_UTENTE INTEGER, 
	ID_SPETTACOLO INTEGER, 
	ID_PREZZO INTEGER, 
	ID_POSTO INTEGER, 
	PAGATO BOOLEAN DEFAULT true , 
	DATA_ORA_OPERAZIONE TIMESTAMP, 
	PRIMARY KEY (ID_PRENOTAZIONE),
CONSTRAINT spettacoliPosti UNIQUE(id_spettacolo, id_posto)
);

--tabella prezzo
CREATE TABLE PREZZO (
	ID_PREZZO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1),
	TIPO VARCHAR(50), 
	PREZZO DOUBLE, 
	PRIMARY KEY (ID_PREZZO)
);

--tabella ruolo
CREATE TABLE RUOLO (
	ID_RUOLO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), 
	RUOLO VARCHAR(50), 
	PRIMARY KEY (ID_RUOLO)
);
--tabella sala
CREATE TABLE SALA (
	ID_SALA INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), 
	DESCRIZIONE VARCHAR(50), 
	PRIMARY KEY (ID_SALA)
);

--tabella spettacolo
CREATE TABLE SPETTACOLO (
	ID_SPETTACOLO INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), 
	ID_FILM INTEGER, 
	DATA_ORA TIMESTAMP, 
	ID_SALA INTEGER, 
	PRIMARY KEY (ID_SPETTACOLO)
);
--tabella utente
CREATE TABLE UTENTE (
	ID_UTENTE INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY(START WITH 1, INCREMENT BY 1), 
        NOME VARCHAR(50),
	EMAIL VARCHAR(50), 
	PASSWORD VARCHAR(50), 
	CREDITO DOUBLE, 
	ID_RUOLO INTEGER, -- 'verificare' in caso non sia stato ancora verificato
	CODICE_ATTIVAZIONE VARCHAR(300), --forse aggiungere anche la data (?)
        DATA_INVIO_CODICE_ATTIVAZIONE TIMESTAMP,
	PRIMARY KEY (ID_UTENTE)
);

-- tabella per password dimenticate
CREATE TABLE password_dimenticata
(
    email varchar(50),
    codice varchar(300), 
    data timestamp,
    CONSTRAINT pd_code_unique UNIQUE (codice),
    CONSTRAINT pd_code_email_pk PRIMARY KEY (email,codice)
);

CREATE INDEX postoIndex ON posto(id_sala, riga, colonna);
CREATE INDEX utenteIndex ON utente(email);
CREATE INDEX password_dimenticataIndex ON password_dimenticata(codice);
CREATE INDEX prenotazioneIndex ON prenotazione(id_spettacolo, id_utente, id_posto);
