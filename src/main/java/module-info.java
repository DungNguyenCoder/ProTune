module dungnguyen.protunefinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires java.desktop;
    requires java.sql;


    opens dungnguyen.protunefinal to javafx.fxml;
    opens dungnguyen.protunefinal.controllers to javafx.fxml;
    opens dungnguyen.protunefinal.views.fxml to javafx.fxml;
    exports dungnguyen.protunefinal;
    exports dungnguyen.protunefinal.controllers;
}