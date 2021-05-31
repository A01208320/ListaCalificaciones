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
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import parallel.ListaCalif;

public class ImportController {
	
	@FXML private CheckBox overwrite;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private final ListaCalif app=new ListaCalif("Both");
	
	private void load(ActionEvent event) {
		stage=(Stage)(((Node)event.getSource()).getScene().getWindow());
		scene=new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void ImportMenu(ActionEvent event) throws IOException {
		root=FXMLLoader.load(getClass().getResource("ImportarMenu.fxml"));
		load(event);
	}
	
	
	public void ImportMaterias() {
            try {
                app.Importar_Materias(overwrite.isSelected());
            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
	public void ExportMaterias() {
            try {
                app.Exportar_Materias();
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
	
	
	public void ImportAlumnos() {
            try {
                app.Importar_Alumnos(overwrite.isSelected());
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
	public void ExportAlumnos() {
            try {
                app.Exportar_Alumnos();
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

	
	public void ImportCalificaciones() {
            try {
                app.Importar_Calificaciones(overwrite.isSelected());
            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
	public void ExportCalificaciones() {
            try {
                app.Exportar_Calificaciones();
            } catch (InterruptedException | IOException ex) {
                Logger.getLogger(ImportController.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
	
	
}
