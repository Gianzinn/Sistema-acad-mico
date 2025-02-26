package InterfacesIMP;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Classes.Aluno;
import Conexao.Conexao;
import InterfacesDAO.AlunoDAO;

public class AlunoDAOImp implements AlunoDAO {

	@Override
	public boolean inserir(Aluno aluno) {
		String sql = "INSERT INTO aluno (id_pessoa, ra) VALUES (?, ?)";

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, aluno.getId());
			ps.setInt(2, aluno.getRa());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next()) {
				aluno.setIdAluno(rs.getInt("id_aluno"));
			}
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean alterar(Aluno aluno) {
		String sql = "UPDATE aluno SET ra = ?, id_aluno = ? WHERE id_pessoa = ?";

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);

			ps.setInt(1, aluno.getRa());
			ps.setInt(2, aluno.getIdAluno());
			ps.setInt(3, aluno.getId());

			ps.executeUpdate();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean remover(int ra) {
		String sql = "DELETE FROM aluno WHERE ra = ?";

		PreparedStatement ps;
		try {
			ps = Conexao.getConnection().prepareStatement(sql);

			ps.setInt(1, ra);

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Aluno> consultar() {
		String sql = "SELECT  Pessoa.id_pessoa, Pessoa.nome, Pessoa.email, Pessoa.data_nasc, Pessoa.cpf, Aluno.id_aluno, Aluno.ra FROM pessoa LEFT JOIN Aluno ON Pessoa.id_pessoa = Aluno.id_pessoa;";
		Statement ps;
		List<Aluno> listaA = new ArrayList<Aluno>();

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				Aluno A = new Aluno();
				A.setId(rs.getInt("id_pessoa"));
				A.setNome(rs.getString("nome"));
				A.setEmail(rs.getString("email"));
				A.setData(rs.getDate("data_nasc"));
				A.setCpf(rs.getString("cpf"));
				A.setIdAluno(rs.getInt("id_aluno"));
				A.setRa(rs.getInt("ra"));

				listaA.add(A);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Erro ao consultar os alunos: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return listaA;
	}

	@Override
	public List<Aluno> consultar(int ra) {
		String sql = "SELECT p.id_pessoa, p.nome, p.email, p.data_nasc, p.cpf, a.id_aluno, a.ra FROM pessoa p LEFT JOIN aluno a ON p.id_pessoa = a.id_pessoa WHERE ra = ?";
		PreparedStatement ps;
		List<Aluno> listaA = new ArrayList<Aluno>();
		
		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, ra);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Aluno A = new Aluno();
				A.setId(rs.getInt("id_pessoa"));
				A.setNome(rs.getString("nome"));
				A.setEmail(rs.getString("email"));
				A.setData(rs.getDate("data_nasc"));
				A.setCpf(rs.getString("cpf"));
				A.setIdAluno(rs.getInt("id_aluno"));
				A.setRa(rs.getInt("ra"));

				listaA.add(A);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Erro ao consultar as pessoas: " + e.getMessage());
			e.printStackTrace();
			return null;
		}

		return listaA;
	}

	@Override
	public List<Aluno> consultar(String s) {
		String sql = "SELECT p.id_pessoa, p.nome, p.email, p.data_nasc, p.cpf, a.id_aluno, a.ra FROM pessoa p LEFT JOIN aluno a ON p.id_pessoa = a.id_pessoa WHERE p.nome like ?";
		List<Aluno> listaA = new ArrayList<Aluno>();

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, "%" + s + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Aluno A = new Aluno();
				A.setId(rs.getInt("id_pessoa"));
				A.setNome(rs.getString("nome"));
				A.setEmail(rs.getString("email"));
				A.setData(rs.getDate("data_nasc"));
				A.setCpf(rs.getString("cpf"));
				A.setIdAluno(rs.getInt("id_aluno"));
				A.setRa(rs.getInt("ra"));
				
				listaA.add(A);

			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Erro ao consultar as pessoas: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return listaA;
	}

}
