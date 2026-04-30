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

        // ── Contenedor con escala para zoom ──
        javafx.scene.layout.StackPane zoomPane =
                new javafx.scene.layout.StackPane(treePane);
        zoomPane.setStyle("-fx-background-color: #bcb89c;");
        zoomPane.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        zoomPane.setPrefSize(treePane.getPrefWidth(), treePane.getPrefHeight());

        // ── ScrollPane ──
        ScrollPane scroll = new ScrollPane(zoomPane);
        scroll.setStyle("-fx-background-color: #bcb89c; -fx-background: #bcb89c;");
        scroll.setPrefSize(1100, 700);
        scroll.setFitToWidth(false);
        scroll.setFitToHeight(false);
        scroll.setPannable(true); // permite arrastrar con el mouse

        // ── Zoom con rueda del mouse ──
        final double ZOOM_FACTOR = 0.1;
        final double MIN_ZOOM    = 0.2;
        final double MAX_ZOOM    = 3.0;
        final double[] escala    = {1.0};

        scroll.addEventFilter(javafx.scene.input.ScrollEvent.SCROLL, event -> {
            if (event.isControlDown() || true) {
                double delta = event.getDeltaY() > 0
                        ? (1 + ZOOM_FACTOR)
                        : (1 - ZOOM_FACTOR);

                double nuevaEscala = escala[0] * delta;
                nuevaEscala = Math.max(MIN_ZOOM, Math.min(MAX_ZOOM, nuevaEscala));
                escala[0]   = nuevaEscala;

                treePane.setScaleX(nuevaEscala);
                treePane.setScaleY(nuevaEscala);

                // Ajustar tamaño del contenedor para que el scroll funcione bien
                zoomPane.setPrefSize(
                        treePane.getPrefWidth()  * nuevaEscala,
                        treePane.getPrefHeight() * nuevaEscala
                );

                event.consume();
            }
        });

        // Header
        HBox header = new HBox();
        header.setStyle("-fx-background-color: #2e2a1e; -fx-padding: 10 16;");
        Label titulo = new Label("❖ PARSE TREE — Project Gestalt");
        titulo.setStyle(
                "-fx-text-fill: #c8c4a8; -fx-font-weight: bold; " +
                        "-fx-font-size: 13px; -fx-font-family: 'Courier New';");

        // Indicador de zoom
        Label lblZoom = new Label("ZOOM: 100%");
        lblZoom.setStyle(
                "-fx-text-fill: #7a7860; -fx-font-size: 10px; " +
                        "-fx-font-family: 'Courier New';");

        // Actualizar label de zoom
        scroll.addEventFilter(javafx.scene.input.ScrollEvent.SCROLL, event -> {
            lblZoom.setText("ZOOM: " + (int)(escala[0] * 100) + "%");
        });

        Pane spacer = new Pane();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Botones de zoom
        Button btnZoomIn = new Button("[ + ]");
        btnZoomIn.setStyle(
                "-fx-background-color: #3e3a2e; -fx-text-fill: #c8c4a8; " +
                        "-fx-font-size: 11px; -fx-padding: 3 10; -fx-background-radius: 0; " +
                        "-fx-font-family: 'Courier New'; -fx-cursor: hand;");
        btnZoomIn.setOnAction(e -> {
            double nueva = Math.min(escala[0] + ZOOM_FACTOR, MAX_ZOOM);
            escala[0] = nueva;
            treePane.setScaleX(nueva);
            treePane.setScaleY(nueva);
            zoomPane.setPrefSize(
                    treePane.getPrefWidth()  * nueva,
                    treePane.getPrefHeight() * nueva);
            lblZoom.setText("ZOOM: " + (int)(nueva * 100) + "%");
        });

        Button btnZoomOut = new Button("[ - ]");
        btnZoomOut.setStyle(
                "-fx-background-color: #3e3a2e; -fx-text-fill: #c8c4a8; " +
                        "-fx-font-size: 11px; -fx-padding: 3 10; -fx-background-radius: 0; " +
                        "-fx-font-family: 'Courier New'; -fx-cursor: hand;");
        btnZoomOut.setOnAction(e -> {
            double nueva = Math.max(escala[0] - ZOOM_FACTOR, MIN_ZOOM);
            escala[0] = nueva;
            treePane.setScaleX(nueva);
            treePane.setScaleY(nueva);
            zoomPane.setPrefSize(
                    treePane.getPrefWidth()  * nueva,
                    treePane.getPrefHeight() * nueva);
            lblZoom.setText("ZOOM: " + (int)(nueva * 100) + "%");
        });

        Button btnReset = new Button("[ RESET ]");
        btnReset.setStyle(
                "-fx-background-color: #3e3a2e; -fx-text-fill: #c8c4a8; " +
                        "-fx-font-size: 11px; -fx-padding: 3 10; -fx-background-radius: 0; " +
                        "-fx-font-family: 'Courier New'; -fx-cursor: hand;");
        btnReset.setOnAction(e -> {
            escala[0] = 1.0;
            treePane.setScaleX(1.0);
            treePane.setScaleY(1.0);
            zoomPane.setPrefSize(
                    treePane.getPrefWidth(),
                    treePane.getPrefHeight());
            lblZoom.setText("ZOOM: 100%");
        });

        header.getChildren().addAll(
                titulo, spacer, lblZoom, btnZoomOut, btnZoomIn, btnReset);
        header.setSpacing(8);
        header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // Línea decorativa
        Label dotLine = new Label(
                "· · · · · · · · · · · · · · · · · · · · · · · · · · · · · " +
                        "· · · · · · · · · · · · · · · · · · · · · · · · · · · · · ·");
        dotLine.setMaxWidth(Double.MAX_VALUE);
        dotLine.setStyle(
                "-fx-text-fill: #9a9070; -fx-font-size: 10px; " +
                        "-fx-padding: 2 8; -fx-background-color: #b8b49a;");

        // Barra inferior con instrucciones
        HBox bottomBar = new HBox();
        bottomBar.setStyle("-fx-background-color: #2e2a1e; -fx-padding: 6 14;");
        Label hint = new Label(
                "❖ Scroll para zoom   ❖ Click + arrastrar para mover");
        hint.setStyle(
                "-fx-text-fill: #7a7860; -fx-font-size: 10px; " +
                        "-fx-font-family: 'Courier New';");
        bottomBar.getChildren().add(hint);

        // Layout
        VBox layout = new VBox(0, header, dotLine, scroll, bottomBar);
        layout.setStyle("-fx-background-color: #bcb89c;");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        // Ventana
        Stage stage = new Stage();
        stage.setTitle("Parse Tree — Project Gestalt");
        stage.initModality(Modality.NONE);
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