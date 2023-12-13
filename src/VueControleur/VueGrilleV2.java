package VueControleur;

import Modele.GrilleSimple;
import Modele.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Observable;
import java.util.Observer;

/**
 * La classe VueGrilleV2 représente la vue de la grille du jeu Tetris.
 * Elle étend la classe JPanel 
 * Elle implémente l'interface Observer pour observer les changements au niveau de GrilleSimple.
 */
class VueGrilleV2 extends JPanel implements Observer {
    //taille d'une cellule dans la grille
    private final static int TAILLE = 16;
    private GrilleSimple modele;
    Canvas c;

    /**
     * Constructeur de la classe VueGrilleV2.
     * @param _modele La grille du jeu.
     */
    public VueGrilleV2(GrilleSimple _modele) {

        modele = _modele;
        setLayout(new BorderLayout());
        Dimension dim = new Dimension(TAILLE * modele.TAILLE, TAILLE * modele.TAILLE);

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
            }
        };

        c.setPreferredSize(dim);
        add(c, BorderLayout.CENTER);
    }

    /**
     * Méthode pour convertir un code couleur en une couleur spécifique.
     * @param code Le code couleur.
     * @return La couleur correspondante.
     */
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

    /**
     * Méthode de rafraichissement de la vue lorsqu'il y a des mises à jour dans la grille
     */
    @Override
    public void update(Observable o, Object arg) {

        BufferStrategy bs = c.getBufferStrategy();

        if(bs == null) {
            c.createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        // appel de la fonction pour dessiner
        c.paint(g);
        // Récupérer les coordonnées de la pièce
        int x = modele.getPieceCourante().getx();
        int y = modele.getPieceCourante().gety();
        afficheGrille(g);
        dessinerFormePiece(g, modele.getPieceCourante(), x, y);
        g.dispose();
        bs.show();
    }

    /**
     * Méthode pour dessiner la forme d'une pièce sur la grille.
     * @param g L'objet Graphics.
     * @param p La pièce à dessiner.
     * @param _x La position en x.
     * @param _y La position en y.
     */
    public void dessinerFormePiece(Graphics g, Piece p, int _x, int _y) {
        for (int y = 0; y < p.getRotation().length; y++) {
            for (int x = 0; x < p.getRotation()[y].length; x++) {
                if (p.getRotation()[y][x] == 1) {
                    int codeCouleur = p.getForme().getCodeCouleur();
                    g.setColor(codeCouleurEnCouleur(codeCouleur));
                    g.fillRect((_x + x) * TAILLE, (_y + y) * TAILLE, TAILLE, TAILLE);
                    // Dessiner le contour noir
                    g.setColor(Color.BLACK);
                    g.drawRect((_x + x) * TAILLE, (_y+y) * TAILLE, TAILLE, TAILLE);
                }
            }
        }
    }

    /**
     * Méthode pour afficher la grille.
     * @param g L'objet Graphics.
     */
    public void afficheGrille(Graphics g){
        for (int y = 0; y < modele.TAILLE; y++) {
            for (int x = 0; x < modele.TAILLE; x++) {
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
                g.setColor(Color.darkGray);
                g.drawRoundRect(x * TAILLE, y * TAILLE, TAILLE, TAILLE, 1, 1);
            }
        }
    }
}
