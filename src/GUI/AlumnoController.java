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

public class AlumnoController implements Initializable {
	@FXML private ChoiceBox<String> GrupoFilter;
	
	@FXML private TableView<TableData> AlumnoTable;
	@FXML private TableColumn<TableData, String> AlumnoGrupo;
	@FXML private TableColumn<TableData, Integer> AlumnoNL;
	@FXML private TableColumn<TableData, String> AlumnoNombre;

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
                AlumnoTable.setItems(getAlumnos(GrupoFilter.getValue()));   
            }
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
            try {
                GrupoFilter.getItems().addAll(app.Grupo_Filter());
            } catch (InterruptedException ex) {
                Logger.getLogger(AlumnoController.class.getName()).log(Level.SEVERE, null, ex);
            }
		GrupoFilter.setValue("Todos");
		
		AlumnoTable.setPlaceholder(new Label("Oprima el boton de buscar para buscar alumnos."));
		
		AlumnoGrupo.setCellValueFactory(new PropertyValueFactory<>("grupo"));
		AlumnoNL.setCellValueFactory(new PropertyValueFactory<>("NL"));
		AlumnoNombre.setCellValueFactory(new PropertyValueFactory<>("nombreAlu"));
	}

	private ObservableList<TableData> getAlumnos(String grupo) {
            ObservableList<TableData> mat = FXCollections.observableArrayList();
            
            // Get queries from Alumnos
            try {
                Map<Integer, TableData> data = app.Consultar_Alumnos(grupo);
                if(!data.isEmpty()){
                    for (int i = 0; i < data.size(); i++) {
			mat.add(data.get(i));
                    }
                }else{
                    AlumnoTable.setPlaceholder(new Label("No se encontraron alumnos."));
                }
            } catch (InterruptedException ex) {
                success=false;
                AlumnoTable.setPlaceholder(new Label("Error al buscar alumnos."));
                Logger.getLogger(AlumnoController.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            return mat;
	}
}
