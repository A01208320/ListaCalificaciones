package GUI;

public class TableData {
	// Materia
	private String nombreMat, codigo;
	// Alumno
	private String nombreAlu, grado, grupo, NL;
	// Calificacion
	private String periodo, calificacion;
        // Filter
        private String filter;
	
	// Materia Constructor
	public TableData(String nombreMat, String codigo) {
		this.nombreMat=nombreMat;
		this.codigo=codigo;
	}
	
	// Alumno Constructor
	public TableData(String nombreAlu, String grado, String grupo, String NL) {
		this.nombreAlu=nombreAlu;
		this.grupo=grupo;
		this.grado=grado;
		this.NL=NL;
	}
	
	// Calificacion Constructor
	public TableData(String periodo, String grado, String grupo, String nombreMat, String nombreAlu, String calificacion) {
		this.periodo = periodo;
		this.grado = grado;
		this.calificacion = calificacion;
		this.grupo = grupo;
		this.nombreMat = nombreMat;
		this.nombreAlu = nombreAlu;
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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombreAlu() {
		return nombreAlu;
	}

	public void setNombreAlu(String nombreAlu) {
		this.nombreAlu = nombreAlu;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getNL() {
		return NL;
	}

	public void setNL(String nL) {
		NL = nL;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getCalificacion() {
            return calificacion;
	}

	public void setCalificacion(String calificacion) {
            this.calificacion = calificacion;
	}
        
        public String getFilter(){
            return filter;
        }
        
        public void setFilter(String filter){
            this.filter=filter;
        }
}
