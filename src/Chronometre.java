
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Permet de gérer un Text associé à une Timeline pour afficher un temps écoulé
 */
public class Chronometre extends Text {

    /**
     * timeline qui va gérer le temps
     */
    private Timeline timeline;
    /**
     * la fenêtre de temps
     */
    private KeyFrame keyFrame;
    /**
     * le contrôleur associé au chronomètre
     */
    private ControleurChronometre actionTemps;

    /**
     * Constructeur permettant de créer le chronomètre avec un label initialisé
     * à "0:0:0" Ce constructeur créer la Timeline, la KeyFrame et le contrôleur
     */
    public Chronometre() {
        super();
        this.actionTemps = new ControleurChronometre(this);
        this.keyFrame = new KeyFrame(Duration.millis(100), this.actionTemps);
        this.timeline = new Timeline(this.keyFrame);
        this.timeline.setCycleCount(Timeline.INDEFINITE);
        this.setFont(Font.font("Arial", 21));
        this.timeline.play();
    }

    /**
     * Permet au controleur de mettre à jour le text la durée est affichée sous
     * la forme m:s
     *
     * @param tempsMillisec la durée depuis à afficher
     */
    public void setTime(long tempsMillisec) {
        String res = "";
        float min = 0;
        float seconde = 0;
        if (tempsMillisec >= 60000) {
            min = (float) tempsMillisec / 60000;
            seconde = (float) ((min - (int) min)) * 60;
            res += (int) min + " min " + (int) seconde + " s";
        } else {
            res += (int) (tempsMillisec * 0.001) + " s";
        }
        this.setText(res);
    }

    /**
     * Permet de démarrer le chronomètre
     */
    public void start() {
        timeline.play();
    }

    /**
     * Permet d'arrêter le chronomètre
     */
    public void stop() {
        timeline.stop();
    }

    /**
     * Permet de remettre le chronomètre à 0
     */
    public void resetTime() {
        this.actionTemps.reset();
    }
}
