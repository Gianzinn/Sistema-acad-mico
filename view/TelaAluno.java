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
import InterfacesDAO.PessoaDAO;
import InterfacesIMP.PessoaDAOImp;
import InterfacesDAO.AlunoDAO;
import InterfacesIMP.AlunoDAOImp;
import Classes.Aluno;
import Classes.Pessoa;

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

public class TelaAluno extends JDialog {

	private static JTextField txtId;
	private static JTextField txtNome;
	private static JTextField txtEmail;
	private static JFormattedTextField txtDtNasc;
	private static JFormattedTextField txtCpf;
	private static JTextField txtPesquisa;
	private static JTextField txtIdAluno;
	private static JTextField txtRA;
	private static JTable tabela;
	private static AlunoDAO alunoDAO = new AlunoDAOImp();
	private static List<Aluno> a;
	private static DefaultTableModel tableModel;

	public TelaAluno(JFrame parent) {
		super(parent, "Aluno", true);
		JDialog frame = new JDialog();
		frame.setModal(true);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		String[] colunas = { "Id", "Nome", "Email", "Data de nascimento", "Cpf", "Id Aluno", "RA" };

		tableModel = new DefaultTableModel(colunas, 0);
		tabela = new JTable(tableModel);

		JScrollPane scroll = new JScrollPane(tabela);

		DefaultTableCellRenderer centralizar = new DefaultTableCellRenderer();
		centralizar.setHorizontalAlignment(SwingConstants.CENTER);

		tabela.getColumnModel().getColumn(0).setCellRenderer(centralizar);
		tabela.getColumnModel().getColumn(3).setCellRenderer(centralizar);
		tabela.getColumnModel().getColumn(4).setCellRenderer(centralizar);
		tabela.getColumnModel().getColumn(5).setCellRenderer(centralizar);
		tabela.getColumnModel().getColumn(6).setCellRenderer(centralizar);

		JPanel panelPesquisa = new JPanel();
		panelPesquisa.setBorder(new EmptyBorder(5, 5, 15, 5));
		frame.getContentPane().add(panelPesquisa, BorderLayout.NORTH);

		String[] elementos = { "Consultar todos", "Consultar nome", "Consultar RA" };
		JComboBox comboBox = new JComboBox(elementos);
		comboBox.setBounds(10, 11, 86, 22);
		panelPesquisa.add(comboBox);

		txtPesquisa = new JTextField();
		panelPesquisa.add(txtPesquisa);
		txtPesquisa.setColumns(20);

		comboBox.addActionListener(e -> txtPesquisa.setText(""));

		alunoDAO.consultar();

		txtPesquisa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String opcaoSelecionada = (String) comboBox.getSelectedItem();
					String entradaUsuario = txtPesquisa.getText();

					try {
						tableModel.setRowCount(0);
						if (opcaoSelecionada.equals(elementos[0])) {
							a = alunoDAO.consultar();
							for (Aluno A : a) {
								tableModel.addRow(new Object[] { A.getId(), A.getNome(), A.getEmail(), Data.formatarParaTela(A.getData()),
										Cpf.formatarCpf(A.getCpf()), A.getIdAluno(), A.getRa() });
							}

						} else if (opcaoSelecionada.equals(elementos[1])) {
							a = alunoDAO.consultar(entradaUsuario);
							for (Aluno A : a) {
								tableModel.addRow(new Object[] { A.getId(), A.getNome(), A.getEmail(), Data.formatarParaTela(A.getData()),
										Cpf.formatarCpf(A.getCpf()), A.getIdAluno(), A.getRa() });
							}

						} else if (opcaoSelecionada.equals(elementos[2])) {
							a = alunoDAO.consultar(Integer.valueOf(entradaUsuario));
							for (Aluno A : a) {
								tableModel.addRow(new Object[] { A.getId(), A.getNome(), A.getEmail(), Data.formatarParaTela(A.getData()),
										Cpf.formatarCpf(A.getCpf()), A.getIdAluno(), A.getRa() });
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

		a = alunoDAO.consultar();
		for (Aluno A : a) {
			tableModel.addRow(new Object[] { A.getId(), A.getNome(), A.getEmail(), Data.formatarParaTela(A.getData()), Cpf.formatarCpf(A.getCpf()),
					A.getIdAluno(), A.getRa() });
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

		MaskFormatter mascaraData = null;
		try {
		    mascaraData = new MaskFormatter("##-##-####");
		    mascaraData.setPlaceholderCharacter('_');
		} catch (ParseException e) {
		    e.printStackTrace();
		}
		
		JLabel labelDtNasc = new JLabel("Dt Nascimento:");
		painel.add(labelDtNasc);

		txtDtNasc = new JFormattedTextField(mascaraData);
		painel.add(txtDtNasc);
		txtDtNasc.setColumns(15);
		txtDtNasc.setEditable(false);

		MaskFormatter mascaraCPF = null;

        try {
            mascaraCPF = new MaskFormatter("###.###.###-##");
            mascaraCPF.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
		
		JLabel labelCpf = new JLabel("Cpf:");
		painel.add(labelCpf);

		txtCpf = new JFormattedTextField(mascaraCPF);
		painel.add(txtCpf);
		txtCpf.setColumns(15);
		txtCpf.setEditable(false);

		JLabel labelIdAluno = new JLabel("Id Aluno:");
		painel.add(labelIdAluno);

		txtIdAluno = new JTextField();
		painel.add(txtIdAluno);
		txtIdAluno.setColumns(5);
		txtIdAluno.setEditable(false);

		JLabel labelRa = new JLabel("RA:");
		painel.add(labelRa);

		txtRA = new JTextField();
		painel.add(txtRA);
		txtRA.setColumns(6);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtNome.getText().isEmpty() || txtEmail.getText().isEmpty() || txtDtNasc.getText().isEmpty()
						|| txtCpf.getText().isEmpty() || txtIdAluno.getText().isEmpty() || txtRA.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Dados incompletos!", "Erro!", JOptionPane.ERROR_MESSAGE);

				} else {
					Aluno a = new Aluno();
					a.setId(Integer.valueOf(txtId.getText()));
					a.setNome(txtNome.getText());
					a.setEmail(txtEmail.getText());
					String dataFormatada = Data.modificarFormatoData(txtDtNasc.getText());
					a.setData(Date.valueOf(dataFormatada));
					a.setCpf(txtCpf.getText());
					a.setIdAluno(Integer.valueOf(txtIdAluno.getText()));
					a.setRa(Integer.valueOf(txtRA.getText()));

					List<Aluno> al = alunoDAO.consultar();
					int existe = 0;
					for (Aluno alunos : al)
						if (alunos.getRa() == (a.getRa())) {
							existe = 1;
							break;
						} else if (a.getRa() == 0) {
							existe = 2;
							break;
						}

					if (existe == 1) {
						JOptionPane.showMessageDialog(frame, "Erro ao inserir aluno, já existe alguem com o mesmo ra!",
								"Erro!", JOptionPane.ERROR_MESSAGE);
					} else if (existe == 2) {
						JOptionPane.showMessageDialog(frame, "Erro ao inserir aluno, o ra precisa ser diferente de 0!",
								"Erro!", JOptionPane.ERROR_MESSAGE);
					} else {
						alunoDAO.inserir(a);
						JOptionPane.showMessageDialog(frame, "Aluno inserido com sucesso!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);
					}
					txtId.setText(null);
					txtNome.setText(null);
					txtEmail.setText(null);
					txtDtNasc.setText(null);
					txtCpf.setText(null);
					txtIdAluno.setText(null);
					txtRA.setText(null);

				}
			}
		});
		painel.add(btnAdicionar);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtNome.getText().isEmpty() || txtEmail.getText().isEmpty() || txtDtNasc.getText().isEmpty()
						|| txtCpf.getText().isEmpty() || txtIdAluno.getText().isEmpty() || txtRA.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Dados incompletos!", "Erro!", JOptionPane.ERROR_MESSAGE);

				} else {
					Aluno a = new Aluno();
					a.setId(Integer.valueOf(txtId.getText()));
					a.setNome(txtNome.getText());
					a.setEmail(txtEmail.getText());
					String dataFormatada = Data.modificarFormatoData(txtDtNasc.getText());
					a.setData(Date.valueOf(dataFormatada));
					a.setCpf(txtCpf.getText());
					a.setIdAluno(Integer.valueOf(txtIdAluno.getText()));
					a.setRa(Integer.valueOf(txtRA.getText()));

					int n = JOptionPane.showConfirmDialog(frame, "Deseja realmente atualizar esse aluno?",
							"Confirmação", JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {

						alunoDAO.alterar(a);

						JOptionPane.showMessageDialog(frame, "Aluno alterado com sucesso!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);
						txtId.setText(null);
						txtNome.setText(null);
						txtEmail.setText(null);
						txtDtNasc.setText(null);
						txtCpf.setText(null);
						txtIdAluno.setText(null);
						txtRA.setText(null);
					}
				}
			}
		});
		painel.add(btnAtualizar);

		JButton btnRemover = new JButton("Remover");
		painel.add(btnRemover);
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtRA.getText().isEmpty()) {
					int n = JOptionPane.showConfirmDialog(parent, "Deseja realmente remover esse aluno?", "Confirmação",
							JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {

						alunoDAO.remover(Integer.valueOf(txtRA.getText()));
						JOptionPane.showMessageDialog(parent, "Aluno removido com sucesso!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);

						txtId.setText(null);
						txtNome.setText(null);
						txtEmail.setText(null);
						txtDtNasc.setText(null);
						txtCpf.setText(null);
						txtIdAluno.setText(null);
						txtRA.setText(null);

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
				Aluno aluno = a.get(row);
				txtId.setText(String.valueOf(aluno.getId()));
				txtNome.setText(aluno.getNome());
				txtEmail.setText(aluno.getEmail());
				String dataFormatada = Data.formatarParaTela(aluno.getData());
		        txtDtNasc.setText(dataFormatada);
				txtCpf.setText(String.valueOf(aluno.getCpf()));
				txtIdAluno.setText(String.valueOf(aluno.getIdAluno()));
				txtRA.setText(String.valueOf(aluno.getRa()));
			}
		});

		frame.setSize(775, 350);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}
}