module org.example.projectgestaltt {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.antlr.antlr4.runtime;

    opens org.example.projectgestaltt to javafx.fxml;
    exports org.example.projectgestaltt;
    exports org.example.projectgestaltt.compiler;
    exports org.example.projectgestaltt.model;
}