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
import Control.Cpf;
import Control.Data;
import InterfacesDAO.ProfessorDAO;
import InterfacesIMP.ProfessorDAOImp;
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

public class TelaProfessor extends JDialog {

	private static JTextField txtId;
	private static JTextField txtNome;
	private static JTextField txtEmail;
	private static JFormattedTextField txtDtNasc;
	private static JFormattedTextField txtCpf;
	private static JTextField txtPesquisa;
	private static JTextField txtIdProfessor;
	private static JTextField txtDepartamento;
	private static JTable tabela;
	private static ProfessorDAO professorDAO = new ProfessorDAOImp();
	private static List<Professor> p;
	private static DefaultTableModel tableModel;

	public TelaProfessor(JFrame parent) {
		super(parent, "Professor", true);
		JDialog frame = new JDialog();
		frame.setModal(true);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		String[] colunas = { "Id", "Nome", "Email", "Data de nascimento", "Cpf", "Id Professor", "Departamento" };

		tableModel = new DefaultTableModel(colunas, 0);
		tabela = new JTable(tableModel);

		JScrollPane scroll = new JScrollPane(tabela);

		DefaultTableCellRenderer centralizar = new DefaultTableCellRenderer();
		centralizar.setHorizontalAlignment(SwingConstants.CENTER);

		tabela.getColumnModel().getColumn(0).setCellRenderer(centralizar);
		tabela.getColumnModel().getColumn(3).setCellRenderer(centralizar);
		tabela.getColumnModel().getColumn(4).setCellRenderer(centralizar);
		tabela.getColumnModel().getColumn(5).setCellRenderer(centralizar);

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

		professorDAO.consultar();

		txtPesquisa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String opcaoSelecionada = (String) comboBox.getSelectedItem();
					String entradaUsuario = txtPesquisa.getText();

					try {
						tableModel.setRowCount(0);
						if (opcaoSelecionada.equals(elementos[0])) {
							p = professorDAO.consultar();
							for (Professor P : p) {
								tableModel.addRow(new Object[] { P.getId(), P.getNome(), P.getEmail(), Data.formatarParaTela(P.getData()),
										Cpf.formatarCpf(P.getCpf()), P.getIdProfessor(), P.getDepartamento() });
							}

						} else if (opcaoSelecionada.equals(elementos[1])) {
							p = professorDAO.consultar(entradaUsuario);
							for (Professor P : p) {
								tableModel.addRow(new Object[] { P.getId(), P.getNome(), P.getEmail(), Data.formatarParaTela(P.getData()), Cpf.formatarCpf(P.getCpf()),
										P.getIdProfessor(), P.getDepartamento() });
							}

						} else if (opcaoSelecionada.equals(elementos[2])) {
							p = professorDAO.consultar(Integer.valueOf(entradaUsuario));
							for (Professor P : p) {
								tableModel.addRow(new Object[] { P.getId(), P.getNome(), P.getEmail(), P.getData(), Cpf.formatarCpf(P.getCpf()),
										P.getIdProfessor(), P.getDepartamento() });
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

		p = professorDAO.consultar();
		for (Professor P : p) {
			tableModel.addRow(new Object[] { P.getId(), P.getNome(), P.getEmail(), Data.formatarParaTela(P.getData()), Cpf.formatarCpf(P.getCpf()),
					P.getIdProfessor(), P.getDepartamento() });
		}

		JPanel painel = new JPanel();
		painel.setBorder(new EmptyBorder(5, 5, 50, 5));
		painel.setLayout(new FlowLayout(FlowLayout.LEFT));

		JLabel labelId = new JLabel("ID:");
		painel.add(labelId);

		txtId = new JTextField();
		painel.add(txtId);
		txtId.setColumns(5);
		txtId.setEditable(false);

		JLabel labelNome = new JLabel("Nome:");
		painel.add(labelNome);

		txtNome = new JTextField();
		painel.add(txtNome);
		txtNome.setColumns(25);
		txtNome.setEditable(false);

		JLabel labelEmail = new JLabel("Email:");
		painel.add(labelEmail);

		txtEmail = new JTextField();
		txtEmail.setColumns(25);
		painel.add(txtEmail);
		txtEmail.setEditable(false);

		JLabel labelDtNasc = new JLabel("Dt Nascimento:");
		painel.add(labelDtNasc);

		MaskFormatter mascaraData = null;
		try {
		    mascaraData = new MaskFormatter("##-##-####");
		    mascaraData.setPlaceholderCharacter('_');
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		
		txtDtNasc = new JFormattedTextField(mascaraData);
		painel.add(txtDtNasc);
		txtDtNasc.setColumns(15);
		txtDtNasc.setEditable(false);

		JLabel labelCpf = new JLabel("Cpf:");
		painel.add(labelCpf);

		MaskFormatter mascaraCPF = null;

        try {
            mascaraCPF = new MaskFormatter("###.###.###-##");
            mascaraCPF.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
		txtCpf = new JFormattedTextField(mascaraCPF);
		painel.add(txtCpf);
		txtCpf.setColumns(15);
		txtCpf.setEditable(false);

		JLabel labelIdAluno = new JLabel("Id Professor:");
		painel.add(labelIdAluno);

		txtIdProfessor = new JTextField();
		painel.add(txtIdProfessor);
		txtIdProfessor.setColumns(5);

		JLabel labelRa = new JLabel("Departamento:");
		painel.add(labelRa);

		txtDepartamento = new JTextField();
		painel.add(txtDepartamento);
		txtDepartamento.setColumns(20);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtNome.getText().isEmpty() || txtEmail.getText().isEmpty() || txtDtNasc.getText().isEmpty()
						|| txtCpf.getText().isEmpty() || txtIdProfessor.getText().isEmpty()
						|| txtDepartamento.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Dados incompletos!", "Erro!", JOptionPane.ERROR_MESSAGE);

				} else {
					Professor p = new Professor();
					p.setId(Integer.valueOf(txtId.getText()));
					p.setNome(txtNome.getText());
					p.setEmail(txtEmail.getText());
					String dataFormatada = Data.modificarFormatoData(txtDtNasc.getText());
					p.setData(Date.valueOf(dataFormatada));
					p.setCpf(txtCpf.getText());
					p.setIdProfessor(Integer.valueOf(txtIdProfessor.getText()));
					p.setDepartamento(txtDepartamento.getText());

					professorDAO.inserir(p);
					JOptionPane.showMessageDialog(frame, "Professor inserido com sucesso!", "Sucesso!",
							JOptionPane.INFORMATION_MESSAGE);
					txtId.setText(null);
					txtNome.setText(null);
					txtEmail.setText(null);
					txtDtNasc.setText(null);
					txtCpf.setText(null);
					txtIdProfessor.setText(null);
					txtDepartamento.setText(null);

				}
			}
		});
		painel.add(btnAdicionar);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtNome.getText().isEmpty() || txtEmail.getText().isEmpty() || txtDtNasc.getText().isEmpty()
						|| txtCpf.getText().isEmpty() || txtIdProfessor.getText().isEmpty()
						|| txtDepartamento.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Dados incompletos!", "Erro!", JOptionPane.ERROR_MESSAGE);

				} else {
					Professor p = new Professor();
					p.setId(Integer.valueOf(txtId.getText()));
					p.setNome(txtNome.getText());
					p.setEmail(txtEmail.getText());
					String dataFormatada = Data.modificarFormatoData(txtDtNasc.getText());
					p.setData(Date.valueOf(dataFormatada));
					p.setCpf(txtCpf.getText());
					p.setIdProfessor(Integer.valueOf(txtIdProfessor.getText()));
					p.setDepartamento(txtDepartamento.getText());

					int n = JOptionPane.showConfirmDialog(frame, "Deseja realmente atualizar esse professor?",
							"Confirmação", JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {

						professorDAO.alterar(p);

						JOptionPane.showMessageDialog(frame, "Professor alterado com sucesso!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);
						txtId.setText(null);
						txtNome.setText(null);
						txtEmail.setText(null);
						txtDtNasc.setText(null);
						txtCpf.setText(null);
						txtIdProfessor.setText(null);
						txtDepartamento.setText(null);
					}
				}
			}
		});
		painel.add(btnAtualizar);

		JButton btnRemover = new JButton("Remover");
		painel.add(btnRemover);
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtCpf.getText().isEmpty()) {
					int n = JOptionPane.showConfirmDialog(parent, "Deseja realmente remover esse professor?",
							"Confirmação", JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {

						professorDAO.remover(Integer.valueOf(txtIdProfessor.getText()));
						JOptionPane.showMessageDialog(parent, "Professor removido com sucesso!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);

						txtId.setText(null);
						txtNome.setText(null);
						txtEmail.setText(null);
						txtDtNasc.setText(null);
						txtCpf.setText(null);
						txtIdProfessor.setText(null);
						txtDepartamento.setText(null);

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
				Professor professor = p.get(row);
				txtId.setText(String.valueOf(professor.getId()));
				txtNome.setText(professor.getNome());
				txtEmail.setText(professor.getEmail());
				String dataFormatada = Data.formatarParaTela(professor.getData());
		        txtDtNasc.setText(dataFormatada);
				txtCpf.setText(String.valueOf(professor.getCpf()));
				txtIdProfessor.setText(String.valueOf(professor.getIdProfessor()));
				txtDepartamento.setText(professor.getDepartamento());
			}
		});

		frame.setSize(775, 350);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}