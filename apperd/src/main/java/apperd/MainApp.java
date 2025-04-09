package apperd;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Device;
import persistence.DeviceRepository;
import service.DeviceService;

public class MainApp extends Application {

    private final DeviceService deviceService = new DeviceService(new DeviceRepository());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Application de vérification de vol");

        TabPane tabPane = new TabPane();

        // Onglet : Ajouter appareil
        Tab tabAjouter = new Tab("Signaler vol");
        tabAjouter.setContent(getAjouterPane());
        tabAjouter.setClosable(false);

        // Onglet : Vérifier appareil
        Tab tabVerifier = new Tab("Vérifier appareil");
        tabVerifier.setContent(getVerifierPane());
        tabVerifier.setClosable(false);

        tabPane.getTabs().addAll(tabAjouter, tabVerifier);

        Scene scene = new Scene(tabPane, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Interface pour ajouter un appareil
    private VBox getAjouterPane() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        TextField tfId = new TextField();
        tfId.setPromptText("Identifiant (IMEI, numéro, etc.)");

        TextField tfType = new TextField();
        tfType.setPromptText("Type (Téléphone, Laptop, etc.)");

        TextField tfMarque = new TextField();
        tfMarque.setPromptText("Marque");

        TextField tfOwnerEmail = new TextField();
        tfOwnerEmail.setPromptText("Email du propriétaire");

        Button btnAjouter = new Button("Ajouter appareil");
        Label lblMessage = new Label();

        btnAjouter.setOnAction(e -> {
            String id = tfId.getText().trim();
            String type = tfType.getText().trim();
            String marque = tfMarque.getText().trim();
            String ownerEmail = tfOwnerEmail.getText().trim();

            if (id.isEmpty() || type.isEmpty() || marque.isEmpty()) {
                lblMessage.setText("Veuillez remplir tous les champs obligatoires.");
                return;
            }
            Device device = new Device(id, type, marque, ownerEmail);
            deviceService.ajouterDevice(device);

            deviceService.signalerVol(id);
            lblMessage.setText("Vol signalé");
            
            tfId.clear();
            tfType.clear();
            tfMarque.clear();
            tfOwnerEmail.clear();
        });

        vbox.getChildren().addAll(
            new Label("Ajouter un nouvel appareil"),
            tfId, tfType, tfMarque, tfOwnerEmail,
            btnAjouter, lblMessage
        );

        return vbox;
    }

    // Interface pour vérifier l'état d'un appareil
    private VBox getVerifierPane() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        TextField tfId = new TextField();
        tfId.setPromptText("Identifiant de l'appareil");

        Button btnVerifier = new Button("Vérifier appareil");
        Label lblResult = new Label();

        btnVerifier.setOnAction(e -> {
            String id = tfId.getText().trim();
            if (id.isEmpty()) {
                lblResult.setText("Veuillez entrer un identifiant.");
                return;
            }
            boolean vole = deviceService.isDeviceVole(id);
            lblResult.setText(vole ? "⚠️ Cet appareil a été signalé volé !" : "✅ Cet appareil n'est pas déclaré volé.");
        });

        vbox.getChildren().addAll(
            new Label("Vérifier l'état d'un appareil"),
            tfId, btnVerifier, lblResult
        );
        return vbox;
    }
}
