package InterfacesDAO;

import java.util.List;

import Classes.Aluno;

public interface AlunoDAO {

	public boolean inserir(Aluno aluno);

	public boolean alterar(Aluno aluno);

	public boolean remover(int ra);

	public List<Aluno> consultar();

	public List<Aluno> consultar(int ra);

	public List<Aluno> consultar(String s);
}
