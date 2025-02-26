package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import Conexao.Conexao;
import Control.Data;
import InterfacesDAO.MatriculaDAO;
import InterfacesIMP.MatriculaDAOImp;
import Classes.Disciplina;
import Classes.Matricula;

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

public class TelaMatricula extends JDialog {

	private static JTextField txtIdMatricula;
	private static JFormattedTextField txtDataMatricula;
	private static JTextField txtIdAluno;
	private static JTextField txtIdDisciplina;
	private static JTextField txtNomeDisciplina;
	private static JTextField txtPesquisa;
	private static JTable tabela;
	private static MatriculaDAO matriculaDAO = new MatriculaDAOImp();
	private static List<Matricula> m;
	private static DefaultTableModel tableModel;

	public TelaMatricula(JFrame parent) {
		super(parent, "Curso", true);
		JDialog frame = new JDialog();
		frame.setModal(true);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		String[] colunas = { "Id Matricula", "Data", "Id Aluno", "Id Disciplina", "Nome" };

		tableModel = new DefaultTableModel(colunas, 0);
		tabela = new JTable(tableModel);

		JScrollPane scroll = new JScrollPane(tabela);

		DefaultTableCellRenderer centralizar = new DefaultTableCellRenderer();
		centralizar.setHorizontalAlignment(SwingConstants.CENTER);

		tabela.getColumnModel().getColumn(0).setCellRenderer(centralizar);
		tabela.getColumnModel().getColumn(1).setCellRenderer(centralizar);
		tabela.getColumnModel().getColumn(2).setCellRenderer(centralizar);
		tabela.getColumnModel().getColumn(3).setCellRenderer(centralizar);

		JPanel panelPesquisa = new JPanel();
		panelPesquisa.setBorder(new EmptyBorder(5, 5, 15, 5));
		frame.getContentPane().add(panelPesquisa, BorderLayout.NORTH);

		String[] elementos = { "Consultar todos", "Consultar ID" };
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
							m = matriculaDAO.consultar();
							for (Matricula M : m) {
								tableModel.addRow(
										new Object[] { M.getIdMatricula(), Data.formatarParaTela(M.getData()), M.getAluno().getIdAluno(),
												M.getDisciplina().getIdDisciplina(), M.getDisciplina().getNome() });
							}
						} else if (opcaoSelecionada.equals(elementos[1])) {
							m = matriculaDAO.consultar(Integer.valueOf(entradaUsuario));
							for (Matricula M : m) {
								tableModel.addRow(
										new Object[] { M.getIdMatricula(), Data.formatarParaTela(M.getData()), M.getAluno().getIdAluno(),
												M.getDisciplina().getIdDisciplina(), M.getDisciplina().getNome() });
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

		m = matriculaDAO.consultar();
		for (Matricula M : m) {
			tableModel.addRow(new Object[] { M.getIdMatricula(), Data.formatarParaTela(M.getData()), M.getAluno().getIdAluno(),
					M.getDisciplina().getIdDisciplina(), M.getDisciplina().getNome() });
		}

		JPanel painel = new JPanel();
		painel.setBorder(new EmptyBorder(5, 5, 50, 5));
		painel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel labelIdMatricula = new JLabel("Id Matricula:");
		painel.add(labelIdMatricula);

		txtIdMatricula = new JTextField();
		txtIdMatricula.setColumns(5);
		painel.add(txtIdMatricula);

		JLabel labelDataMatricula = new JLabel("Data Matricula:");
		painel.add(labelDataMatricula);

		MaskFormatter mascaraData = null;
		try {
		    mascaraData = new MaskFormatter("##-##-####");
		    mascaraData.setPlaceholderCharacter('_');
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		
		txtDataMatricula = new JFormattedTextField(mascaraData);
		painel.add(txtDataMatricula);
		txtDataMatricula.setColumns(14);

		JLabel labelIdAluno = new JLabel("Id Aluno:");
		painel.add(labelIdAluno);

		txtIdAluno = new JTextField();
		txtIdAluno.setColumns(5);
		painel.add(txtIdAluno);

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
		txtNomeDisciplina.setEditable(false);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtIdMatricula.getText().isEmpty() || txtDataMatricula.getText().isEmpty()
						|| txtIdAluno.getText().isEmpty() || txtIdDisciplina.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Dados incompletos!", "Erro!", JOptionPane.ERROR_MESSAGE);
				} else {
					Matricula m = new Matricula();
					m.setIdMatricula(Integer.valueOf(txtIdMatricula.getText()));
					
					String dataFormatada = Data.modificarFormatoData(txtDataMatricula.getText());
					m.setData(Date.valueOf(dataFormatada));
					
					m.getAluno().setIdAluno(Integer.valueOf(txtIdAluno.getText()));
					m.getDisciplina().setIdDisciplina(Integer.valueOf(txtIdDisciplina.getText()));

					matriculaDAO.inserir(m);
					JOptionPane.showMessageDialog(frame, "Disciplina inserida com sucesso!", "Sucesso!",
							JOptionPane.INFORMATION_MESSAGE);
					txtIdMatricula.setText(null);
					txtDataMatricula.setText(null);
					txtIdAluno.setText(null);
					txtIdDisciplina.setText(null);
					txtNomeDisciplina.setText(null);

				}
			}
		});
		painel.add(btnAdicionar);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtIdMatricula.getText().isEmpty() || txtDataMatricula.getText().isEmpty()
						|| txtIdAluno.getText().isEmpty() || txtIdDisciplina.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Dados incompletos!", "Erro!", JOptionPane.ERROR_MESSAGE);
				} else {
					Matricula m = new Matricula();
					m.setIdMatricula(Integer.valueOf(txtIdMatricula.getText()));
					
					String dataFormatada = Data.modificarFormatoData(txtDataMatricula.getText());
					m.setData(Date.valueOf(dataFormatada));
					
					m.getAluno().setIdAluno(Integer.valueOf(txtIdAluno.getText()));
					m.getDisciplina().setIdDisciplina(Integer.valueOf(txtIdDisciplina.getText()));

					int n = JOptionPane.showConfirmDialog(frame, "Deseja realmente atualizar essa disciplina?",
							"Confirmação", JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {

						matriculaDAO.alterar(m);

						JOptionPane.showMessageDialog(frame, "Disciplina alterada com sucesso!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);
						txtIdMatricula.setText(null);
						txtDataMatricula.setText(null);
						txtIdAluno.setText(null);
						txtIdDisciplina.setText(null);
						txtNomeDisciplina.setText(null);
					}
				}
			}
		});
		painel.add(btnAtualizar);

		JButton btnRemover = new JButton("Remover");
		painel.add(btnRemover);
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtDataMatricula.getText().isEmpty()) {
					int n = JOptionPane.showConfirmDialog(parent, "Deseja realmente remover essa disciplina?",
							"Confirmação", JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {

						matriculaDAO.remover(Integer.valueOf(txtIdMatricula.getText()));
						JOptionPane.showMessageDialog(parent, "Disciplina removida com sucesso!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);

						txtIdMatricula.setText(null);
						txtDataMatricula.setText(null);
						txtIdAluno.setText(null);
						txtIdDisciplina.setText(null);
						txtNomeDisciplina.setText(null);

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
				Matricula M = m.get(row);
				txtIdMatricula.setText(String.valueOf(M.getIdMatricula()));
				String dataFormatada = Data.formatarParaTela(M.getData());
				txtDataMatricula.setText(dataFormatada);
				txtIdAluno.setText(String.valueOf(M.getAluno().getIdAluno()));
				txtIdDisciplina.setText(String.valueOf(M.getDisciplina().getIdDisciplina()));
				txtNomeDisciplina.setText(M.getDisciplina().getNome());

			}
		});

		frame.setSize(775, 350);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}