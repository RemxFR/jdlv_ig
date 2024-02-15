module org.ynov.jdlv_ig {
    requires javafx.controls;
    requires javafx.fxml;

    requires httpcore;
    requires httpclient;
    requires java.net.http;
    requires static lombok;
    requires org.json;

    opens org.ynov.jdlv_ig to javafx.fxml;
    exports org.ynov.jdlv_ig;
    exports org.ynov.jdlv_ig.logique;
    opens org.ynov.jdlv_ig.logique to javafx.fxml;

}