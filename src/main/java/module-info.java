module org.example.projectgestaltt {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.projectgestaltt to javafx.fxml;
    exports org.example.projectgestaltt;
}