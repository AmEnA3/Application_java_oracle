CREATE TABLE livres (
    id_livre NUMBER PRIMARY KEY,
    code_livre VARCHAR2(20) PRIMARY KEY,
    titre VARCHAR2(100) NOT NULL,
    auteur VARCHAR2(100) NOT NULL,
    categorie VARCHAR2(50)  NOT NULL,
    annee_publication NUMBER(4) ,
    quantite_disponible NUMBER 
);

CREATE TABLE etudiants (
    num_etudiant VARCHAR2(20) PRIMARY KEY,
    nom VARCHAR2(50) ,
    prenom VARCHAR2(50),
    email VARCHAR2(100),
    telephone VARCHAR2(20)
);




CREATE TABLE emprunts (
    id_emprunt NUMBER PRIMARY KEY,
    num_etudiant NUMBER REFERENCES etudiants(num_etudiant),
    id_livre NUMBER REFERENCES livres(id_livre),
    date_emprunt DATE,
    duree_emprunt NUMBER
);













