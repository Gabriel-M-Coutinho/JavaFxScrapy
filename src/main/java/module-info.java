module com.example.teste {
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
    requires org.apache.poi.ooxml;
    requires jdk.httpserver;
    requires okhttp3;
    requires com.fasterxml.jackson.databind;
    requires org.seleniumhq.selenium.chrome_driver;
    requires org.seleniumhq.selenium.firefox_driver;
    requires io.github.bonigarcia.webdrivermanager;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;

    opens com.example.teste to javafx.fxml;
    exports com.example.teste;
}