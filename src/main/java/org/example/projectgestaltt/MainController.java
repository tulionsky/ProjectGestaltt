package org.example.projectgestaltt;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import org.example.projectgestaltt.compiler.GestaltCompiler;
import org.example.projectgestaltt.model.*;

import java.io.*;
import java.nio.file.Files;

public class MainController {

    // ── Editor de código ─────────────────────────────────────────────────────
    @FXML private TextArea editorCodigo;

    // ── Panel árbol gráfico ──────────────────────────────────────────────────
    @FXML private ScrollPane scrollArbol;
    @FXML private Pane       paneArbol;
    @FXML private Label      lblEstado;

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

        // Árbol gráfico
        if (resultado.getParseTree() != null) {
            Pane treePane = org.example.projectgestaltt.compiler.TreeVisualizer.draw(
                    resultado.getParseTree(),
                    resultado.getParser()
            );
            paneArbol.getChildren().clear();
            paneArbol.getChildren().add(treePane);
            paneArbol.setPrefSize(
                    treePane.getPrefWidth(),
                    treePane.getPrefHeight()
            );
        }

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

    // ── Acción: Limpiar ──────────────────────────────────────────────────────
    @FXML
    private void onLimpiar() {
        editorCodigo.clear();
        paneArbol.getChildren().clear();
        tablaTokens.getItems().clear();
        tablaErrores.getItems().clear();
        tablaSimbolos.getItems().clear();
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
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private String codigoEjemplo() {
        return """
                gestalt MiPrograma engage

                    pod x := 10;
                    faith pi := 3.14;
                    pascal nombre := "2B";
                    nier activo := true;

                    yorha pod numeros[5];

                    directive (x > 5) engage
                        report(x);
                    disengage otherwise engage
                        report(0);
                    disengage

                    mission (x > 0) engage
                        x := x - 1;
                    disengage

                    sortie (pod i := 0; i < 5; i := i + 1) engage
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