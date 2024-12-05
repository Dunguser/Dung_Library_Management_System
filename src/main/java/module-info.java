module org.example.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.checkerframework.checker.qual;
    requires jdk.unsupported.desktop;
    requires org.json;
    requires java.sql;
    requires com.google.gson;
    requires java.net.http;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires jakarta.mail;

    exports org.model.person;
    opens org.model.person to javafx.fxml;
    exports org.model.book;
    opens org.model.book to javafx.fxml;
    exports org.model.account;
    opens org.model.account to javafx.fxml;
    exports org.model.librarian;
    opens org.model.librarian to javafx.fxml;
    exports org.model.operator;
    opens org.model.operator to javafx.fxml;
    exports org.model.reader;
    opens org.model.reader to javafx.fxml;
    exports org.controller;
    opens org.controller to javafx.fxml;
    exports org.model.googlebookapi;
    opens org.model.googlebookapi to javafx.fxml;
    exports org.model.qrcode;
    opens org.model.qrcode to javafx.fxml;
    exports org;
    opens org to javafx.fxml;
    exports org.model;
    opens org.model to javafx.fxml;
}

