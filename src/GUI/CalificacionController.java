/*
	<Lista Calificaciones: List of Scores for Schools>
	Copyright (C) <2021>  <A01208320> <A01208320@itesm.mx>

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
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
	@FXML private ChoiceBox<String> GrupoFilter;
	
	@FXML private TableView<TableData> CalifTable;
	@FXML private TableColumn<TableData, Integer> CalifPeriodo;
        @FXML private TableColumn<TableData, Integer> CalifNL;
	@FXML private TableColumn<TableData, String> CalifMateria;
	@FXML private TableColumn<TableData, String> CalifGrupo;
	@FXML private TableColumn<TableData, String> CalifNombre;
	@FXML private TableColumn<TableData, Integer> CalifCalif;
	
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
                CalifTable.setItems(getCalif(PeriodoFilter.getValue(), MateriaFilter.getValue(), GrupoFilter.getValue()));
            }
        }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
            try {
                MateriaFilter.getItems().addAll(app.Materia_Filter());
                MateriaFilter.setValue("Todos");
                PeriodoFilter.getItems().addAll(app.Periodo_Filter());
                PeriodoFilter.setValue("Todos");
                GrupoFilter.getItems().addAll(app.Grupo_Filter());
                GrupoFilter.setValue("Todos");
                
                CalifTable.setPlaceholder(new Label("Presione el boton de buscar para buscar Calificaciones."));
            } catch (InterruptedException ex) {
                success=false;
                CalifTable.setPlaceholder(new Label("Error en el filtros."));
                Logger.getLogger(CalificacionController.class.getName()).log(Level.SEVERE, null, ex);
            }

            CalifPeriodo.setCellValueFactory(new PropertyValueFactory<>("periodo"));
            CalifNL.setCellValueFactory(new PropertyValueFactory<>("NL"));
            CalifMateria.setCellValueFactory(new PropertyValueFactory<>("nombreMat"));
            CalifGrupo.setCellValueFactory(new PropertyValueFactory<>("grupo"));
            CalifNombre.setCellValueFactory(new PropertyValueFactory<>("nombreAlu"));
            CalifCalif.setCellValueFactory(new PropertyValueFactory<>("calificacion"));
	}
	
	private ObservableList<TableData> getCalif(String periodo, String materia, String grupo) {
            ObservableList<TableData> mat = FXCollections.observableArrayList();
            
            // Get all queries from Calificaciones
            try {
                Map<Integer, TableData> data = app.Consultar_Calif(periodo, materia, grupo);
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
