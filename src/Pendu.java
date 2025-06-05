
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.text.TextAlignment;
import java.util.List;
import java.util.Arrays;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Vue du jeu du pendu
 */
public class Pendu extends Application {

    /**
     * modèle du jeu
     *
     */
    private MotMystere modelePendu;
    /**
     * Liste qui contient les images du jeu
     */
    private ArrayList<Image> lesImages;
    /**
     * Liste qui contient les noms des niveaux
     */
    public List<String> niveaux;

    // les différents contrôles qui seront mis à jour ou consultés pour l'affichage
    /**
     * le dessin du pendu
     */
    private ImageView dessin;
    /**
     * le mot à trouver avec les lettres déjà trouvé
     */
    private Text motCrypte;
    /**
     * la barre de progression qui indique le nombre de tentatives
     */
    private ProgressBar pg;
    /**
     * le clavier qui sera géré par une classe à implémenter
     */
    private Clavier clavier;
    /**
     * le text qui indique le niveau de difficulté
     */
    private Text leNiveau;
    /**
     * le chronomètre qui sera géré par une clasee à implémenter
     */
    private Chronometre chrono;
    /**
     * le panel Central qui pourra être modifié selon le mode (accueil ou jeu)
     */
    private BorderPane fenetre;
    /**
     * le bouton Paramètre / Engrenage
     */
    private Button boutonParametres;
    /**
     * le bouton Accueil / Maison
     */
    private Button boutonMaison;
    /**
     * le bouton qui permet de (lancer ou relancer une partie
     */
    private Button boutonInfo;

    private Set<String> lettreFausse;

    /**
     * initialise les attributs (créer le modèle, charge les images, crée le
     * chrono ...)
     */
    @Override
    public void init() {
        this.fenetre = new BorderPane();
        this.modelePendu = new MotMystere("/usr/share/dict/french", 3, 10, MotMystere.FACILE, 10);
        this.lesImages = new ArrayList<Image>();
        this.chargerImages("./img");
        ImageView img1 = new ImageView(new Image("../img/home.png"));
        ImageView img2 = new ImageView(new Image("../img/parametres.png"));
        ImageView img3 = new ImageView(new Image("../img/info.png"));
        img1.setFitHeight(30.);
        img1.setFitWidth(30.);
        img2.setFitHeight(30.);
        img2.setFitWidth(30.);
        img3.setFitHeight(30.);
        img3.setFitWidth(30.);
        this.boutonMaison = new Button("", img1);
        this.boutonParametres = new Button("", img2);
        this.boutonInfo = new Button("", img3);
        this.boutonMaison.setOnAction(new RetourAccueil(modelePendu, this));
        this.boutonParametres.setOnAction(new ControleurParametre(this));
        this.boutonInfo.setOnAction(new ControleurInfos(this));
        this.clavier = new Clavier("ABCDEFGHIJKLMNOPQRSTUVWXYZ-", new ControleurLettres(this.modelePendu, this));
        this.dessin = new ImageView();
        this.niveaux = Arrays.asList("Facile", "Médium", "Difficile", "Expert");
        this.pg = new ProgressBar();

    }

    /**
     * @return le graphe de scène de la vue à partir de methodes précédantes
     */
    private Scene laScene() {
        fenetre.setTop(this.titre());
        return new Scene(this.fenetre, 800, 1000);
    }

    /**
     * @return le panel contenant le titre du jeu
     */
    private Pane titre() {
        BorderPane banniere = new BorderPane();
        Text txt = new Text("jeu du Pendu");
        txt.setFont(Font.font("Arial", 30));
        banniere.setLeft(txt);
        banniere.setStyle("-fx-background-color:rgb(179, 179, 226);");
        banniere.setPadding(new Insets(20));
        HBox hbox = new HBox(3);
        hbox.getChildren().addAll(this.boutonMaison, this.boutonParametres, this.boutonInfo);
        hbox.setAlignment(Pos.CENTER);
        banniere.setRight(hbox);
        return banniere;
    }

    private Pane fenetreJeu() {
        BorderPane bp = new BorderPane();
        boutonMaison.setDisable(false);
        boutonParametres.setDisable(true);
        boutonInfo.setDisable(false);
        bp.setCenter(centerJeu());
        bp.setRight(rightJeu());
        bp.setPadding(new Insets(15));
        return bp;
    }

    private VBox centerJeu() {
        VBox vbcenter = new VBox(20);
        vbcenter.setFillWidth(false);
        this.motCrypte = new Text(modelePendu.getMotCrypte());
        this.motCrypte.setFont(Font.font("Arial", 25));
        vbcenter.getChildren().addAll(this.motCrypte, this.dessin, this.pg, this.clavier);
        vbcenter.setAlignment(Pos.TOP_CENTER);
        return vbcenter;
    }

    private VBox rightJeu() {
        VBox vbright = new VBox(50);
        this.leNiveau = new Text("Niveau " + this.niveaux.get(modelePendu.getNiveau()));
        this.leNiveau.setFont(Font.font("Arial", 21));
        Button nvPartie = new Button("relancer une partie");
        nvPartie.setOnAction(new ControleurLancerPartie(modelePendu, this));
        vbright.getChildren().addAll(this.leNiveau, this.chrono(), nvPartie);
        return vbright;
    }

    private TitledPane chrono() {
        TitledPane tp = new TitledPane("Chronomètre", this.chrono);
        tp.setCollapsible(false);
        return tp;
    }

    // /**
    // * @return la fenêtre d'accueil sur laquelle on peut choisir les paramètres de
    // jeu
    // */
    private Pane fenetreAccueil() {
        boutonMaison.setDisable(true);
        boutonParametres.setDisable(false);
        boutonInfo.setDisable(false);
        VBox vb = new VBox(20);
        Button bt = new Button("Lancer une partie");
        ToggleGroup tg = new ToggleGroup();
        RadioButton rb1 = new RadioButton("Facile");
        RadioButton rb2 = new RadioButton("Médium");
        RadioButton rb3 = new RadioButton("Difficile");
        RadioButton rb4 = new RadioButton("Expert");
        rb1.setOnAction(new ControleurNiveau(modelePendu));
        rb2.setOnAction(new ControleurNiveau(modelePendu));
        rb3.setOnAction(new ControleurNiveau(modelePendu));
        rb4.setOnAction(new ControleurNiveau(modelePendu));
        bt.setOnAction(new ControleurLancerPartie(modelePendu, this));
        rb1.setToggleGroup(tg);
        rb3.setToggleGroup(tg);
        rb2.setToggleGroup(tg);
        rb4.setToggleGroup(tg);
        VBox radiobox = new VBox(10);
        radiobox.getChildren().addAll(rb1, rb2, rb3, rb4);
        TitledPane tp = new TitledPane("Niveau de difficulté", radiobox);
        tp.setCollapsible(false);
        vb.getChildren().addAll(bt, tp);
        vb.setPadding(new Insets(15));
        return vb;
    }

    /**
     * charge les images à afficher en fonction des erreurs
     *
     * @param repertoire répertoire où se trouvent les images
     */
    private void chargerImages(String repertoire) {
        for (int i = 0; i < this.modelePendu.getNbErreursMax() + 1; i++) {
            File file = new File(repertoire + "/pendu" + i + ".png");
            System.out.println(file.toURI().toString());
            this.lesImages.add(new Image(file.toURI().toString()));
        }
    }

    public void modeAccueil() {
        this.fenetre.setCenter(fenetreAccueil());
    }

    public void modeJeu() {
        this.fenetre.setCenter(fenetreJeu());
    }

    public void ajtLettreFausse(String c) {
        this.lettreFausse.add(c);
        this.clavier.desactiveTouches(this.lettreFausse);
    }

    public void modeParametres() {
        // A implémenter
    }

    /**
     * lance une partie
     */
    public void lancePartie() {
        this.chrono = new Chronometre();
        this.lettreFausse = new HashSet<>();
        this.dessin.setImage(lesImages.get(0));
        this.modeJeu();
    }

    /**
     * raffraichit l'affichage selon les données du modèle
     */
    public void majAffichage() {
        this.motCrypte.setText(modelePendu.getMotCrypte());
        this.dessin.setImage(lesImages.get(modelePendu.getNbErreursMax() - modelePendu.getNbErreursRestants()));
        this.pg.setProgress((float) ((float) 1
                - ((float) modelePendu.getNbErreursRestants() / (float) modelePendu.getNbErreursMax())));
        if (modelePendu.gagne()) {
            this.chrono.stop();
            this.popUpMessageGagne().showAndWait();
        } else if (modelePendu.perdu()) {
            this.chrono.stop();
            this.popUpMessagePerdu().showAndWait();
        }
    }

    public void lanceChrono() {
        this.chrono.start();
    }

    /**
     * accesseur du chronomètre (pour les controleur du jeu)
     *
     * @return le chronomètre du jeu
     */
    public Chronometre getChrono() {
        return this.chrono;
    }

    public Alert popUpPartieEnCours() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Êtes vous sûr de commencer une nouvelle partie ?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Attention");
        return alert;
    }

    public Alert popUpReglesDuJeu() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Text txt = new Text("Le but est de deviner un mot ou une phrase en proposant des lettres. À chaque erreur, le dessin d'un pendu se complète jusqu'à ce que le mot soit trouvé ou que le dessin soit terminé.");
        txt.setWrappingWidth(300);
        alert.getDialogPane().setContent(txt);
        alert.setTitle("Les règles du pendu");
        return alert;
    }

    public Alert popUpMessageGagne() {
        // A implementer
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Text txt = new Text("Bravo ! Vous avez gagné, cela vous à prit " + modelePendu.getNbEssais() + " tentatives");
        alert.getDialogPane().setContent(txt);
        txt.setWrappingWidth(300);
        ImageView icon = new ImageView("../img/gagner.gif");
        icon.setFitHeight(48);
        icon.setFitWidth(48);
        alert.getDialogPane().setGraphic(icon);
        alert.setTitle("Bravo!");
        return alert;
    }

    public Alert popUpMessagePerdu() {
        Alert alert;
        String humiliation = modelePendu.getNiveau() == 0
                ? " pourtant le jeu était mit en facile tes vraiment nul enfet."
                : " met une difficulté plus basse si tu trouve ça trop dure.";
        Text txt = new Text("Oh non! Tu à perdu... le mot était "
                + modelePendu.getMotATrouve() + humiliation);
        txt.setWrappingWidth(300);
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().setContent(txt);
        ImageView icon = new ImageView("../img/perdu.jpg");
        icon.setFitHeight(48);
        icon.setFitWidth(48);
        alert.getDialogPane().setGraphic(icon);
        alert.setTitle("Aie..");

        return alert;
    }

    /**
     * créer le graphe de scène et lance le jeu
     *
     * @param stage la fenêtre principale
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("IUTEAM'S - La plateforme de jeux de l'IUTO");
        stage.setScene(this.laScene());
        this.modeAccueil();
        stage.show();
    }

    public void reset() {
        this.modelePendu.setMotATrouver();
        clavier.reset();
        this.pg.setProgress(0);
        majAffichage();
    }

    /**
     * Programme principal
     *
     * @param args inutilisé
     */
    public static void main(String[] args) {
        launch(args);
    }

}
