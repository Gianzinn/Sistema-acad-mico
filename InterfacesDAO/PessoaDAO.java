package InterfacesDAO;

import java.util.List;

import Classes.Pessoa;

public interface PessoaDAO {

	public boolean inserir(Pessoa pessoa);

	public boolean alterar(Pessoa pessoa);

	public boolean remover(int id);

	public List<Pessoa> consultar();

	public List<Pessoa> consultarCpf(String cpf);

	public List<Pessoa> consultar(String s);

}
