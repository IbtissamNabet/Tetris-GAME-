package VueControleur;

import Modele.GrilleSimple;
import Modele.Piece;

import javax.swing.*;
import java.awt.*;

/**
 * La classe VuePieceSuivante représente la vue de la pièce suivante 
 * à afficher à droite de la grille du jeu Tetris.
 * Elle étend la classe JPanel.
 */
public class VuePieceSuivante extends JPanel {
    private final static int TAILLE = 16;
    private GrilleSimple modele;

    /**
     * Constructeur de la classe VuePieceSuivante.
     * @param modele La grille contenant la pièce suivante.
     */
    public VuePieceSuivante(GrilleSimple modele) {
        this.modele = modele;
        setPreferredSize(new Dimension(TAILLE * 4, TAILLE * 4)); // Ajustez la taille en conséquence
        setBackground(Color.WHITE);
    }

    /**
     * Redessine le composant graphique avec la pièce suivante à afficher.
     * Cette méthode est appelée automatiquement lorsque le composant doit être mis à jour.
     * @param g L'objet Graphics utilisé pour dessiner le composant.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dessiner la pièce suivante à droite de la grille
        Piece pieceSuivante = modele.getPieceSuivante();
        int x = modele.TAILLE + 1;
        int y = modele.TAILLE / 2;
        dessinerFormePiece(g, modele.getPieceSuivante());
    }

    /**
     * Méthode pour dessiner la forme d'une pièce.
     * @param g L'objet Graphics.
     * @param p La pièce à dessiner.
     */
    public void dessinerFormePiece(Graphics g, Piece p) {
        for (int y = 0; y < p.getForme().type.length; y++) {
            for (int x = 0; x < p.getForme().type[y].length; x++) {
                if (p.getForme().type[y][x] == 1) {
                    int codeCouleur = p.getForme().getCodeCouleur();
                    g.setColor(codeCouleurEnCouleur(codeCouleur));
                    g.fillRect(x*TAILLE, y*TAILLE, TAILLE, TAILLE);
                    // Dessiner le contour noir
                    g.setColor(Color.BLACK);
                    g.drawRect(x * TAILLE, y * TAILLE, TAILLE, TAILLE);
                }
            }
        }
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
}

