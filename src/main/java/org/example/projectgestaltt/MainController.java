package org.example.projectgestaltt;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.example.projectgestaltt.compiler.GestaltCompiler;
import org.example.projectgestaltt.compiler.TreeVisualizer;
import org.example.projectgestaltt.model.*;

import java.io.*;
import java.nio.file.Files;

public class MainController {

    // ── Editor de código ─────────────────────────────────────────────────────
    @FXML private TextArea editorCodigo;

    // ── Estado ───────────────────────────────────────────────────────────────
    @FXML private Label lblEstado;

    // ── Tabla de tokens ──────────────────────────────────────────────────────
    @FXML private TableView<TokenInfo>             tablaTokens;
    @FXML private TableColumn<TokenInfo, String>   colTipoToken;
    @FXML private TableColumn<TokenInfo, String>   colValorToken;
    @FXML private TableColumn<TokenInfo, Integer>  colLineaToken;
    @FXML private TableColumn<TokenInfo, Integer>  colColumnaToken;

    // ── Tabla de errores ─────────────────────────────────────────────────────
    @FXML private TableView<ErrorInfo>             tablaErrores;
    @FXML private TableColumn<ErrorInfo, String>   colTipoError;
    @FXML private TableColumn<ErrorInfo, Integer>  colLineaError;
    @FXML private TableColumn<ErrorInfo, Integer>  colColumnaError;
    @FXML private TableColumn<ErrorInfo, String>   colDescError;

    // ── Tabla de símbolos ────────────────────────────────────────────────────
    @FXML private TableView<SimboloInfo>            tablaSimbolos;
    @FXML private TableColumn<SimboloInfo, String>  colNombreSim;
    @FXML private TableColumn<SimboloInfo, String>  colTipoSim;
    @FXML private TableColumn<SimboloInfo, String>  colAmbitoSim;
    @FXML private TableColumn<SimboloInfo, Integer> colLineaSim;
    @FXML private TableColumn<SimboloInfo, String>  colValorSim;

    // ── Árbol guardado ───────────────────────────────────────────────────────
    private ParseTree lastParseTree = null;
    private Parser    lastParser    = null;
    private boolean lastNoFunca =  false;

    private final GestaltCompiler compiler = new GestaltCompiler();

    // ── Inicialización ───────────────────────────────────────────────────────
    @FXML
    public void initialize() {
        // Tokens
        colTipoToken   .setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colValorToken  .setCellValueFactory(new PropertyValueFactory<>("valor"));
        colLineaToken  .setCellValueFactory(new PropertyValueFactory<>("linea"));
        colColumnaToken.setCellValueFactory(new PropertyValueFactory<>("columna"));

        // Errores
        colTipoError   .setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colLineaError  .setCellValueFactory(new PropertyValueFactory<>("linea"));
        colColumnaError.setCellValueFactory(new PropertyValueFactory<>("columna"));
        colDescError   .setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        // Símbolos
        colNombreSim.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipoSim  .setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colAmbitoSim.setCellValueFactory(new PropertyValueFactory<>("ambito"));
        colLineaSim .setCellValueFactory(new PropertyValueFactory<>("linea"));
        colValorSim .setCellValueFactory(new PropertyValueFactory<>("valor"));

        // Código de ejemplo al abrir
        editorCodigo.setText(codigoEjemplo());
    }

    // ── Acción: Compilar ─────────────────────────────────────────────────────
    @FXML
    private void onCompilar() {
        String codigo = editorCodigo.getText();
        if (codigo == null || codigo.isBlank()) {
            lblEstado.setText("⚠ El editor está vacío.");
            return;
        }

        CompilerResult resultado = compiler.analizar(codigo);

        // Tokens
        tablaTokens.setItems(
                FXCollections.observableArrayList(resultado.getTokens()));

        // Errores
        tablaErrores.setItems(
                FXCollections.observableArrayList(resultado.getErrores()));

        // Símbolos
        tablaSimbolos.setItems(
                FXCollections.observableArrayList(resultado.getSimbolos()));

        // Guardar árbol para mostrar después
        lastParseTree = resultado.getParseTree();
        lastParser    = resultado.getParser();
        lastNoFunca = !resultado.isExitoso();

        // Estado
        if (resultado.isExitoso()) {
            lblEstado.setText("NO ERROR — "
                    + resultado.getTokens().size() + " TOKENS");
            lblEstado.getStyleClass().setAll("status-ok");
        } else {
            int nErr = resultado.getErrores().size();
            lblEstado.setText(nErr + " ERROR(S) FOUND");
            lblEstado.getStyleClass().setAll("status-error");
        }
    }

    // ── Acción: Ver árbol en ventana nueva ───────────────────────────────────
    @FXML
    private void onVerArbol() {
        if (lastParseTree == null) {
            mostrarAlerta("Sin árbol",
                    "Primero debes compilar un programa.");
            return;
        }

        if (lastNoFunca) {
            mostrarAlerta("Errores encontrados",
                    "No se puede mostrar el árbol porque el programa contiene errores.\n" +
                            "Corrija los errores y vuelva a compilar.");
            return;
        }

        // Crear el árbol gráfico
        Pane treePane = TreeVisualizer.draw(lastParseTree, lastParser);

        // ScrollPane para hacer scroll
        ScrollPane scroll = new ScrollPane(treePane);
        scroll.setStyle(
                "-fx-background-color: #bcb89c; -fx-background: #bcb89c;");
        scroll.setPrefSize(1100, 700);
        scroll.setFitToWidth(false);
        scroll.setFitToHeight(false);

        // Header
        HBox header = new HBox();
        header.setStyle("-fx-background-color: #2e2a1e; -fx-padding: 10 16;");
        Label titulo = new Label("❖ PARSE TREE — Project Gestalt");
        titulo.setStyle(
                "-fx-text-fill: #c8c4a8; -fx-font-weight: bold; " +
                        "-fx-font-size: 13px; -fx-font-family: 'Courier New';");
        header.getChildren().add(titulo);

        // Línea decorativa
        Label dotLine = new Label(
                "· · · · · · · · · · · · · · · · · · · · · · · · · · · · · " +
                        "· · · · · · · · · · · · · · · · · · · · · · · · · · · · · ·");
        dotLine.setMaxWidth(Double.MAX_VALUE);
        dotLine.setStyle(
                "-fx-text-fill: #9a9070; -fx-font-size: 10px; " +
                        "-fx-padding: 2 8; -fx-background-color: #b8b49a;");

        // Layout
        VBox layout = new VBox(0, header, dotLine, scroll);
        layout.setStyle("-fx-background-color: #bcb89c;");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        // Ventana nueva
        Stage stage = new Stage();
        stage.setTitle("Parse Tree — Project Gestalt");
        stage.initModality(Modality.NONE);
        javafx.scene.image.Image icono = new javafx.scene.image.Image(
                getClass().getResourceAsStream("/org/example/projectgestaltt/icon.png"));
        stage.getIcons().add(icono);
        stage.setScene(new Scene(layout));
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        stage.show();
    }

    // ── Acción: Limpiar ──────────────────────────────────────────────────────
    @FXML
    private void onLimpiar() {
        editorCodigo.clear();
        tablaTokens.getItems().clear();
        tablaErrores.getItems().clear();
        tablaSimbolos.getItems().clear();
        lastParseTree = null;
        lastParser    = null;
        lastNoFunca = false;
        lblEstado.setText("NO ERROR");
        lblEstado.getStyleClass().setAll("status-ok");
    }

    // ── Acción: Abrir archivo ────────────────────────────────────────────────
    @FXML
    private void onAbrir() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Abrir archivo Gestalt");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter(
                        "Archivos Gestalt", "*.gt", "*.gestalt", "*.txt"));
        File f = fc.showOpenDialog(editorCodigo.getScene().getWindow());
        if (f != null) {
            try {
                editorCodigo.setText(Files.readString(f.toPath()));
            } catch (IOException e) {
                mostrarAlerta("Error al leer archivo", e.getMessage());
            }
        }
    }

    // ── Acción: Guardar archivo ──────────────────────────────────────────────
    @FXML
    private void onGuardar() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar archivo Gestalt");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos Gestalt", "*.gt"));
        fc.setInitialFileName("programa.gt");
        File f = fc.showSaveDialog(editorCodigo.getScene().getWindow());
        if (f != null) {
            try {
                Files.writeString(f.toPath(), editorCodigo.getText());
            } catch (IOException e) {
                mostrarAlerta("Error al guardar archivo", e.getMessage());
            }
        }
    }

    // ── Acción: Cargar ejemplo ───────────────────────────────────────────────
    @FXML
    private void onEjemplo() {
        editorCodigo.setText(codigoEjemplo());
    }

    // ── Helpers ──────────────────────────────────────────────────────────────
    private void mostrarAlerta(String titulo, String mensaje) {
        Stage alertStage = new Stage();
        alertStage.initModality(Modality.APPLICATION_MODAL);
        alertStage.setTitle(titulo);
        alertStage.setResizable(false);
        javafx.scene.image.Image icono = new javafx.scene.image.Image(
                getClass().getResourceAsStream("/org/example/projectgestaltt/icon.png"));
        alertStage.getIcons().add(icono);

        // Header
        HBox header = new HBox();
        header.getStyleClass().add("alert-header");
        Label lblTitulo = new Label("❖ " + titulo.toUpperCase());
        lblTitulo.getStyleClass().add("alert-titulo");
        header.getChildren().add(lblTitulo);

        // Línea de puntos
        Label dotLine = new Label(
                "· · · · · · · · · · · · · · · · · · · · · · · · · · · · · · · · · · ·");
        dotLine.setMaxWidth(Double.MAX_VALUE);
        dotLine.getStyleClass().add("alert-dot-line");

        // Cuerpo
        VBox body = new VBox(12);
        body.getStyleClass().add("alert-body");
        body.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Label lblMensaje = new Label(mensaje);
        lblMensaje.getStyleClass().add("alert-mensaje");
        lblMensaje.setMaxWidth(360);
        lblMensaje.setWrapText(true);

        Button btnCerrar = new Button("[ OK ]");
        btnCerrar.getStyleClass().add("alert-btn");
        btnCerrar.setOnAction(e -> alertStage.close());

        body.getChildren().addAll(lblMensaje, btnCerrar);

        // Barra inferior
        HBox bottomBar = new HBox();
        bottomBar.getStyleClass().add("alert-bottom");
        Label hint = new Label("❖ Press OK to continue");
        hint.getStyleClass().add("alert-bottom-hint");
        bottomBar.getChildren().add(hint);

        // Layout
        VBox layout = new VBox(0, header, dotLine, body, bottomBar);
        layout.getStyleClass().add("alert-root");

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(
                getClass().getResource("/org/example/projectgestaltt/style.css")
                        .toExternalForm());

        alertStage.setScene(scene);
        alertStage.setMinWidth(420);
        alertStage.showAndWait();
    }

    private String codigoEjemplo() {
        return """
                gestalt MiPrograma engage

                    unit pod x := 10;
                    unit faith pi := 3.14;
                    unit pascal nombre := "2B";
                    unit nier activo := true;

                    yorha pod numeros[5];

                    directive (x > 5) engage
                        report(x);
                    disengage otherwise engage
                        report(0);
                    disengage

                    mission (x > 0) engage
                        x := x - 1;
                    disengage

                    sortie (unit pod i := 0; i < 5; i := i + 1) engage
                        report(i);
                    disengage

                    model sumar(pod a, pod b) engage
                        glory a + b;
                    disengage

                    sumar(3, 7);

                disengage replicant
                """;
    }
}