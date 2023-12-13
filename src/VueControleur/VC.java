package VueControleur;

import Modele.GrilleSimple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * La classe VC (VueControleur) représente la vue et le contrôleur du jeu Tetris.
 * Elle étend la classe JFrame et implémente l'interface Observer pour observer les changements dans le modèle.
 * Contient une référence à GrilleSimple
 * Contient également les différents composants graphiques du jeu
 * Ordonne ces composants dans la fenetre
 * gère les interactions avec l'utilisateur
 */
public class VC extends JFrame implements Observer {

    JTextArea jt = new JTextArea("");
    JButton jb = new JButton("do");
    VuePieceSuivante vuePieceSuivante;
    GrilleSimple modele;
    Observer vueGrille;
    private Executor ex =  Executors.newSingleThreadExecutor();

    /**
     * Constructeur de la classe VC.
     * @param _modele Le modèle de la grille.
     */
    public VC(GrilleSimple _modele) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        modele = _modele;

        setSize(400, 450);
        JPanel jp = new JPanel(new BorderLayout());
        Color backgroundColor = Color.GRAY;
        Color buttonColor = Color.WHITE;
        Color textColor = Color.WHITE;
        Color font = Color.BLACK;
        jp.setBackground(backgroundColor);
        jb.setBackground(buttonColor);
        jt.setBackground(textColor);

        jt.setLineWrap(true);
        jt.setWrapStyleWord(true);
        jt.setAlignmentX(Component.CENTER_ALIGNMENT);
        jt.setAlignmentY(Component.CENTER_ALIGNMENT);
        jt.setPreferredSize(new Dimension(400, 50));
        jt.setForeground(font);
        jp.add(jt, BorderLayout.NORTH);
        jp.add(jb, BorderLayout.SOUTH);

        vueGrille = new VueGrilleV2(modele);
        jp.add((JPanel)vueGrille, BorderLayout.CENTER);

        vuePieceSuivante = new VuePieceSuivante(modele);
        jp.add(vuePieceSuivante, BorderLayout.EAST);

        setContentPane(jp);
        jp.setFocusable(true);
        jp.requestFocusInWindow();


        jb.addActionListener(new ActionListener() { //évènement bouton : object contrôleur qui réceptionne
            @Override
            public void actionPerformed(ActionEvent e) {
                ex.execute(new Runnable() {
                    @Override
                    public void run() {
                        modele.gauche();
                    }
                });
            }
        });


        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
                                          @Override
                                          public boolean dispatchKeyEvent(KeyEvent e) {
                                              System.out.println("key event");
                                              if (e.getID() == KeyEvent.KEY_PRESSED) {
                                                  System.out.println(e.getKeyCode());
                                                  switch (e.getKeyCode()) {
                                                      case KeyEvent.VK_SPACE:
                                                          modele.getPieceCourante().modifRotation();
                                                          System.out.println("touche SPACE enfoncée pour le deplacement a gauche  ");
                                                          break;
                                                      case KeyEvent.VK_RIGHT:
                                                          modele.droite();
                                                          System.out.println("touche DROITE enfoncée pour le deplacement a droite ");
                                                          break;
                                                      case KeyEvent.VK_LEFT:
                                                          modele.gauche();
                                                          System.out.println("touche GAUCHE enfoncée pour le deplacement en haut ");
                                                          break;
                                                      case KeyEvent.VK_DOWN:
                                                          modele.bas();
                                                          System.out.println("touche BAS enfoncée pour le deplacement en bas  ");
                                                          break;
                                                      case KeyEvent.VK_UP:
                                                          modele.rotation();
                                                          System.out.println("touche HAUT enfoncée pour la rotation ");
                                                          break;
                                                  }

                                              }
                                              return false;
                                          }
                                      });
    }


    static long lastTime = System.currentTimeMillis();

    /**
     * Méthode de rafraichissement de la vue lorsqu'il y a des mises à jour dans le modèle (l'observable)
     */
    @Override
    public void update(Observable o, Object arg) {

        SwingUtilities.invokeLater(new Runnable() {
            //@Override
            public void run() {
                vueGrille.update(o, arg);
                vuePieceSuivante.repaint();
                jt.setText("Elapsed time : " + (System.currentTimeMillis() - lastTime) + "ms - x=" + modele.getPieceCourante().getx() + " y=" + modele.getPieceCourante().gety() + "\n"+ "Le score actuel est :"+(modele.getScore()));
                lastTime = System.currentTimeMillis();
            }
        });

    }

    /**
     * Méthode principale pour lancer l'application Tetris.
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    GrilleSimple m = new GrilleSimple();
                    VC vc = new VC(m);
                    vc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    m.addObserver(vc);
                    vc.setVisible(true);

                }
            }
        );
    }
}
