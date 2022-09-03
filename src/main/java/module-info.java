module com.controller {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;


    opens com.controller to javafx.fxml;
    opens com.dao;
    opens com.entity;
    exports com.controller;
    exports com.entity;



}