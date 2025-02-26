package InterfacesIMP;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Classes.Matricula;
import Classes.Nota;
import Conexao.Conexao;
import InterfacesDAO.NotaDAO;

public class NotaDAOImp implements NotaDAO {

	@Override
	public boolean inserir(Nota nota) {
		String sql = "INSERT INTO nota (id_matricula, n1, n2) VALUES (?, ?, ?)";

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setInt(1, nota.getMatricula().getIdMatricula());
			ps.setFloat(2, nota.getN1());
			ps.setFloat(3, nota.getN2());

			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			while (rs.next()) {
				nota.setIdNota(rs.getInt("id_nota"));
			}
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean alterar(Nota nota) {
		String sql = "UPDATE nota SET id_matricula = ?, n1 = ?, n2 = ? WHERE id_nota = ?";

		PreparedStatement ps;

		try {
			ps = Conexao.getConnection().prepareStatement(sql);

			ps.setInt(1, nota.getMatricula().getIdMatricula());
			ps.setFloat(2, nota.getN1());
			ps.setFloat(3, nota.getN2());
			ps.setFloat(4, nota.getIdNota());

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
		String sql = "DELETE FROM nota WHERE id_nota = ?";

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
	public List<Nota> consultar() {
			String sql = "SELECT n.id_nota, n.n1, n.n2, m.id_matricula "
					+ "FROM nota n "
					+ "LEFT JOIN matricula m ON m.id_matricula = n.id_matricula ";

			Statement ps;
			List<Nota> listaN = new ArrayList<Nota>();

			try {
				ps = Conexao.getConnection().prepareStatement(sql);
				ResultSet rs = ps.executeQuery(sql);

				while (rs.next()) {
					Nota n = new Nota();
					Matricula m = new Matricula();

					m.setIdMatricula(rs.getInt("id_matricula"));
					n.setMatricula(m);

					n.setIdNota(rs.getInt("id_nota"));
					n.setN1(rs.getFloat("n1"));
					n.setN2(rs.getFloat("n2"));

					listaN.add(n);

				}
				ps.close();
			} catch (SQLException e) {
				System.out.println("Erro ao consultar as matriculas: " + e.getMessage());
				e.printStackTrace();
				return null;
			}
			return listaN;
	}

	@Override
	public List<Nota> consultar(int id) {
		String sql = "SELECT n.id_nota, n.n1, n.n2, m.id_matricula "
				+ "FROM nota n "
				+ "LEFT JOIN matricula m ON m.id_matricula = n.id_matricula "
				+ "WHERE n.id_nota = ?";

		PreparedStatement ps;
		List<Nota> listaN = new ArrayList<Nota>();

		try {
			ps = Conexao.getConnection().prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Nota n = new Nota();
				Matricula m = new Matricula();

				m.setIdMatricula(rs.getInt("id_matricula"));
				n.setMatricula(m);

				n.setIdNota(rs.getInt("id_nota"));
				n.setN1(rs.getFloat("n1"));
				n.setN2(rs.getFloat("n2"));
				
				listaN.add(n);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Erro ao consultar as matriculas: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		return listaN;
	}

}
