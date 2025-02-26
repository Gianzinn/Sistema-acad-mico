package InterfacesDAO;

import java.util.List;

import Classes.Professor;

public interface ProfessorDAO {

	public boolean inserir(Professor professor);

	public boolean alterar(Professor professor);

	public boolean remover(int id);

	public List<Professor> consultar();

	public List<Professor> consultar(int id);

	public List<Professor> consultar(String s);
}
