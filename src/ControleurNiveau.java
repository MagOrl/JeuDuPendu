
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;

/**
 * Controleur des radio boutons gérant le niveau
 */
public class ControleurNiveau implements EventHandler<ActionEvent> {

    /**
     * modèle du jeu
     */
    private MotMystere modelePendu;

    /**
     * @param modelePendu modèle du jeu
     */
    public ControleurNiveau(MotMystere modelePendu) {
        this.modelePendu = modelePendu;
    }

    /**
     * gère le changement de niveau
     *
     * @param actionEvent
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        // A implémenter
        RadioButton radiobouton = (RadioButton) actionEvent.getTarget();
        String nomDuRadiobouton = radiobouton.getText();
        switch (nomDuRadiobouton) {
            case "Facile":
                modelePendu.setNiveau(0);
                break;
            case "Médium":
                modelePendu.setNiveau(1);
                break;
            case "Difficile":
                modelePendu.setNiveau(2);
                break;
            case "Expert":
                modelePendu.setNiveau(3);
                break;
            default:
                System.err.println("queleque chose de mal c'est passer");
        }
    }
}
