package Modele;

import java.util.Observable;

/**
 * La classe GrilleSimple modélise la grille de jeu Tetris.
 * Elle gère l'état de chaque case, la pièce en cours et la pièce suivante.
 * Implémente l'interface Runnable pour le changement d'état de la pièce et de la grille.
 * Elle étend la classe Observable pour permettre l'observation des mises à jour de la grille.
 */

public class GrilleSimple extends Observable implements Runnable {

    public final int TAILLE = 20;
    private int[][] grille;
    private Piece pieceCourante;
    private Piece pieceSuivante;
    int score;

    /**
     * Constructeur de la classe GrilleSimple.
     * Initialise la grille, génère une pièce aléatoire en cours et une pièce suivante.
     * Démarre un ordonnanceur pour gérer la mise à jour périodique de la grille.
     */
    public GrilleSimple() {
        grille = new int[TAILLE][TAILLE];
        initialiser();
        pieceCourante = genererPieceAleatoire();
        pieceSuivante = genererPieceAleatoire();
        System.out.println("Coordonnées initiales de la pièce : x = " + pieceCourante.getx() + ", y = " + pieceCourante.gety());
        new OrdonnanceurSimple(this).start(); // pour changer le temps de pause, garder la référence de l'ordonnanceur
    }

    /**
     * Initialise la grille en mettant toutes les cases à zéro.
     */
    private void initialiser() {
        for (int i = 0; i < TAILLE; i++) {
            for (int j = 0; j < TAILLE; j++) {
                grille[i][j] = 0;
            }
        }
    }

    //getters et setters

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

    /**
     * Génère une nouvelle pièce aléatoire.
     * @return Une nouvelle pièce aléatoire.
     */
    public Piece genererPieceAleatoire() {
        return new Piece(this, Formes.values()[(int) (Math.random() * Formes.values().length)]);
    }

    /**
     * Place une pièce dans la grille en fonction de sa position et de sa rotation (sa configuration selon sa rotation).
     * @param piece La pièce à placer dans la grille.
     */
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

    /**
     * Affecte un code couleur à une case de la grille .
     * @param x indice de la colonne de la grille.
     * @param y indice de la ligne de la grille.
     */
    public void placerCase(int x, int y, int codeCouleur) {
        grille[y][x] = codeCouleur;
    }

    /**
     * Méthode principale faisant appel aux autres méthodes pour
     * Gère le placement des pièces, la vérification de la grille et la génération de nouvelles pièces.
     */
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

    /**
     * Teste si à la position (px,py) la pièce est en colision .
     * @param p la pièce à vérifier
     * @param px position x de la pièce lors de la vérification
     * @param py position y de la pièce lors de la vérification
     * @return true si il sort ou se met sur une case de la gille déjà occupée 
     */
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

    /**
     * Teste si à la position (px,py) la pièce est en colision .
     * @param tab référence à une configuration de pièce (et non à une pièce)
     * @param px position x de la pièce lors de la vérification
     * @param py position y de la pièce lors de la vérification
     * @return true si il sort ou se met sur une case de la gille déjà occupée 
     */
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

    /**
     * Déplace la pièce courante à gauche dans la grille.
     */
    public void gauche() {
        pieceCourante.gauche();
    }

    /**
     * Déplace la pièce courante à droite dans la grille.
     */
    public void droite() {
        pieceCourante.droite();
    }

    /**
     * Déplace la pièce courante vers le bas dans la grille.
     */
    public void bas() {
        pieceCourante.bas();
    }

    /**
     * Effectue une rotation qui modifie la configuration de pieceCourante.rotation 
     */
    public void rotation() {
        pieceCourante.modifRotation();
    }

    /**
     * Vérifie si une ligne donnée de la grille est complète
     * @param y indice le la ligne à vérifier
     * @return true si toute les cases de la lignes sont différents de 0 
     */
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

    /**
     * supprime une ligne donnée de la grille
     * décale les lignes en haut de la ligne supprimé à une case vers le bas
     * @param y indice le la ligne à supprimer 
     */
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

    /**
     * Effectue la vérification et la suppression des lignes complètes dans la totalité de la grille
     * décale les lignes du dessus de la ligne supprimé à une case vers le bas
     * incrémente le score selon le nombre de lignes supprimées
     */  
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

    /**
     * Réinitialise toute les valeurs des attributs de GrilleSimple pour une nouvelle partie
     */  
    public void nouvellePartie(){
        initialiser();
        setScore(0);
        setPieceCourante(genererPieceAleatoire());
        setPieceSuivante(genererPieceAleatoire());
        setChanged();
        notifyObservers();
    }

    /**
     * Vérifie si une partie est terminée
     * @return true si la pièce courante est sortie de la partie supérieure de la grille
     */  
    public boolean partie_terminee(){
        return verifColision(pieceCourante, pieceCourante.getx(), pieceCourante.gety());
    }
}



