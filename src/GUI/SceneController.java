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

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	

	
	private void load(ActionEvent event) {
		stage=(Stage)(((Node)event.getSource()).getScene().getWindow());
		scene=new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void MainMenu(ActionEvent event) throws IOException {
		root=FXMLLoader.load(getClass().getResource("MenuPrincipal.fxml"));
		load(event);
	}
	
	
	public void ConsultMenu(ActionEvent event) throws IOException {
		root=FXMLLoader.load(getClass().getResource("ConsultarMenu.fxml"));
		load(event);
	}
	public void ConsultMaterias(ActionEvent event) throws IOException {
		root=FXMLLoader.load(getClass().getResource("ConsultarMaterias.fxml"));
		load(event);
	}
	public void ConsultAlumnos(ActionEvent event) throws IOException{
		root=FXMLLoader.load(getClass().getResource("ConsultarAlumnos.fxml"));
		load(event);
	}
	public void ConsultCalificaciones(ActionEvent event) throws IOException{
		root=FXMLLoader.load(getClass().getResource("ConsultarCalificaciones.fxml"));
		load(event);
	}
	
	
	public void ImportMenu(ActionEvent event) throws IOException {
		root=FXMLLoader.load(getClass().getResource("ImportarMenu.fxml"));
		load(event);
	}
	public void ImportMaterias(ActionEvent event) throws IOException {
		root=FXMLLoader.load(getClass().getResource("ImportarMaterias.fxml"));
		load(event);
	}
	public void ImportAlumnos(ActionEvent event) throws IOException{
		root=FXMLLoader.load(getClass().getResource("ImportarAlumnos.fxml"));
		load(event);
	}
	public void ImportCalificaciones(ActionEvent event) throws IOException{
		root=FXMLLoader.load(getClass().getResource("ImportarCalificaciones.fxml"));
		load(event);
	}
}
