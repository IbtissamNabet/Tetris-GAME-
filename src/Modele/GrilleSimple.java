package Modele;

import java.util.Observable;


public class GrilleSimple extends Observable implements Runnable {

    public final int TAILLE = 20;
    private int[][] grille;

    //initialisation de la piéce
    private Piece pieceCourante;

    private Piece pieceSuivante;

    int score;


    public GrilleSimple() {
        grille = new int[TAILLE][TAILLE];
        initialiser();

        pieceCourante = genererPieceAleatoire();
        pieceSuivante = genererPieceAleatoire();
        System.out.println("Coordonnées initiales de la pièce : x = " + pieceCourante.getx() + ", y = " + pieceCourante.gety());
        new OrdonnanceurSimple(this).start(); // pour changer le temps de pause, garder la référence de l'ordonnanceur

    }


    private void initialiser() {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                grille[i][j] = 0;
            }
        }
    }

    public Piece getPieceCourante() {
        return pieceCourante;
    }

    public void setPieceCourante(Piece p) {
        this.pieceCourante = p;
    }

    public Piece getPieceSuivante() {
        return pieceSuivante;
    }

    public void setPieceSuivante(Piece p) {
        this.pieceSuivante = p;
    }

    public int[][] getGrille() {
        return this.grille;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int s) {
        score = s;
    }


    public Piece genererPieceAleatoire() {
        return new Piece(this, Formes.values()[(int) (Math.random() * Formes.values().length)]);
        //return new Piece(this, Formes.values()[0]);
    }

    public void placerPiece(Piece piece) {
        int[][] type = piece.getRotation();
        int pieceX = piece.getx();
        int pieceY = piece.gety();

        for (int i = 0; i < type.length; i++) {
            for (int j = 0; j < type[i].length; j++) {
                if (type[i][j] == 1) {
                    placerCase(pieceX + j, pieceY + i, piece.getForme().getCodeCouleur());
                }
            }
        }
        pieceCourante.setx(pieceX);
        pieceCourante.sety(pieceY);
        System.out.println("Coordonnées après placement de la pièce : x = " + pieceCourante.getx() + ", y = " + pieceCourante.gety());
    }

    public void placerCase(int x, int y, int codeCouleur) {
        grille[y][x] = codeCouleur;
    }


    public void action() {
        pieceCourante.action();
    }

    public boolean validationPosition(int _nextX, int _nextY) {
        return (_nextX >= 0 && _nextX < TAILLE && _nextY >= 0 && _nextY < TAILLE);
    }

    public void run() {
        if (partie_terminee()){
            nouvellePartie();
        }
        if (pieceCourante.blocked) {
            System.out.println("placer piece");
            placerPiece(pieceCourante);
            verifGrille();
            pieceCourante = pieceSuivante;
            pieceSuivante = genererPieceAleatoire();
        }
        pieceCourante.run();
        setChanged();
        notifyObservers();
    }

    //verifier la colision dans la grille
    //verifier la collision avec la grille inferieure

    //verifier si ya collision de la piece p dans la posotion px py dans la grille
    public boolean verifColision(Piece p, int px, int py) {
        //px et py  les coordonnées de la pièce par rapport à la grille.
        //on recupere le tableau qui represente le type de la piece actuelle
        int[][] type = p.getRotation();
        for (int y = 0; y < type.length; y++) {
            for (int x = 0; x < type[y].length; x++) {
                //on parcourt le tableau de la piece
                //si ya pas d'autre piece (d'ou !=0) et si on est pas en bas de la gille
                if (type[y][x] == 1) {
                    //calcul de position dans la grille
                    int grilleX = x + px;
                    int grilleY = y + py;

                    // Vérification de la collision
                    if (grilleY >= TAILLE || grilleX < 0 || grilleX >= TAILLE || grilleY < 0 || grille[grilleY][grilleX] != 0) {
                        return true;
                    }
                }
            }
        }
        return false; //pas de colision
    }

    public boolean verifColision2(int[][] tab, int px, int py) {
        //px et py  les coordonnées de la pièce par rapport à la grille.
        //on recupere le tableau qui represente le type de la piece actuelle

        for (int y = 0; y < tab.length; y++) {
            for (int x = 0; x < tab[y].length; x++) {
                //on parcourt le tableau de la piece
                //si ya pas d'autre piece (d'ou !=0) et si on est pas en bas de la gille
                if (tab[y][x] == 1) {
                    //calcul de position dans la grille
                    int grilleX = x + px;
                    int grilleY = y + py;

                    // Vérification de la collision
                    if (grilleY >= TAILLE || grilleX < 0 || grilleX >= TAILLE || grilleY < 0 || grille[grilleY][grilleX] != 0) {
                        return true;
                    }
                }
            }
        }
        return false; //pas de colision
    }

    public void gauche() {
        pieceCourante.gauche();


    }

    public void droite() {
        pieceCourante.droite();

    }

    public void bas() {
        pieceCourante.bas();


    }

    public void rotation() {
        pieceCourante.modifRotation();
    }

    public boolean ligneComplete(int y) {
        boolean complete = true;
        for (int x = 0; x < grille[y].length; x++) {
            if (grille[y][x] == 0) {
                complete = false;
                break;
            }
        }
        return complete;
    }

    public void supprimeLigne(int L) {
        for (int y = L; y >= 0; y--) {
            for (int x = 0; x < grille[y].length; x++) {
                if (y == 0) {
                    grille[y][x] = 0;
                } else {
                    grille[y][x] = grille[y - 1][x];
                }
            }
        }
    }

    public void verifGrille() {
        int nbligneSupp = 0;
        for (int y = 0; y < grille.length; y++) {
            if (ligneComplete(y)) {
                System.out.println("ligne " + y + " complete");

                supprimeLigne(y);
                nbligneSupp++;
            }
        }
        switch (nbligneSupp) {
            case 1:
                System.out.println("une ligne vient d'étre supprimé , score = " + score);
                setScore(40+getScore());
                break;

            case 2:
                System.out.println("une 2eme ligne vient d'étre supprimé , score = " + score);

                setScore(100+getScore());
                break;
            case 3:
                System.out.println("une 3 eme ligne vient d'étre supprimé , score = " + score);

                setScore(300+getScore());
                break;
            case 4:
                System.out.println("une 4 eme ligne vient d'étre supprimé , score = " + score);

                setScore(1200+getScore());
                break;
            default:
                setScore(0+getScore());

        }

    }
    public void nouvellePartie(){
        initialiser();
        setScore(0);
        setPieceCourante(genererPieceAleatoire());
        setPieceSuivante(genererPieceAleatoire());
        setChanged();
        notifyObservers();
    }
    public boolean partie_terminee(){
        return verifColision(pieceCourante, pieceCourante.getx(), pieceCourante.gety());
    }
}



