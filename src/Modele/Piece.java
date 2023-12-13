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
    private int[][] rotation; // Indique l'orientation actuelle de la pièce
    private int dY = -1;


    //le constructeur de piece qui prend en paramettre la grille a modifier
    //en plaçant la piece et la forme de piece qu'on veut placer
    public Piece(GrilleSimple _grille, Formes forme_) {
        grille = _grille;
        this.forme = forme_;
        this.rotation=new int[forme_.type.length][forme_.type[0].length];
        for (int i=0; i<forme_.type.length; i++){
            for (int j=0; j<forme_.type[0].length; j++){
                rotation[i][j]=forme_.type[i][j];
            }
        }
    }
    public void action() {}

    @Override
    public void run() {
        int nextY = y;
        int nextX = x;
        nextY -= dY;
        if (!grille.verifColision(this, nextX, nextY)) {
            y = nextY;
            x = nextX;
            System.out.println("pos" + x + " " + y);
        } else if( grille.verifColision(this,nextX,nextY)){
            this.blocked=true;
        }
    }

    public Formes getForme(){
            return forme;
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

    public void sety(int y_) {
        y = y_;
    }

    public int[][] getType() {
        return forme.type;
    }

    public int[][] getRotation(){
        return this.rotation;
    }

    public void gauche() {
        int nX = x - 1;
        if (!grille.verifColision(this, nX, y)) {
            x = nX;
            System.out.println("nouvelle position piece : x = " + nX + ", y = " + y);
            grille.notifyObservers();
        } else {
            System.out.println("impossible de se deplacer a gauche");
        }
    }

    public void droite() {
        int nX = x + 1;
        if (!grille.verifColision(this, nX, y) ) {
            x = nX;
            System.out.println("nouvelle position piece : x = " + nX + ", y = " + y);

            grille.notifyObservers();
        } else {
            System.out.println("impossible de se deplacer a droite");
        }
    }

    public void modifRotation() {
        int[][] CurrentRotation = this.rotation;//type actuel
        int[][] newRotation = new int[CurrentRotation[0].length][CurrentRotation.length]; //type apres rotation
        // parcourt chaque élément du tableau typeActuel.
        // Effectuer la rotation de la pièce
        if(!grille.verifColision(this,x,y)){
            for (int i = 0; i < CurrentRotation.length; i++) {
                for (int j = 0; j < CurrentRotation[i].length; j++) {
                    if (!grille.verifColision(this, j, CurrentRotation.length - 1 - i) ) {
                        //sens horaire
                        //la nouvelle position de l'indice de ligne après la rotation.
                        newRotation[j][CurrentRotation.length - 1 - i] = CurrentRotation[i][j];
                    }
                }
            }
            // Mettre à jour le type de la pièce avec la rotation
            if(!grille.verifColision2(newRotation,x,y)){
                this.rotation = newRotation;
            }
        }
    }


    public  void bas (){
        int nY=y+1;

        if(!grille.verifColision(this,x,nY)){
            y=nY;
            System.out.println("nouvelle position piece : x = " + x + ", y = " + nY);
        }
        else {
            System.out.println("impossible de se deplacer en gauche");
        }
    }

}

