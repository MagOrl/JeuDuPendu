
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Circle;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javafx.scene.shape.Circle;

/**
 * Génère la vue d'un clavier et associe le contrôleur aux touches le choix ici
 * est d'un faire un héritié d'un TilePane
 */
public class Clavier extends TilePane {

    /**
     * il est conseillé de stocker les touches dans un ArrayList
     */
    private List<Button> clavier;

    /**
     * constructeur du clavier
     *
     * @param touches une chaine de caractères qui contient les lettres à mettre
     * sur les touches
     * @param actionTouches le contrôleur des touches
     * @param tailleLigne nombre de touches par ligne
     */
    public Clavier(String touches, EventHandler<ActionEvent> actionTouches) {
        this.clavier = new ArrayList<>();
        this.setPrefRows(3);
        this.setPrefColumns(8);
        for (int i = 0; i < touches.length(); ++i) {
            Button bt = new Button(touches.charAt(i) + "");
            Circle circle = new Circle(1.5,25.,25.);
            bt.setShape(circle);
            clavier.add(bt);
            clavier.get(i).setOnAction(actionTouches);
            this.getChildren().add(bt);
        }
    }

    /**
     * permet de désactiver certaines touches du clavier (et active les autres)
     *
     * @param touchesDesactivees une chaine de caractères contenant la liste des
     * touches désactivées
     */
    public void desactiveTouches(Set<String> touchesDesactivees) {
        for (String touch : touchesDesactivees) {
            for (Button bt : this.clavier) {
                if (bt.getText().equals(touch)) {
                    bt.setDisable(true);
                }
            }
        }
    }

    public void reset() {
        for (Button b : clavier) {
            b.setDisable(false);
        }
    }
}
