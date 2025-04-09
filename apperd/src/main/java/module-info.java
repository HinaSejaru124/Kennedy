module apperd {
    requires javafx.controls;
    requires javafx.fxml;

    opens apperd to javafx.fxml;
    exports apperd;
}
