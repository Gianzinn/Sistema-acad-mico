package InterfacesIMP;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Classes.Pessoa;
import Conexao.Conexao;
import InterfacesDAO.PessoaDAO;

public class PessoaDAOImp implements PessoaDAO {

	@Override
	public boolean inserir(Pessoa pessoa) {
		String sql = "INSERT INTO pessoa (nome, email, data_nasc, cpf) VALUES (?,?,?,?)";

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, pessoa.getNome());
			ps.setString(2, pessoa.getEmail());
			ps.setDate(3, pessoa.getData());
			ps.setString(4, pessoa.getCpf());

			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next()) {
				pessoa.setId(rs.getInt(1));
			}
			
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean alterar(Pessoa pessoa) {
		String sql = "UPDATE Pessoa SET nome = ?, email = ?, data_nasc = ?, cpf = ? WHERE id_pessoa = ?";

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);

			ps.setString(1, pessoa.getNome());
			ps.setString(2, pessoa.getEmail());
			ps.setDate(3, pessoa.getData());
			ps.setString(4, pessoa.getCpf());
			ps.setInt(5, pessoa.getId());

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean remover(int id) {
		String sql = "DELETE FROM pessoa WHERE id_pessoa = ?";

		PreparedStatement ps;
		try {
			ps = Conexao.getConnection().prepareStatement(sql);

			ps.setInt(1, id);

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Pessoa> consultar() {
		String sql = "SELECT * FROM pessoa";
		Statement ps;
		List<Pessoa> listaP = new ArrayList<Pessoa>();

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				Pessoa P = new Pessoa();
				P.setId(rs.getInt(1));
				P.setNome(rs.getString(2));
				P.setEmail(rs.getString(3));
				P.setData(rs.getDate(4));
				P.setCpf(rs.getString(5));

				listaP.add(P);

			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Erro ao consultar as pessoas: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return listaP;
	}

	@Override
	public List<Pessoa> consultarCpf(String cpf) {
		
		String sql = "SELECT * FROM pessoa WHERE cpf = ?";
		PreparedStatement ps;
		List<Pessoa> listaP = new ArrayList<Pessoa>();

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, cpf);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Pessoa P = new Pessoa();
				P.setId(rs.getInt(1));
				P.setNome(rs.getString(2));
				P.setEmail(rs.getString(3));
				P.setData(rs.getDate(4));
				P.setCpf(rs.getString(5));

				listaP.add(P);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Erro ao consultar as pessoas: " + e.getMessage());
			e.printStackTrace();
			return null;
		}

		return listaP;
	}

	@Override
	public List<Pessoa> consultar(String s) {
		
		String sql = "SELECT * FROM pessoa WHERE nome like ?";
		PreparedStatement ps;
		List<Pessoa> listaP = new ArrayList<Pessoa>();

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, "%" + s + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Pessoa P = new Pessoa();
				P.setId(rs.getInt(1));
				P.setNome(rs.getString(2));
				P.setEmail(rs.getString(3));
				P.setData(rs.getDate(4));
				P.setCpf(rs.getString(5));

				listaP.add(P);

			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Erro ao consultar as pessoas: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return listaP;
	}

}
