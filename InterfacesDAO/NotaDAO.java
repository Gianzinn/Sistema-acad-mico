package InterfacesDAO;

import java.util.List;

import Classes.Nota;

public interface NotaDAO {
	public boolean inserir(Nota nota);

	public boolean alterar(Nota nota);

	public boolean remover(int id);

	public List<Nota> consultar();
	
	public List<Nota> consultar(int id);

}
