package Classes;

public class Disciplina {

	private int idDisciplina;
	private String nome;
	private Curso curso;
	private Professor professor;
	
	public Disciplina() {
		this.curso = new Curso();
		this.professor = new Professor();
	}

	
	
	public Disciplina(int idDisciplina, String nome, Curso c, Professor pro) {
		super();
		this.idDisciplina = idDisciplina;
		this.nome = nome;
		this.curso = c;
		this.professor = pro;
	}


	public int getIdDisciplina() {
		return idDisciplina;
	}

	public void setIdDisciplina(int id) {
		this.idDisciplina = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Disciplina [id=" + idDisciplina + ", nome=" + nome + "]";
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso c) {
		this.curso = c;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor pro) {
		this.professor = pro;
	}
	
}
