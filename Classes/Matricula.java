package Classes;

import java.sql.Date;

public class Matricula {
	private int idMatricula;
	private Date data;
	private Aluno aluno;
	private Disciplina disciplina;

	public Matricula() {
		this.aluno = new Aluno();
		this.disciplina = new Disciplina();
	}

	public Matricula(int idMatricula, Date data, Aluno aluno, Disciplina disciplina) {
		super();
		this.idMatricula = idMatricula;
		this.data = data;
		this.aluno = aluno;
		this.disciplina = disciplina;
	}

	public int getIdMatricula() {
		return idMatricula;
	}

	public void setIdMatricula(int idMatricula) {
		this.idMatricula = idMatricula;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	@Override
	public String toString() {
		return "Matricula [idMatricula=" + idMatricula + ", data=" + data + ", aluno=" + aluno + ", disciplina="
				+ disciplina + "]";
	}

	
	
}
