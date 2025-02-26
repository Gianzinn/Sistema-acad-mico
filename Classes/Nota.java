package Classes;

public class Nota {
	private int idNota;
	private Matricula matricula;
	private float n1, n2;
	
	public Nota() {
		this.matricula = new Matricula();
	}
	
	public Nota(int idNota, Matricula matricula, float n1, float n2) {
		super();
		this.idNota = idNota;
		this.matricula = matricula;
		this.n1 = n1;
		this.n2 = n2;
	}

	public int getIdNota() {
		return idNota;
	}

	public void setIdNota(int idNota) {
		this.idNota = idNota;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public float getN1() {
		return n1;
	}

	public void setN1(float n1) {
		this.n1 = n1;
	}

	public float getN2() {
		return n2;
	}

	public void setN2(float n2) {
		this.n2 = n2;
	}

	@Override
	public String toString() {
		return "Nota [idNota=" + idNota + ", matricula=" + matricula + ", n1=" + n1 + ", n2=" + n2 + "]";
	}
	
	
}
