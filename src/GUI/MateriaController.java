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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import parallel.ListaCalif;

public class MateriaController implements Initializable {
	@FXML private TableView<TableData> MateriaTabla;
	@FXML private TableColumn<TableData, String> MateriaNombre;
	@FXML private TableColumn<TableData, String> MateriaCodigo;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private final ListaCalif app=new ListaCalif("Prolog");

	
	private void load(ActionEvent event) {
		stage=(Stage)(((Node)event.getSource()).getScene().getWindow());
		scene=new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public void ConsultMenu(ActionEvent event) throws IOException {
		root=FXMLLoader.load(getClass().getResource("ConsultarMenu.fxml"));
		load(event);
	}


	@Override
	public void initialize(URL locat, ResourceBundle res) {
            MateriaNombre.setCellValueFactory(new PropertyValueFactory<>("nombreMat"));
            MateriaCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            MateriaTabla.setItems(getMaterias());
		
	}
	
	private ObservableList<TableData> getMaterias(){
            ObservableList<TableData> mat=FXCollections.observableArrayList();
	
            // Get queries of Materias
            try {
                Map<Integer, TableData> data = app.Consultar_Materias();
                if(!data.isEmpty()){
                    for (int i = 0; i < data.size(); i++) {
			mat.add(data.get(i));
                    }
                } else {
                    MateriaTabla.setPlaceholder(new Label("No se encontraron materias."));
                }
            } catch (InterruptedException ex) {
                MateriaTabla.setPlaceholder(new Label("Error buscando materias."));
                Logger.getLogger(MateriaController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return mat;
	}
	
	
}
