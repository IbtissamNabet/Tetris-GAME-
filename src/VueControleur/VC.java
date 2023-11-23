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

    JTextField jt = new JTextField("");
    JButton jb = new JButton("do");
    GrilleSimple modele;

    Observer vueGrille;
    private Executor ex =  Executors.newSingleThreadExecutor();

    public VC(GrilleSimple _modele) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        modele = _modele;

        setSize(350, 400);
        JPanel jp = new JPanel(new BorderLayout());


        jp.add(jt, BorderLayout.NORTH);
        jp.add(jb, BorderLayout.SOUTH);

        //vueGrille = new VueGrilleV1(modele); // composants swing, saccades
        vueGrille = new VueGrilleV2(modele); // composant AWT dédié

        jp.add((JPanel)vueGrille, BorderLayout.CENTER);
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

       /* jb.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("Key Pressed: " + e.getKeyChar());//évènement clavier : object contrôleur qui réceptionne
                super.keyPressed(e);
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        modele.droite();
                        System.out.println("touche SPACE enfoncée ");
                        break;
                    case KeyEvent.VK_RIGHT:
                        modele.droite();
                        System.out.println("touche DROITE enfoncée ");
                        break;
                    case KeyEvent.VK_LEFT:
                        modele.gauche();
                        System.out.println("touche GAUCHE enfoncée ");
                        break;
                    /*case KeyEvent.VK_DOWN:modele.bas();
                        System.out.println("touche BAS enfoncée ");
                    break;
                }

            }
        });*/

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
                                                          System.out.println("touche SPACE enfoncée ");
                                                          break;
                                                      case KeyEvent.VK_RIGHT:
                                                          modele.droite();
                                                          System.out.println("touche DROITE enfoncée ");
                                                          break;
                                                      case KeyEvent.VK_LEFT:
                                                          modele.gauche();
                                                          System.out.println("touche GAUCHE enfoncée ");
                                                          break;
                                                      case KeyEvent.VK_DOWN:
                                                          modele.bas();
                                                          System.out.println("touche BAS enfoncée ");
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

                jt.setText("Elapsed time : " + (System.currentTimeMillis() - lastTime) + "ms - x = " + modele.getPieceCourante().getx() + " y = " + modele.getPieceCourante().gety());
                lastTime = System.currentTimeMillis();

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
