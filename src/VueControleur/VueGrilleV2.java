package VueControleur;

import Modele.GrilleSimple;
import Modele.Piece;

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
        Dimension dim = new Dimension(TAILLE * modele.TAILLE, TAILLE * modele.TAILLE);
        //this.setPreferredSize(dim);


        //setBackground(Color.black);

        c = new Canvas() {


            public void paint(Graphics g) {


                for (int y = 0; y < modele.TAILLE; y++) {
                    for (int x = 0; x < modele.TAILLE; x++) {
                        //if (!(i == modele.getPieceCourante().getx() && j == modele.getPieceCourante().gety())) {
                        g.setColor(Color.WHITE);
                        //dessiner un rectangle rempli sur une zone graphique spécifiée.
                        //int x, int y, int width, int height
                        g.fillRect(x * TAILLE, y * TAILLE, TAILLE, TAILLE);
                        g.setColor(Color.BLACK);
                        g.drawRoundRect(x * TAILLE, y * TAILLE, TAILLE, TAILLE, 1, 1);

                    }

                }
                //g.setColor(Color.RED);

            }
        };

        c.setPreferredSize(dim);
        add(c, BorderLayout.CENTER);
    }

    //les couleurs
    Color codeCouleurEnCouleur(int code) {
        switch (code) {
            case 1:
                return Color.GREEN;
            case 2:
                return Color.BLUE;

            case 3:
                return Color.pink;

            case 4:
                return Color.red;

            case 5:
                return Color.orange;

            case 6:
                return Color.yellow;

            case 7:
                return Color.cyan;

            default:
                return Color.BLACK;
        }
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
        afficheGrille(g);
        dessinerFormePiece(g, modele.getPieceCourante(), x, y);

            g.dispose();

        bs.show();

    }

//parcourt le tableau de la pièce, et pour chaque case (1 pour une partie de la pièce, 0 pour une partie vide), elle remplit le rectangle correspondant dans la grille avec la couleur appropriée
    public void dessinerFormePiece(Graphics g, Piece p, int _x, int _y) {
        for (int y = 0; y < p.getForme().type.length; y++) {

            for (int x = 0; x < p.getForme().type[y].length; x++) {

                if (p.getForme().type[y][x] == 1) {

                    int codeCouleur = p.getForme().getCodeCouleur();
                    g.setColor(codeCouleurEnCouleur(codeCouleur));
                    g.fillRect((_x + x) * TAILLE, (_y + y) * TAILLE, TAILLE, TAILLE);

                }
            }
        }
    }

    public void afficheGrille(Graphics g){
        for (int y = 0; y < modele.TAILLE; y++) {
            for (int x = 0; x < modele.TAILLE; x++) {
                //if (!(i == modele.getPieceCourante().getx() && j == modele.getPieceCourante().gety())) {
                if (modele.getGrille()[y][x]!=0){
                    int codeCouleur = modele.getGrille()[y][x];
                    g.setColor(codeCouleurEnCouleur(codeCouleur));
                }
                else{
                    g.setColor(Color.WHITE);
                }
                //dessiner un rectangle rempli sur une zone graphique spécifiée.
                //int x, int y, int width, int height
                g.fillRect(x * TAILLE, y * TAILLE, TAILLE, TAILLE);
                g.setColor(Color.BLACK);
                g.drawRoundRect(x * TAILLE, y * TAILLE, TAILLE, TAILLE, 1, 1);

            }

        }
    }
}
