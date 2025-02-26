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

import InterfacesDAO.NotaDAO;
import InterfacesIMP.NotaDAOImp;
import Classes.Nota;
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

public class TelaNota extends JDialog {

	private static JTextField txtIdNota;
	private static JTextField txtNota1;
	private static JTextField txtNota2;
	private static JTextField txtIdMatricula;
	private static JTextField txtPesquisa;
	private static JTable tabela;
	private static NotaDAO notaDAO = new NotaDAOImp();
	private static List<Nota> n;
	private static DefaultTableModel tableModel;

	public TelaNota(JFrame parent) {
		super(parent, "Curso", true);
		JDialog frame = new JDialog();
		frame.setModal(true);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		String[] colunas = { "Id Nota", "Nota 1", "Nota 2", "Id Matricula" };

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
							n = notaDAO.consultar();
							for (Nota N : n) {
								tableModel.addRow(new Object[] { N.getIdNota(), N.getN1(), N.getN2(),
										N.getMatricula().getIdMatricula() });
							}

						} else if (opcaoSelecionada.equals(elementos[1])) {
							n = notaDAO.consultar(Integer.valueOf(entradaUsuario));
							for (Nota N : n) {
								tableModel.addRow(new Object[] { N.getIdNota(), N.getN1(), N.getN2(),
										N.getMatricula().getIdMatricula() });
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

		n = notaDAO.consultar();
		for (Nota N : n) {
			tableModel.addRow(new Object[] { N.getIdNota(), N.getN1(), N.getN2(), N.getMatricula().getIdMatricula() });
		}

		JPanel painel = new JPanel();
		painel.setBorder(new EmptyBorder(5, 5, 50, 5));
		painel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel labelIdNota = new JLabel("Id Nota:");
		painel.add(labelIdNota);

		txtIdNota = new JTextField();
		txtIdNota.setColumns(5);
		painel.add(txtIdNota);

		JLabel labelNota1 = new JLabel("Nota 1:");
		painel.add(labelNota1);

		txtNota1 = new JTextField();
		txtNota1.setColumns(5);
		painel.add(txtNota1);

		JLabel labelNota2 = new JLabel("Nota 2:");
		painel.add(labelNota2);

		txtNota2 = new JTextField();
		txtNota2.setColumns(5);
		painel.add(txtNota2);

		JLabel labelIdMatricula = new JLabel("Id Matricula:");
		painel.add(labelIdMatricula);

		txtIdMatricula = new JTextField();
		txtIdMatricula.setColumns(5);
		painel.add(txtIdMatricula);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtIdNota.getText().isEmpty() || txtNota1.getText().isEmpty() || txtNota2.getText().isEmpty()
						|| txtIdMatricula.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Dados incompletos!", "Erro!", JOptionPane.ERROR_MESSAGE);

				} else {
					Nota n = new Nota();
					n.setIdNota(Integer.valueOf(txtIdNota.getText()));
					n.setN1(Float.valueOf(txtNota1.getText()));
					n.setN2(Float.valueOf(txtNota2.getText()));
					n.getMatricula().setIdMatricula(Integer.valueOf(txtIdMatricula.getText()));

					notaDAO.inserir(n);
					JOptionPane.showMessageDialog(frame, "Disciplina inserida com sucesso!", "Sucesso!",
							JOptionPane.INFORMATION_MESSAGE);
					txtIdNota.setText(null);
					txtNota1.setText(null);
					txtNota2.setText(null);
					txtIdMatricula.setText(null);

				}
			}
		});
		painel.add(btnAdicionar);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtIdNota.getText().isEmpty() || txtNota1.getText().isEmpty() || txtNota2.getText().isEmpty()
						|| txtIdMatricula.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Dados incompletos!", "Erro!", JOptionPane.ERROR_MESSAGE);

				} else {
					Nota n = new Nota();
					n.setIdNota(Integer.valueOf(txtIdNota.getText()));
					n.setN1(Float.valueOf(txtNota1.getText()));
					n.setN2(Float.valueOf(txtNota2.getText()));
					n.getMatricula().setIdMatricula(Integer.valueOf(txtIdMatricula.getText()));

					int N = JOptionPane.showConfirmDialog(frame, "Deseja realmente atualizar essa disciplina?",
							"Confirmação", JOptionPane.YES_NO_OPTION);
					if (N == JOptionPane.YES_OPTION) {

						notaDAO.alterar(n);

						JOptionPane.showMessageDialog(frame, "Disciplina alterada com sucesso!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);
						txtIdNota.setText(null);
						txtNota1.setText(null);
						txtNota2.setText(null);
						txtIdMatricula.setText(null);
					}
				}
			}
		});
		painel.add(btnAtualizar);

		JButton btnRemover = new JButton("Remover");
		painel.add(btnRemover);
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtIdNota.getText().isEmpty()) {
					int n = JOptionPane.showConfirmDialog(parent, "Deseja realmente remover essa disciplina?",
							"Confirmação", JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {

						notaDAO.remover(Integer.valueOf(txtIdNota.getText()));
						JOptionPane.showMessageDialog(parent, "Disciplina removida com sucesso!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);

						txtIdNota.setText(null);
						txtNota1.setText(null);
						txtNota2.setText(null);
						txtIdMatricula.setText(null);

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
				Nota N = n.get(row);
				txtIdNota.setText(String.valueOf(N.getIdNota()));
				txtNota1.setText(String.valueOf(N.getN1()));
				txtNota2.setText(String.valueOf(N.getN2()));
				txtIdMatricula.setText(String.valueOf(N.getMatricula().getIdMatricula()));

			}
		});

		frame.setSize(775, 350);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}