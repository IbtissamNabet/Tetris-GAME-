package VueControleur;

import Modele.GrilleSimple;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class VC extends JFrame implements Observer {
    //JTextField jt = new JTextField("");
    JTextArea jt = new JTextArea("");
    JTextField jscore = new JTextField("");
    JButton jb = new JButton("do");

    VuePieceSuivante vuePieceSuivante;
    GrilleSimple modele;

    Observer vueGrille;
    private Executor ex =  Executors.newSingleThreadExecutor();

    public VC(GrilleSimple _modele) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        modele = _modele;

        setSize(450, 400);
        JPanel jp = new JPanel(new BorderLayout());
        Color backgroundColor = Color.GRAY;  // Choisissez la couleur que vous souhaitez
        Color buttonColor = Color.GREEN;
        Color textColor = Color.RED;
        Color font = Color.BLACK;

        // Appliquez les couleurs à vos composants
        jp.setBackground(backgroundColor);
        jb.setBackground(buttonColor);
        jt.setBackground(textColor);
        jscore.setBackground(textColor);

        // Configurer le JTextArea avec enroulement automatique
        jt.setLineWrap(true);
        jt.setWrapStyleWord(true); // Enrouler au mot
        // Configurer la position du text dans le JTextArea
        jt.setAlignmentX(Component.CENTER_ALIGNMENT);
        jt.setAlignmentY(Component.CENTER_ALIGNMENT);
        // Configurer la taille préférée du JTextArea
        jt.setPreferredSize(new Dimension(100, 400));
        jt.setForeground(font);

        jp.add(jt, BorderLayout.WEST);
        jp.add(jb, BorderLayout.SOUTH);
        jp.add(jscore, BorderLayout.NORTH);
        //vueGrille = new VueGrilleV1(modele); // composants swing, saccades
        vueGrille = new VueGrilleV2(modele); // composant AWT dédié

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
                                                          modele.getPieceCourante().rotation();
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

    @Override
    public void update(Observable o, Object arg) { // rafraichissement de la vue

        SwingUtilities.invokeLater(new Runnable() {
            //@Override
            public void run() {
                vueGrille.update(o, arg);
                vuePieceSuivante.repaint();
                jt.setText("Elapsed time : " + (System.currentTimeMillis() - lastTime) + "ms - x=" + modele.getPieceCourante().getx() + " y=" + modele.getPieceCourante().gety());
                lastTime = System.currentTimeMillis();
                jscore.setText("Le score actuel est :"+(modele.getScore()));
            }
        });

    }



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
