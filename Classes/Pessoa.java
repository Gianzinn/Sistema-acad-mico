package Classes;

import java.sql.Date;

public class Pessoa {

	private int id;
	private String nome;
	private String email;
	private Date data;
	private String cpf;

	public Pessoa() {
	}
	
	public Pessoa(String nome, String email, Date data, String cpf) {
		super();
		this.nome = nome;
		this.email = email;
		this.data = data;
		this.cpf = cpf;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	
	@Override
	public String toString() {
		return "Pessoa [id=" + id + ", nome=" + nome + ", email=" + email + ", data=" + data + ", cpf=" + cpf + "]";
	}

	
}
