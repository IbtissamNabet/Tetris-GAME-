package Modele;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
public class Piece implements Runnable {

    GrilleSimple grille;

    public boolean blocked=false;
    private int x = 4 + (int) (Math.random() * ((16 - 4) + 1));
    private int y = -1;


    private Formes forme;
    private int codeCouleur = 1;
    private int rotation; // Indique l'orientation actuelle de la pièce

    private int dY = -1;
    private Formes formes;

    public enum Formes {
        I(new int[][]{{1, 1, 1, 1}}),
        L(new int[][]{{0, 0, 1}, {1, 1, 1}}),
        J(new int[][]{{1, 0, 0}, {1, 1, 1}}),
        O(new int[][]{{1, 1}, {1, 1}}),
        S(new int[][]{{0, 1, 1}, {1, 1, 0}}),
        T(new int[][]{{0, 1, 0}, {1, 1, 1}}),
        Z(new int[][]{{1, 1, 0}, {0, 1, 1}});
        public int[][] type;

        //constructeur de l'enum
        Formes(int[][] type_) {
            this.type = type_;
        }
    }

    //le constructeur de piece qui prend en paramettre la grille a modifier
    //en plaçant la piece est la forme de piece qu'on veut placer
    public Piece(GrilleSimple _grille, Formes forme_) {
        grille = _grille;
        this.forme = forme_;

    }

    public void action() {

    }
@Override
    public void run() {
        int nextY = y;
        int nextX = x;
        nextY -= dY;

        if (grille.validationPosition(nextX, nextY) && !grille.verifColision(this, nextX, nextY)) {
            y = nextY;
            x = nextX;
            System.out.println("pos" + x + " " + y);
        } else if(grille.validationPosition(nextX, nextY) && grille.verifColision(this,nextX,nextY)){
            //Piece p =new Piece(grille,grille.genererPieceAleatoire().forme);
            //grille.setPieceCourante(p);
            this.blocked=true;
        }
    }




    public int getCodeCouleur() {
        return codeCouleur;
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public void setx(int x_) {
        x = x_;
    }

    public void gety(int y_) {
        y = y_;
    }

    public int[][] getType() {
        return forme.type;
    }

    public void gauche() {
        int nX = x - 1;

        if (!grille.verifColision(this, nX, y) && grille.validationPosition(nX, y)) {
            x = nX;

            System.out.println("nouvelle position piece : x = " + nX + ", y = " + y);

            grille.notifyObservers();
        } else {
            System.out.println("impossible de se deplacer a gauche");
        }


    }

    public void droite() {
        int nX = x + 1;
        if (!grille.verifColision(this, nX, y) && grille.validationPosition(nX, y)) {
            x = nX;
            System.out.println("nouvelle position piece : x = " + nX + ", y = " + y);

            grille.notifyObservers();
        } else {
            System.out.println("impossible de se deplacer a droite");
        }
    }

    public void rotation() {
        int[][] typeActuel = this.forme.type;//type actuel
        int[][] typeRotation = new int[typeActuel[0].length][typeActuel.length]; //type apres rotation
// parcourt chaque élément du tableau typeActuel.
        // Effectuer la rotation de la pièce
        for (int i = 0; i < typeActuel.length; i++) {
            for (int j = 0; j < typeActuel[i].length; j++) {
                if (!grille.verifColision(this, j, typeActuel.length - 1 - i) && grille.validationPosition(j, typeActuel.length - 1 - i)) {
                    //sens horaire
                    //la nouvelle position de l'indice de ligne après la rotation.
                    typeRotation[j][typeActuel.length - 1 - i] = typeActuel[i][j];
                }
            }

            // Mettre à jour le type de la pièce avec la rotation
            this.forme.type = typeRotation;

        }
    }


    public  void bas (){
        int nY=y-1;

        if(!grille.verifColision(this,x,nY)&& grille.validationPosition(x,nY)){
            y=nY;
            System.out.println("nouvelle position piece : x = " + x + ", y = " + nY);
        }
        else {
            System.out.println("impossible de se deplacer a gauche");
        }


    }

}

