package InterfacesIMP;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Classes.Curso;
import Classes.Disciplina;
import Classes.Professor;
import Conexao.Conexao;
import InterfacesDAO.DisciplinaDAO;

public class DisciplinaDAOImp implements DisciplinaDAO {

	@Override
	public boolean inserir(Disciplina disciplina) {
		String sql = "INSERT INTO disciplina (nome, id_curso, id_professor) VALUES (?, ?, ?)";

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, disciplina.getNome());
			ps.setInt(2, disciplina.getCurso().getId());
			ps.setInt(3, disciplina.getProfessor().getIdProfessor());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next()) {
				disciplina.setIdDisciplina(rs.getInt("id_disciplina"));
			}
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean alterar(Disciplina disciplina) {
		String sql = "UPDATE disciplina SET nome = ?, id_curso = ?, id_professor = ? WHERE id_disciplina = ?";

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);

			ps.setString(1, disciplina.getNome());
			ps.setInt(2, disciplina.getCurso().getId());
			ps.setInt(3, disciplina.getProfessor().getIdProfessor());
			ps.setInt(4, disciplina.getIdDisciplina());

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
		String sql = "DELETE FROM disciplina WHERE id_disciplina = ?";

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
	public List<Disciplina> consultar() {
		String sql = "SELECT d.id_disciplina, d.nome as nome_disciplina, c.id_curso, c.nome, p.id_professor "
				+ "FROM disciplina d "
				+ "LEFT JOIN curso c ON d.id_curso = c.id_curso "
				+ "LEFT JOIN Professor p ON d.id_professor = p.id_professor;";

		Statement ps;
		List<Disciplina> listaD = new ArrayList<Disciplina>();

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				Disciplina d = new Disciplina();
				Professor p = new Professor();
				Curso c = new Curso();

				c.setId(rs.getInt("id_curso"));
				c.setNome(rs.getString("nome"));
				d.setCurso(c);

				p.setIdProfessor(rs.getInt("id_professor"));
				d.setProfessor(p);

				d.setIdDisciplina(rs.getInt("id_disciplina"));
				d.setNome(rs.getString("nome_disciplina"));

				listaD.add(d);

			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Erro ao consultar as pessoas: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return listaD;
	}

	@Override
	public List<Disciplina> consultar(int id) {
		String sql = "SELECT d.id_disciplina, d.nome as nome_disciplina, c.id_curso, c.nome, p.id_professor "
				+ "FROM disciplina d "
				+ "LEFT JOIN curso c ON d.id_curso = c.id_curso "
				+ "LEFT JOIN Professor p ON d.id_professor = p.id_professor "
				+ "WHERE id_disciplina = ?";
		PreparedStatement ps;
		List<Disciplina> listaD = new ArrayList<Disciplina>();

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Disciplina d = new Disciplina();
				Professor p = new Professor();
				Curso c = new Curso();

				c.setId(rs.getInt("id_curso"));
				c.setNome(rs.getString("nome"));
				d.setCurso(c);

				p.setIdProfessor(rs.getInt("id_professor"));
				d.setProfessor(p);

				d.setIdDisciplina(rs.getInt("id_disciplina"));
				d.setNome(rs.getString("nome_disciplina"));
				
				listaD.add(d);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Erro ao consultar as disciplinas: " + e.getMessage());
			e.printStackTrace();
			return null;
		}

		return listaD;
	}

	@Override
	public List<Disciplina> consultar(String s) {
		String sql = "SELECT d.id_disciplina, d.nome as nome_disciplina, c.id_curso, c.nome, p.id_professor "
				+ "FROM disciplina d "
				+ "LEFT JOIN curso c ON d.id_curso = c.id_curso "
				+ "LEFT JOIN Professor p ON d.id_professor = p.id_professor "
				+ "WHERE d.nome like ?";
		List<Disciplina> listaD = new ArrayList<Disciplina>();

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setString(1, "%" + s + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Disciplina d = new Disciplina();
				Professor p = new Professor();
	            Curso c = new Curso(); 
	            
	            c.setId(rs.getInt("c.id_curso"));
	            c.setNome(rs.getString("c.nome"));
	            d.setCurso(c); 
	            
	            p.setIdProfessor(rs.getInt("p.id_professor"));
	            d.setProfessor(p);
	            
	            d.setIdDisciplina(rs.getInt("d.id_disciplina"));
	            d.setNome(rs.getString("d.nome_disciplina"));
	            
				listaD.add(d);

			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Erro ao consultar as disciplinas: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return listaD;
	}

}
