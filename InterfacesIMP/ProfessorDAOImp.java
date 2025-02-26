package InterfacesIMP;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Classes.Professor;
import Conexao.Conexao;
import InterfacesDAO.ProfessorDAO;

public class ProfessorDAOImp implements ProfessorDAO{

	@Override
	public boolean inserir(Professor professor) {
		String sql = "INSERT INTO professor (id_pessoa, departamento) VALUES (?, ?)";

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, professor.getId());
			ps.setString(2, professor.getDepartamento());

			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next()) {
				professor.setIdProfessor(rs.getInt("id_professor"));
			}
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean alterar(Professor professor) {
		String sql = "UPDATE professor SET departamento = ?, id_pessoa = ? WHERE id_professor = ?";

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);

			ps.setString(1, professor.getDepartamento());
			ps.setInt(2, professor.getId());
			ps.setInt(3, professor.getIdProfessor());

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
		String sql = "DELETE FROM professor WHERE id_professor = ?";

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
	public List<Professor> consultar() {
		String sql = "SELECT pe.id_pessoa, pe.nome, pe.email, pe.data_nasc, pe.cpf, p.id_professor, p.departamento "
				+ "FROM pessoa pe "
				+ "LEFT JOIN Professor p "
				+ "ON pe.id_pessoa = p.id_pessoa;";
		Statement ps;
		List<Professor> listaP = new ArrayList<Professor>();

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				Professor P = new Professor();
				P.setId(rs.getInt("id_pessoa"));
				P.setNome(rs.getString("nome"));
				P.setEmail(rs.getString("email"));
				P.setData(rs.getDate("data_nasc"));
				P.setCpf(rs.getString("cpf"));
				P.setIdProfessor(rs.getInt("id_professor"));
				P.setDepartamento(rs.getString("departamento"));

				listaP.add(P);

			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Erro ao consultar os professores: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return listaP;
	}

	@Override
	public List<Professor> consultar(int id) {
		String sql = "SELECT pe.id_pessoa, pe.nome, pe.email, pe.data_nasc, pe.cpf, p.id_professor, p.departamento "
				+ "FROM pessoa pe LEFT JOIN Professor p "
				+ "ON pe.id_pessoa = p.id_pessoa "
				+ "WHERE pe.id_pessoa = ?";
		PreparedStatement ps;
		List<Professor> listaP = new ArrayList<Professor>();

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Professor P = new Professor();
				P.setId(rs.getInt("id_pessoa"));
				P.setNome(rs.getString("nome"));
				P.setEmail(rs.getString("email"));
				P.setData(rs.getDate("data_nasc"));
				P.setCpf(rs.getString("cpf"));
				P.setIdProfessor(rs.getInt("id_professor"));
				P.setDepartamento(rs.getString("departamento"));
				
				listaP.add(P);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Erro ao consultar o professor: " + e.getMessage());
			e.printStackTrace();
			return null;
		}

		return listaP;
	}

	@Override
	public List<Professor> consultar(String s) {
		String sql = "SELECT pe.id_pessoa, pe.nome, pe.email, pe.data_nasc, pe.cpf, p.id_professor, p.departamento "
				+ "FROM pessoa pe "
				+ "LEFT JOIN Professor p "
				+ "ON pe.id_pessoa = p.id_pessoa "
				+ "WHERE pe.nome like ?";
		List<Professor> listaP = new ArrayList<Professor>();

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, "%" + s + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Professor P = new Professor();
				P.setId(rs.getInt("id_pessoa"));
				P.setNome(rs.getString("nome"));
				P.setEmail(rs.getString("email"));
				P.setData(rs.getDate("data_nasc"));
				P.setCpf(rs.getString("cpf"));
				P.setIdProfessor(rs.getInt("id_professor"));
				P.setDepartamento(rs.getString("departamento"));

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
