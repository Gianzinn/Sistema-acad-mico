package Classes;

import java.sql.Date;

public class Aluno extends Pessoa {

	private int idAluno;
	private int ra;

	public Aluno() {

	}

	public Aluno(String nome, String email, Date data, String cpf, int ra) {
		super(nome, email, data, cpf);
		this.ra = ra;
	}

	public int getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(int id) {
		this.idAluno = id;
	}

	public int getRa() {
		return ra;
	}

	public void setRa(int ra) {
		this.ra = ra;
	}

	@Override
	public String toString() {
		return "Aluno [Id=" + getId() + ", Nome=" + getNome() + ", Email=" + getEmail() + ", Data=" + getData()
				+ ", Cpf=" + getCpf() + ", id_aluno=" + idAluno + ", ra=" + ra + "]";
	}

}
