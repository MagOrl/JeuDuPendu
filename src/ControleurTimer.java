import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurTimer implements EventHandler<ActionEvent> {
    private Timer timer;

    private long temps;

    private long tempsCourant;


    private long duree;

    private Pendu vuePendu;
    /**
     * 
     * @param t la vue
     * @param temps le temps max que l'utilisateur à choisi 
     * @param vuePendu l'application
     */
    public ControleurTimer(Timer t, long temps,Pendu vuePendu) {
        this.timer = t;
        this.temps = temps;
        this.tempsCourant = -1;
        this.duree = 0;
        this.vuePendu = vuePendu;
    }

    @Override
    public void handle(ActionEvent arg0) {
        long heurSys = System.currentTimeMillis();
        if (this.temps - this.duree <= 0) {
            this.vuePendu.perduChrono();
        }
         if (tempsCourant != -1) {
            timer.setTime(heurSys);
            long tempEcoule = heurSys - this.tempsCourant;
            this.duree += tempEcoule;
            this.timer.setTime(this.temps - this.duree);
        }
        tempsCourant = heurSys;

    }
    /**
     * Remet la durée à 0
     */
    public void reset(){
        this.duree = 0;
        this.tempsCourant = -1;
    }
}


