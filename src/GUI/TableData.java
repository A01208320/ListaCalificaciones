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

public class TableData {
	// Materia
	private String nombreMat;
        private int codigo;
	// Alumno
	private String nombreAlu, grupo;
        private int NL;
	// Calificacion
	private int periodo, calificacion;
        // Filter
        private String filter;
	
	// Materia Constructor
	public TableData(String nombreMat, String codigo) {
		this.nombreMat=nombreMat;
		this.codigo=Integer.parseInt(codigo);
	}
	
	// Alumno Constructor
	public TableData(String nombreAlu, String grupo, String NL) {
		this.nombreAlu=nombreAlu;
		this.grupo=grupo;
		this.NL=Integer.parseInt(NL);
	}
	
	// Calificacion Constructor
	public TableData(String periodo,String nombreMat, String grupo, String NL, String nombreAlu, String calificacion) {
		this.periodo = Integer.parseInt(periodo);
                this.nombreMat = nombreMat;
                this.grupo = grupo;
                this.NL=Integer.parseInt(NL);
                this.nombreAlu = nombreAlu;
		this.calificacion = Integer.parseInt(calificacion);
	}
        
        // Filter Constructor
        public TableData(String filter){
            this.filter=filter;
        }

	//? Setters and Getters
	public String getNombreMat() {
		return nombreMat;
	}

	public void setNombreMat(String nombreMat) {
		this.nombreMat = nombreMat;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombreAlu() {
		return nombreAlu;
	}

	public void setNombreAlu(String nombreAlu) {
		this.nombreAlu = nombreAlu;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public int getNL() {
		return NL;
	}

	public void setNL(int nL) {
		NL = nL;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public int getCalificacion() {
            return calificacion;
	}

	public void setCalificacion(int calificacion) {
            this.calificacion = calificacion;
	}
        
        public String getFilter(){
            return filter;
        }
        
        public void setFilter(String filter){
            this.filter=filter;
        }
}
