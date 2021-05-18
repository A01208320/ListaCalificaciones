package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import parallel.ListaCalif;

public class CalificacionController implements Initializable{
	@FXML private ChoiceBox<String> PeriodoFilter;
	@FXML private ChoiceBox<String> MateriaFilter;
	@FXML private ChoiceBox<String> GradoFilter;
	@FXML private ChoiceBox<String> GrupoFilter;
	
	@FXML private TableView<TableData> CalifTable;
	@FXML private TableColumn<TableData, String> CalifPeriodo;
	@FXML private TableColumn<TableData, String> CalifMateria;
	@FXML private TableColumn<TableData, String> CalifGrado;
	@FXML private TableColumn<TableData, String> CalifGrupo;
	@FXML private TableColumn<TableData, String> CalifNombre;
	@FXML private TableColumn<TableData, String> CalifCalif;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private final ListaCalif app = new ListaCalif("Prolog");
        private boolean success=true;
	
	private void load(ActionEvent event) {
		stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void ConsultMenu(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("ConsultarMenu.fxml"));
		load(event);
	}
	
	public void Search() {
            if(success){
                CalifTable.setItems(getCalif(PeriodoFilter.getValue(), MateriaFilter.getValue(), GradoFilter.getValue(), GrupoFilter.getValue()));
            }
        }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
            try {
                MateriaFilter.getItems().addAll(app.Materia_Filter());
                MateriaFilter.setValue("Todos");
                
                String[] Periodo= {"Todos", "1", "2", "3", "4", "5", "6"};
                PeriodoFilter.getItems().addAll(Periodo);
                PeriodoFilter.setValue("Todos");
                String[] Grupo= {"Todos","A", "B"};
                GrupoFilter.getItems().addAll(Grupo);
                GrupoFilter.setValue("Todos");
                String[] Grado= {"Todos", "1", "2", "3", "4", "5", "6"};
                GradoFilter.getItems().addAll(Grado);
                GradoFilter.setValue("Todos");
                
                CalifTable.setPlaceholder(new Label("Presione el boton de buscar para buscar Calificaciones."));
            } catch (InterruptedException ex) {
                success=false;
                CalifTable.setPlaceholder(new Label("Error en el filtro de Materia."));
                Logger.getLogger(CalificacionController.class.getName()).log(Level.SEVERE, null, ex);
            }

            CalifPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));
            CalifMateria.setCellValueFactory(new PropertyValueFactory<>("nombreMat"));
            CalifGrado.setCellValueFactory(new PropertyValueFactory<>("grado"));
            CalifGrupo.setCellValueFactory(new PropertyValueFactory<>("grupo"));
            CalifNombre.setCellValueFactory(new PropertyValueFactory<>("nombreAlu"));
            CalifCalif.setCellValueFactory(new PropertyValueFactory<>("calificacion"));
	}
	
	private ObservableList<TableData> getCalif(String periodo, String materia, String grado, String grupo) {
            ObservableList<TableData> mat = FXCollections.observableArrayList();
            
            // Get all queries from Calificaciones
            try {
                Map<Integer, TableData> data = app.Consultar_Calif(periodo, materia, grado, grupo);
                if(!data.isEmpty()){
                    for (int i = 0; i < data.size(); i++) {
                        mat.add(data.get(i));
                    }
                }
                else{
                    CalifTable.setPlaceholder(new Label("No se encontraron calificaciones."));
                }
            } catch (InterruptedException ex) {
                success=false;
                CalifTable.setPlaceholder(new Label("Error al buscar Calificaciones."));
                Logger.getLogger(CalificacionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return mat;
	}
}
