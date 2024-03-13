module org.ynov.jdlv_ig {
    requires javafx.controls;
    requires javafx.fxml;

    requires httpcore;
    requires httpclient;
    requires java.net.http;
    requires static lombok;
    requires org.json;
    requires com.google.gson;
    requires org.apache.commons.lang3;

    opens org.ynov.jdlv_ig to javafx.fxml;
    exports org.ynov.jdlv_ig;
    exports org.ynov.jdlv_ig.entity;
    opens org.ynov.jdlv_ig.entity to javafx.fxml;
    exports org.ynov.jdlv_ig.http_controller;
    opens org.ynov.jdlv_ig.http_controller to javafx.fxml;

}