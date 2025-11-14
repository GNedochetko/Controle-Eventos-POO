module br.unicentro.appeventos {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires transitive java.sql;
    requires javafx.graphics;
    requires javafx.base;
    opens br.unicentro.appeventos.controller to javafx.fxml;
    exports br.unicentro.appeventos.controller;
    opens br.unicentro.appeventos.model to javafx.fxml;
    exports br.unicentro.appeventos.model;
    opens br.unicentro.appeventos.main to javafx.fxml;
    exports br.unicentro.appeventos.main;
    opens br.unicentro.appeventos.dao to javafx.fxml;
    exports br.unicentro.appeventos.dao;
}