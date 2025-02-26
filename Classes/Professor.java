package Classes;

public class Professor extends Pessoa{

	private int idProfessor;
	private String departamento;

	public Professor() {
	}

	public Professor(int id, String departamento) {
		super();
		this.idProfessor = id;
		this.departamento = departamento;
	}

	public int getIdProfessor() {
		return idProfessor;
	}

	public void setIdProfessor(int id) {
		this.idProfessor = id;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	@Override
	public String toString() {
		return "Professor [id=" + idProfessor + ", departamento=" + departamento + "]";
	}

}
