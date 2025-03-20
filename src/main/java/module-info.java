module dungnguyen.protunefinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires java.desktop;
    requires javafx.media;
    requires com.google.gson;


    opens dungnguyen.protunefinal to javafx.fxml;
    opens dungnguyen.protunefinal.controllers to javafx.fxml;
    opens dungnguyen.protunefinal.views.fxml to javafx.fxml;
    opens dungnguyen.protunefinal.models to com.google.gson;
    exports dungnguyen.protunefinal;
    exports dungnguyen.protunefinal.controllers;
    exports dungnguyen.protunefinal.models;
}