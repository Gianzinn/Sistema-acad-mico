package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Conexao.Conexao;

import InterfacesDAO.DisciplinaDAO;
import InterfacesIMP.DisciplinaDAOImp;
import Classes.Curso;
import Classes.Disciplina;
import Classes.Professor;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.TextField;
import javax.swing.JComboBox;

public class TelaDisciplina extends JDialog {

	private static JTextField txtIdDisciplina;
	private static JTextField txtNomeDisciplina;
	private static JTextField txtIdCurso;
	private static JTextField txtNome;
	private static JTextField txtIdProfessor;
	private static JTextField txtPesquisa;
	private static JTable tabela;
	private static DisciplinaDAO disciplinaDAO = new DisciplinaDAOImp();
	private static List<Disciplina> d;
	private static DefaultTableModel tableModel;

	public TelaDisciplina(JFrame parent) {
		super(parent, "Curso", true);
		JDialog frame = new JDialog();
		frame.setModal(true);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		String[] colunas = { "Id disciplina", "Nome Disciplina", "Id curso", "Nome curso", "Id Professor" };

		tableModel = new DefaultTableModel(colunas, 0);
		tabela = new JTable(tableModel);

		JScrollPane scroll = new JScrollPane(tabela);

		DefaultTableCellRenderer centralizar = new DefaultTableCellRenderer();
		centralizar.setHorizontalAlignment(SwingConstants.CENTER);

		tabela.getColumnModel().getColumn(0).setCellRenderer(centralizar);
		tabela.getColumnModel().getColumn(2).setCellRenderer(centralizar);
		tabela.getColumnModel().getColumn(4).setCellRenderer(centralizar);

		JPanel panelPesquisa = new JPanel();
		panelPesquisa.setBorder(new EmptyBorder(5, 5, 15, 5));
		frame.getContentPane().add(panelPesquisa, BorderLayout.NORTH);

		String[] elementos = { "Consultar todos", "Consultar nome", "Consultar ID" };
		JComboBox comboBox = new JComboBox(elementos);
		comboBox.setBounds(10, 11, 86, 22);
		panelPesquisa.add(comboBox);

		txtPesquisa = new JTextField();
		panelPesquisa.add(txtPesquisa);
		txtPesquisa.setColumns(20);

		comboBox.addActionListener(e -> txtPesquisa.setText(""));

		txtPesquisa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String opcaoSelecionada = (String) comboBox.getSelectedItem();
					String entradaUsuario = txtPesquisa.getText();

					try {
						tableModel.setRowCount(0);
						if (opcaoSelecionada.equals(elementos[0])) {
							d = disciplinaDAO.consultar();
							for (Disciplina D : d) {
								tableModel.addRow(new Object[] { D.getIdDisciplina(), D.getNome(), D.getCurso().getId(),
										D.getCurso().getNome(), D.getProfessor().getIdProfessor() });
							}

						} else if (opcaoSelecionada.equals(elementos[1])) {
							d = disciplinaDAO.consultar(entradaUsuario);
							for (Disciplina D : d) {
								tableModel.addRow(new Object[] { D.getIdDisciplina(), D.getNome(), D.getCurso().getId(),
										D.getCurso().getNome(), D.getProfessor().getIdProfessor() });
							}

						} else if (opcaoSelecionada.equals(elementos[2])) {
							d = disciplinaDAO.consultar(Integer.valueOf(entradaUsuario));
							for (Disciplina D : d) {
								tableModel.addRow(new Object[] { D.getIdDisciplina(), D.getNome(), D.getCurso().getId(),
										D.getCurso().getNome(), D.getProfessor().getIdProfessor() });
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(null, "Erro ao realizar a consulta: " + ex.getMessage());
					}
				}
			}
		});

		frame.getContentPane().add(scroll, BorderLayout.CENTER);

		d = disciplinaDAO.consultar();
		for (Disciplina D : d) {
			tableModel.addRow(new Object[] { D.getIdDisciplina(), D.getNome(), D.getCurso().getId(),
					D.getCurso().getNome(), D.getProfessor().getIdProfessor() });
		}

		JPanel painel = new JPanel();
		painel.setBorder(new EmptyBorder(5, 5, 50, 5));
		painel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel labelIdDisciplina = new JLabel("Id Disciplina:");
		painel.add(labelIdDisciplina);

		txtIdDisciplina = new JTextField();
		txtIdDisciplina.setColumns(5);
		painel.add(txtIdDisciplina);

		JLabel labelNomeDisciplina = new JLabel("Nome Disciplina:");
		painel.add(labelNomeDisciplina);

		txtNomeDisciplina = new JTextField();
		txtNomeDisciplina.setColumns(20);
		painel.add(txtNomeDisciplina);

		JLabel labelId = new JLabel("ID Curso:");
		painel.add(labelId);

		txtIdCurso = new JTextField();
		painel.add(txtIdCurso);
		txtIdCurso.setColumns(5);

		JLabel labelIdProfessor = new JLabel("Id Professor:");
		painel.add(labelIdProfessor);

		txtIdProfessor = new JTextField();
		txtIdProfessor.setColumns(5);
		painel.add(txtIdProfessor);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtIdCurso.getText().isEmpty() || txtNome.getText().isEmpty() || txtIdDisciplina.getText().isEmpty()
						|| txtNomeDisciplina.getText().isEmpty() || txtIdProfessor.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Dados incompletos!", "Erro!", JOptionPane.ERROR_MESSAGE);
				} else {
					Disciplina d = new Disciplina();
					d.setIdDisciplina(Integer.valueOf(txtIdDisciplina.getText()));
					d.setNome(txtNomeDisciplina.getText());
					d.getCurso().setId(Integer.valueOf(txtIdCurso.getText()));
					d.getProfessor().setIdProfessor(Integer.valueOf(txtIdProfessor.getText()));

					disciplinaDAO.inserir(d);
					JOptionPane.showMessageDialog(frame, "Disciplina inserida com sucesso!", "Sucesso!",
							JOptionPane.INFORMATION_MESSAGE);
					txtIdDisciplina.setText(null);
					txtNomeDisciplina.setText(null);
					txtIdCurso.setText(null);
					txtIdProfessor.setText(null);

				}
			}
		});
		painel.add(btnAdicionar);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtIdCurso.getText().isEmpty() || txtIdDisciplina.getText().isEmpty()
						|| txtNomeDisciplina.getText().isEmpty() || txtIdProfessor.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Dados incompletos!", "Erro!", JOptionPane.ERROR_MESSAGE);
				} else {
					Disciplina d = new Disciplina();
					d.getCurso().setId(Integer.valueOf(txtIdCurso.getText()));
					d.setIdDisciplina(Integer.valueOf(txtIdDisciplina.getText()));
					d.setNome(txtNomeDisciplina.getText());
					d.getProfessor().setIdProfessor(Integer.valueOf(txtIdProfessor.getText()));

					int n = JOptionPane.showConfirmDialog(frame, "Deseja realmente atualizar essa disciplina?",
							"Confirmação", JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {

						disciplinaDAO.alterar(d);

						JOptionPane.showMessageDialog(frame, "Disciplina alterada com sucesso!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);
						txtIdDisciplina.setText(null);
						txtNomeDisciplina.setText(null);
						txtIdCurso.setText(null);
						txtIdProfessor.setText(null);
					}
				}
			}
		});
		painel.add(btnAtualizar);

		JButton btnRemover = new JButton("Remover");
		painel.add(btnRemover);
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtNomeDisciplina.getText().isEmpty()) {
					int n = JOptionPane.showConfirmDialog(parent, "Deseja realmente remover essa disciplina?",
							"Confirmação", JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {

						disciplinaDAO.remover(Integer.valueOf(txtIdDisciplina.getText()));
						JOptionPane.showMessageDialog(parent, "Disciplina removida com sucesso!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);

						txtIdDisciplina.setText(null);
						txtNomeDisciplina.setText(null);
						txtIdCurso.setText(null);
						txtIdProfessor.setText(null);

					}
				} else {
					JOptionPane.showMessageDialog(parent, "Dados incompletos!", "Erro!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		frame.getContentPane().add(painel, BorderLayout.SOUTH);
		tabela.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tabela.rowAtPoint(e.getPoint());
				Disciplina D = d.get(row);
				txtIdDisciplina.setText(String.valueOf(D.getIdDisciplina()));
				txtNomeDisciplina.setText(D.getNome());
				txtIdCurso.setText(String.valueOf(D.getCurso().getId()));
				txtIdProfessor.setText(String.valueOf(D.getProfessor().getIdProfessor()));

			}
		});

		frame.setSize(775, 350);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}