package Modele;

/**
 * La classe OrdonnanceurSimple permet de planifier l'exécution périodique d'une tâche (Runnable).
 * Elle étend la classe Thread et utilise un Runnable pour exécuter la tâche périodiquement.
 */
public class OrdonnanceurSimple extends Thread {
    public Runnable monRunnable;

    /**
     * Constructeur de la classe OrdonnanceurSimple.
     * @param _monRunnable La tâche à exécuter périodiquement.
     */
    public OrdonnanceurSimple(Runnable _monRunnable) {
        monRunnable = _monRunnable;
    }

    /**
     * Méthode principale du thread.
     * Planifie l'exécution périodique de la tâche spécifiée dans le constructeur.
     * Utilise une pause de 250 millisecondes entre chaque exécution.
     */
    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            monRunnable.run();


        }
    }
}
