package VueControleur;

import Modele.GrilleSimple;
import Modele.Piece;

import javax.swing.*;
import java.awt.*;
public class VuePieceSuivante extends JPanel {
    private final static int TAILLE = 16;
    private GrilleSimple modele;


    public VuePieceSuivante(GrilleSimple modele) {
        this.modele = modele;
        setPreferredSize(new Dimension(TAILLE * 4, TAILLE * 4)); // Ajustez la taille en conséquence
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dessiner la pièce suivante à droite de la grille
        Piece pieceSuivante = modele.getPieceSuivante();
        int x = modele.TAILLE + 1; // Ajustez la position X selon vos besoins
        int y = modele.TAILLE / 2; // Ajustez la position Y selon vos besoins
        dessinerFormePiece(g, modele.getPieceSuivante());
    }

    public void dessinerFormePiece(Graphics g, Piece p) {
        for (int y = 0; y < p.getForme().type.length; y++) {
            for (int x = 0; x < p.getForme().type[y].length; x++) {
                if (p.getForme().type[y][x] == 1) {
                    int codeCouleur = p.getForme().getCodeCouleur();
                    g.setColor(codeCouleurEnCouleur(codeCouleur));
                    g.fillRect(x*TAILLE, y*TAILLE, TAILLE, TAILLE);
                }
            }
        }
    }

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
