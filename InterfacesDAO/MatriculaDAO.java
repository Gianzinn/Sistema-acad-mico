package InterfacesDAO;

import java.util.List;

import Classes.Matricula;

public interface MatriculaDAO {
	public boolean inserir(Matricula matricula);

	public boolean alterar(Matricula matricula);

	public boolean remover(int id);

	public List<Matricula> consultar();
	
	public List<Matricula> consultar(int id);

}
