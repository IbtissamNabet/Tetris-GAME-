package Modele;

import java.util.Observable;


public class GrilleSimple extends Observable implements Runnable {

    public final int TAILLE = 20;
    private int[][] grille;

    //initialisation de la piéce
    private Piece pieceCourante;


    public GrilleSimple() {
        grille = new int[TAILLE][TAILLE];
        initialiser();

        pieceCourante=genererPieceAleatoire();
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
    public Piece genererPieceAleatoire() {
        return new Piece(this, Piece.Formes.values()[(int) (Math.random() * Piece.Formes.values().length)]);
    }

    public void placerPiece(Piece piece) {
        int[][] type = piece.getType();
        int pieceX = piece.getx();
        int pieceY = piece.gety();

        for (int i = 0; i < type.length; i++) {
            for (int j = 0; j < type[i].length; j++) {
                if (type[i][j] == 1) {
                    placerCase(pieceX + i, pieceY + j, piece.getCodeCouleur());
                }
            }
        }
        pieceCourante.setx(pieceX);
        pieceCourante.gety(pieceY);
        System.out.println("Coordonnées après placement de la pièce : x = " + pieceCourante.getx() + ", y = " + pieceCourante.gety());
    }

    public void placerCase(int x, int y, int codeCouleur) {
        if (validationPosition(x, y)) {
            grille[x][y] = codeCouleur;
        }
    }

    public void action() {
        pieceCourante.action();


    }

    public boolean validationPosition(int _nextX, int _nextY) {
        return (_nextX >= 0 && _nextX < TAILLE && _nextY >= 0 && _nextY < TAILLE);
    }

    public void run() {

        pieceCourante.run();

        setChanged(); // setChanged() + notifyObservers() : notification de la vue pour le rafraichissement
        notifyObservers();

    }

    public Piece getPieceCourante() {
        return pieceCourante;
    }

    //verifier la colision dans la grille
    //verifier la collision avec la grille inferieure

//verifier si ya collision de la piece p dans la posotion px py dans la grille
    public boolean verifColision(Piece p,int px ,int py ) {
        //px et py  les coordonnées de la pièce par rapport à la grille.
        //on recupere le tableau qui represente le type de la piece actuelle
        int[][] type = p.getType();
        for (int x = 0; x < type.length; x++) {
            for (int  y= 0; y < type[x].length; y++) {
                //on parcourt le tableau de la piece
                //si ya pas d'autre piece (d'ou !=0) et si on est pas en bas de la gille
                if (type[x][y] == 1) {
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
    public void gauche(){
        pieceCourante.gauche();


    }
    public  void droite(){
        pieceCourante.droite();

    }
    /*public  void bas (){
        pieceCourante.bas();


    }*/
}
/*
public void supprimerLignesPleines() {

    int i = 0;

    while (i < this.hauteur) {

        int j = 0;

        while (j < this.largeur) {

            if (this.grille[i][j] == 0) {

                break;

            }

            j++;

        }

        if (j == this.largeur) {

            this.bougerLignes(i, -1);

            i++;

        } else {

            i++;

        }

    }

}
 */
/*
    Ajoutez une méthode bougerLignes pour déplacer les lignes du haut vers le bas lorsqu'une ligne est pleine :

java

public void bougerLignes(int ligne, int sens) {

    if (ligne < 0 || ligne >= this.hauteur) {

        return;

    }

    for (int i = ligne; i > 0 && i < this.hauteur; i += sens) {

        for (int j = 0; j < this.largeur; j++) {

            this.grille[i][j] = this.grille[i - sens][j];

        }

    }

}*/