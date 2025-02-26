package InterfacesIMP;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Classes.Aluno;
import Classes.Disciplina;
import Classes.Matricula;
import Conexao.Conexao;
import InterfacesDAO.MatriculaDAO;

public class MatriculaDAOImp implements MatriculaDAO {

	@Override
	public boolean inserir(Matricula matricula) {
		String sql = "INSERT INTO matricula (id_aluno, id_disciplina, data_matricula) VALUES (?, ?, ?)";

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, matricula.getAluno().getIdAluno());
			ps.setInt(2, matricula.getDisciplina().getIdDisciplina());
			ps.setDate(3, matricula.getData());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next()) {
				matricula.setIdMatricula(rs.getInt("id_disciplina"));
			}
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean alterar(Matricula matricula) {
		String sql = "UPDATE matricula SET id_aluno = ?, id_disciplina = ?, data_matricula = ? WHERE id_matricula = ?";

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);

			ps.setInt(1, matricula.getAluno().getIdAluno());
			ps.setInt(2, matricula.getDisciplina().getIdDisciplina());
			ps.setDate(3, matricula.getData());
			ps.setInt(4, matricula.getIdMatricula());

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
		String sql = "DELETE FROM matricula WHERE id_matricula = ?";

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
	public List<Matricula> consultar() {
		String sql = "SELECT m.id_matricula, m.data_matricula, a.id_aluno, d.id_disciplina, d.nome "
				+ "FROM matricula m "
				+ "LEFT JOIN disciplina d ON d.id_disciplina = m.id_disciplina "
				+ "LEFT JOIN aluno a ON a.id_aluno = m.id_aluno";

		Statement ps;
		List<Matricula> listaM = new ArrayList<Matricula>();

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				Matricula m = new Matricula();
				Aluno a = new Aluno();
				Disciplina d = new Disciplina();

				a.setIdAluno(rs.getInt("id_aluno"));
				m.setAluno(a);

				d.setIdDisciplina(rs.getInt("id_disciplina"));
				d.setNome(rs.getString("nome"));
				m.setDisciplina(d);

				m.setIdMatricula(rs.getInt("id_matricula"));
				m.setData(rs.getDate("data_matricula"));

				listaM.add(m);

			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Erro ao consultar as matriculas: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return listaM;
	}

	@Override
	public List<Matricula> consultar(int id) {
		String sql = "SELECT m.id_matricula, m.data_matricula, a.id_aluno, d.id_disciplina, d.nome "
				+ "FROM matricula m "
				+ "LEFT JOIN disciplina d ON d.id_disciplina = m.id_disciplina "
				+ "LEFT JOIN aluno a ON a.id_aluno = m.id_aluno "
				+ "WHERE m.id_matricula = ?";

		PreparedStatement ps;
		List<Matricula> listaM = new ArrayList<Matricula>();

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Matricula m = new Matricula();
				Aluno a = new Aluno();
				Disciplina d = new Disciplina();

				a.setIdAluno(rs.getInt("id_aluno"));
				m.setAluno(a);

				d.setIdDisciplina(rs.getInt("id_disciplina"));
				d.setNome(rs.getString("nome"));
				m.setDisciplina(d);

				m.setIdMatricula(rs.getInt("id_matricula"));
				m.setData(rs.getDate("data_matricula"));
				
				listaM.add(m);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Erro ao consultar as matriculas: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return listaM;
	}

}
