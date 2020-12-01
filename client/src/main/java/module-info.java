module client {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires io.netty.all;

    opens com.cloudcastle.client.controller to javafx.fxml;
    exports com.cloudcastle.client;
}