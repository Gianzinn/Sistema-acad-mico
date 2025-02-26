package InterfacesDAO;

import java.util.List;

import Classes.Curso;

public interface CursoDAO {

	public boolean inserir(Curso curso);

	public boolean alterar(Curso curso);

	public boolean remover(int id);

	public List<Curso> consultar();

	public List<Curso> consultar(int id);

	public List<Curso> consultar(String s);

}
