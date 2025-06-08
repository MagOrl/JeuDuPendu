import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;

public class ControleurSetTimer implements EventHandler<ActionEvent> {

    /**
     * vue du jeu
     */
    private Pendu vuePendu;

    /**
     * @param modelePendu modèle du jeu
     * @param vuePendu vue du jeu
     */
    public ControleurSetTimer(Pendu vuePendu) {
        this.vuePendu = vuePendu;
    }

    @Override
    public void handle(ActionEvent arg0) {
        RadioButton rb = (RadioButton) arg0.getTarget();
        switch (rb.getText()) {
            case "Pas de timer":
                this.vuePendu.setTimer(-1);
                break;
            case "1 minutes":
                this.vuePendu.setTimer(60000);
                break;
            case "2 minutes":
                this.vuePendu.setTimer(120000);
                break;
            case "3 minutes":
                this.vuePendu.setTimer(180000);
                break;

        }
    }

}
