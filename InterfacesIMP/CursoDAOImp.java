package InterfacesIMP;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Classes.Curso;
import Conexao.Conexao;
import InterfacesDAO.CursoDAO;

public class CursoDAOImp implements CursoDAO {

	@Override
	public boolean inserir(Curso curso) {
		String sql = "INSERT INTO curso (nome, carga_horaria) VALUES (?,?)";

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, curso.getNome());
			ps.setInt(2, curso.getCargaHoraria());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next()) {
				curso.setId(rs.getInt(1));
			}
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean alterar(Curso curso) {
		String sql = "UPDATE curso SET nome = ?, carga_horaria = ? WHERE id_curso = ?";

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);

			ps.setString(1, curso.getNome());
			ps.setInt(2, curso.getCargaHoraria());
			ps.setInt(3, curso.getId());

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
		String sql = "DELETE FROM curso WHERE id_curso = ?";
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
	public List<Curso> consultar() {
		String sql = "SELECT * FROM curso";
		Statement ps;
		List<Curso> listaC = new ArrayList<Curso>();

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				Curso C = new Curso();
				C.setId(rs.getInt("id_curso"));
				C.setNome(rs.getString("nome"));
				C.setCargaHoraria(rs.getInt("carga_horaria"));

				listaC.add(C);

			}

		} catch (SQLException e) {
			System.out.println("Erro ao consultar os cursos: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return listaC;
	}

	@Override
	public List<Curso> consultar(int id) {
		String sql = "SELECT * FROM curso WHERE id_curso = ?";
		PreparedStatement ps;
		List<Curso> listaC = new ArrayList<Curso>();

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Curso C = new Curso();
				C.setId(rs.getInt("id_curso"));
				C.setNome(rs.getString("nome"));
				C.setCargaHoraria(rs.getInt("carga_horaria"));
				
				listaC.add(C);
			}

		} catch (SQLException e) {
			System.out.println("Erro ao consultar as pessoas: " + e.getMessage());
			e.printStackTrace();
			return null;
		}

		return listaC;
	}

	@Override
	public List<Curso> consultar(String s) {
		String sql = "SELECT * FROM curso WHERE nome like ?";
		List<Curso> listaC = new ArrayList<Curso>();

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, "%" + s + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Curso C = new Curso();
				C.setId(rs.getInt("id_curso"));
				C.setNome(rs.getString("nome"));
				C.setCargaHoraria(rs.getInt("carga_horaria"));

				listaC.add(C);
			}

		} catch (SQLException e) {
			System.out.println("Erro ao consultar as pessoas: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return listaC;
	}
}
