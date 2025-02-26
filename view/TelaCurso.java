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

import InterfacesDAO.CursoDAO;
import InterfacesIMP.CursoDAOImp;
import Classes.Curso;

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

public class TelaCurso extends JDialog {

	private static JTextField txtId;
	private static JTextField txtNome;
	private static JTextField txtCargaHoraria;
	private static JTextField txtPesquisa;
	private static JTable tabela;
	private static CursoDAO cursoDAO = new CursoDAOImp();
	private static List<Curso> c;
	private static DefaultTableModel tableModel;

	public TelaCurso(JFrame parent) {
		super(parent, "Curso", true);
		JDialog frame = new JDialog();
		frame.setModal(true);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		String[] colunas = { "Id", "Nome", "Carga Horaria" };

		tableModel = new DefaultTableModel(colunas, 0);
		tabela = new JTable(tableModel);

		JScrollPane scroll = new JScrollPane(tabela);

		DefaultTableCellRenderer centralizar = new DefaultTableCellRenderer();
		centralizar.setHorizontalAlignment(SwingConstants.CENTER);

		tabela.getColumnModel().getColumn(0).setCellRenderer(centralizar);
		tabela.getColumnModel().getColumn(2).setCellRenderer(centralizar);

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
							c = cursoDAO.consultar();
							for (Curso C : c) {
								tableModel.addRow(new Object[] { C.getId(), C.getNome(), C.getCargaHoraria() });
							}

						} else if (opcaoSelecionada.equals(elementos[1])) {
							c = cursoDAO.consultar(entradaUsuario);
							for (Curso C : c) {
								tableModel.addRow(new Object[] { C.getId(), C.getNome(), C.getCargaHoraria() });
							}

						} else if (opcaoSelecionada.equals(elementos[2])) {
							c = cursoDAO.consultar(Integer.valueOf(entradaUsuario));
							for (Curso C : c) {
								tableModel.addRow(new Object[] { C.getId(), C.getNome(), C.getCargaHoraria() });
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

		c = cursoDAO.consultar();
		for (Curso C : c) {
			tableModel.addRow(new Object[] { C.getId(), C.getNome(), C.getCargaHoraria() });
		}

		JPanel painel = new JPanel();
		painel.setBorder(new EmptyBorder(5, 5, 50, 5));
		painel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel labelId = new JLabel("ID:");
		painel.add(labelId);

		txtId = new JTextField();
		painel.add(txtId);
		txtId.setColumns(5);

		JLabel labelNome = new JLabel("Nome:");
		painel.add(labelNome);

		txtNome = new JTextField();
		painel.add(txtNome);
		txtNome.setColumns(25);

		JLabel labelCargaHoraria = new JLabel("Carga Horaria:");
		painel.add(labelCargaHoraria);

		txtCargaHoraria = new JTextField();
		txtCargaHoraria.setColumns(5);
		painel.add(txtCargaHoraria);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtId.getText().isEmpty() || txtNome.getText().isEmpty() || txtCargaHoraria.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Dados incompletos!", "Erro!", JOptionPane.ERROR_MESSAGE);
				} else {
					Curso c = new Curso();
					c.setId(Integer.valueOf(txtId.getText()));
					c.setNome(txtNome.getText());
					c.setCargaHoraria(Integer.valueOf(txtCargaHoraria.getText()));

					cursoDAO.inserir(c);
					JOptionPane.showMessageDialog(frame, "Curso inserido com sucesso!", "Sucesso!",
							JOptionPane.INFORMATION_MESSAGE);
					txtId.setText(null);
					txtNome.setText(null);
					txtCargaHoraria.setText(null);

				}
			}
		});
		painel.add(btnAdicionar);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtId.getText().isEmpty() || txtNome.getText().isEmpty() || txtCargaHoraria.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Dados incompletos!", "Erro!", JOptionPane.ERROR_MESSAGE);
				} else {
					Curso c = new Curso();
					c.setId(Integer.valueOf(txtId.getText()));
					c.setNome(txtNome.getText());
					c.setCargaHoraria(Integer.valueOf(txtCargaHoraria.getText()));

					int n = JOptionPane.showConfirmDialog(frame, "Deseja realmente atualizar esse curso?",
							"Confirmação", JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {

						cursoDAO.alterar(c);

						JOptionPane.showMessageDialog(frame, "Curso alterado com sucesso!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);

						txtId.setText(null);
						txtNome.setText(null);
						txtCargaHoraria.setText(null);
					}
				}
			}
		});
		painel.add(btnAtualizar);

		JButton btnRemover = new JButton("Remover");
		painel.add(btnRemover);
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtId.getText().isEmpty()) {
					int n = JOptionPane.showConfirmDialog(parent, "Deseja realmente remover esse curso?", "Confirmação",
							JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {

						cursoDAO.remover(Integer.valueOf(txtId.getText()));
						JOptionPane.showMessageDialog(parent, "Curso removido com sucesso!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);

						txtId.setText(null);
						txtNome.setText(null);
						txtCargaHoraria.setText(null);

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
				Curso curso = c.get(row);
				txtId.setText(String.valueOf(curso.getId()));
				txtNome.setText(curso.getNome());
				txtCargaHoraria.setText(String.valueOf(curso.getCargaHoraria()));
			}
		});

		frame.setSize(775, 350);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}