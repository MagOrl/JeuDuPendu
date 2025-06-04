import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Contrôleur du chronomètre
 */
public class ControleurChronometre implements EventHandler<ActionEvent> {
    /**
     * temps enregistré lors du dernier événement
     */
    private long duree;
    /**
     * temps écoulé depuis le début de la mesure
     */
    private long tempsCourant;
    /**
     * Vue du chronomètre
     */
    private Chronometre chrono;

    /**
     * Constructeur du contrôleur du chronomètre
     * noter que le modèle du chronomètre est tellement simple
     * qu'il est inclus dans le contrôleur
     * @param chrono Vue du chronomètre
     */
    public ControleurChronometre (Chronometre chrono){
        this.chrono = chrono;
        this.duree = 0;
        this.tempsCourant = -1;

    }

    /**
     * Actions à effectuer tous les pas de temps
     * essentiellement mesurer le temps écoulé depuis la dernière mesure
     * et mise à jour de la durée totale
     * @param actionEvent événement Action
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        long heurSys = System.currentTimeMillis();
        if (this.tempsCourant != -1) {
            long tempsEcoule = heurSys - this.tempsCourant;
            this.duree += tempsEcoule;
            this.chrono.setTime(this.duree);
        }
        this.tempsCourant = heurSys;
    }

    /**
     * Remet la durée à 0
     */
    public void reset(){
        this.duree = 0;
        this.tempsCourant = -1;
    }
}
