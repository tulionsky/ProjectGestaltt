module org.example.projectgestaltt {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.antlr.antlr4.runtime;

    opens org.example.projectgestaltt to javafx.fxml;
    opens org.example.projectgestaltt.model to javafx.base;  // ← AGREGAR ESTA
    exports org.example.projectgestaltt;
    exports org.example.projectgestaltt.compiler;
    exports org.example.projectgestaltt.model;
}