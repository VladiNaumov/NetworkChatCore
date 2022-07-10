module com.example.clientfirst {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.naumdeveloper to javafx.fxml;
    exports com.naumdeveloper;
}