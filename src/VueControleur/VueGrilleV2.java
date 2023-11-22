package VueControleur;

import Modele.GrilleSimple;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Observable;
import java.util.Observer;

class VueGrilleV2 extends JPanel implements Observer {
//taille d'une cellule dans la grille
    private final static int TAILLE = 16;
    private GrilleSimple modele;
    Canvas c;

    public VueGrilleV2(GrilleSimple _modele) {

        modele = _modele;
        setLayout(new BorderLayout());
        Dimension dim = new Dimension(TAILLE*modele.TAILLE,TAILLE*modele.TAILLE);
        //this.setPreferredSize(dim);



        //setBackground(Color.black);

        c = new Canvas() {


            public void paint(Graphics g) {


                for (int i = 0; i < modele.TAILLE; i++) {
                    for (int j = 0; j < modele.TAILLE; j++) {
                        //if (!(i == modele.getPieceCourante().getx() && j == modele.getPieceCourante().gety())) {
                        g.setColor(Color.WHITE);
                        //dessiner un rectangle rempli sur une zone graphique spécifiée.
                        //int x, int y, int width, int height
                        g.fillRect(i * TAILLE, j * TAILLE, TAILLE, TAILLE);
                        g.setColor(Color.BLACK);
                        g.drawRoundRect(i * TAILLE, j * TAILLE, TAILLE, TAILLE, 1, 1);

                    }

                }
                //g.setColor(Color.RED);

            }
        };

        c.setPreferredSize(dim);
        add(c, BorderLayout.CENTER);
    }



    @Override
    public void update(Observable o, Object arg) {

        BufferStrategy bs = c.getBufferStrategy(); // bs + dispose + show : double buffering pour éviter les scintillements
        if(bs == null) {
            c.createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        c.paint(g); // appel de la fonction pour dessiner
        // Récupérer les coordonnées de la pièce J

        int x = modele.getPieceCourante().getx();

        int y = modele.getPieceCourante().gety();


        // Dessiner les rectangles de la pièce J

        dessinerFormePiece(g, modele.getPieceCourante().getType(), x, y);


        g.dispose();

        bs.show();

    }

//parcourt le tableau de la pièce, et pour chaque case (1 pour une partie de la pièce, 0 pour une partie vide), elle remplit le rectangle correspondant dans la grille avec la couleur appropriée
    public void dessinerFormePiece(Graphics g, int[][] type, int x, int y) {

        for (int i = 0; i < type.length; i++) {

            for (int j = 0; j < type[i].length; j++) {

                if (type[i][j] == 1) {

                    g.setColor(Color.BLUE);

                    g.fillRect((x + i) * TAILLE, (y + j) * TAILLE, TAILLE, TAILLE);

                }
            }

            }

        }

/*    public void afficheGrille(Graphics g){
        for (int i = 0; i < modele.TAILLE; i++) {
            for (int j = 0; j < modele.TAILLE; j++) {
                //if (!(i == modele.getPieceCourante().getx() && j == modele.getPieceCourante().gety())) {
                if (modele[])
                g.setColor(Color.WHITE);
                //dessiner un rectangle rempli sur une zone graphique spécifiée.
                //int x, int y, int width, int height
                g.fillRect(i * TAILLE, j * TAILLE, TAILLE, TAILLE);
                g.setColor(Color.BLACK);
                g.drawRoundRect(i * TAILLE, j * TAILLE, TAILLE, TAILLE, 1, 1);

            }

        }
    }*/
}
