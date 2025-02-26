package InterfacesDAO;

import java.util.List;

import Classes.Disciplina;

public interface DisciplinaDAO {

	public boolean inserir(Disciplina disciplina);

	public boolean alterar(Disciplina disciplina);

	public boolean remover(int id);

	public List<Disciplina> consultar();

	public List<Disciplina> consultar(int id);

	public List<Disciplina> consultar(String s);

}
