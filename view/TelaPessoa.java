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

public class TelaPessoa extends JDialog {

	private static JTextField txtId;
	private static JTextField txtNome;
	private static JTextField txtEmail;
	private static JFormattedTextField txtDtNasc;
	private static JFormattedTextField txtCpf;
	private static JTextField txtPesquisa;
	private static JTable tabela;
	private static PessoaDAO pessoaDAO = new PessoaDAOImp();
	private static List<Pessoa> p;
	private static DefaultTableModel tableModel;

	public TelaPessoa(JFrame parent) {
		super(parent, "Pessoa", true);
		JDialog frame = new JDialog();
		frame.setModal(true);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		String[] colunas = { "Id", "Nome", "Email", "Data de nascimento", "Cpf" };

		tableModel = new DefaultTableModel(colunas, 0);
		tabela = new JTable(tableModel);

		JScrollPane scroll = new JScrollPane(tabela);

		DefaultTableCellRenderer centralizar = new DefaultTableCellRenderer();
		centralizar.setHorizontalAlignment(SwingConstants.CENTER);

		tabela.getColumnModel().getColumn(0).setCellRenderer(centralizar);
		tabela.getColumnModel().getColumn(3).setCellRenderer(centralizar);
		tabela.getColumnModel().getColumn(4).setCellRenderer(centralizar);

		JPanel panelPesquisa = new JPanel();
		panelPesquisa.setBorder(new EmptyBorder(5, 5, 15, 5));
		frame.getContentPane().add(panelPesquisa, BorderLayout.NORTH);

		String[] elementos = { "Consultar todos", "Consultar nome", "Consultar CPF" };
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
							p = pessoaDAO.consultar();
							for (Pessoa P : p) {
								tableModel.addRow(
										new Object[] { P.getId(), P.getNome(), P.getEmail(), Data.formatarParaTela(P.getData()), Cpf.formatarCpf(P.getCpf()) });
							}

						} else if (opcaoSelecionada.equals(elementos[1])) {
							p = pessoaDAO.consultar(entradaUsuario);
							for (Pessoa P : p) {
								tableModel.addRow(
										new Object[] { P.getId(), P.getNome(), P.getEmail(), Data.formatarParaTela(P.getData()), Cpf.formatarCpf(P.getCpf()) });
							}

						} else if (opcaoSelecionada.equals(elementos[2])) {
							p = pessoaDAO.consultarCpf(entradaUsuario);
							for (Pessoa P : p) {
								tableModel.addRow(
										new Object[] { P.getId(), P.getNome(), P.getEmail(), Data.formatarParaTela(P.getData()), Cpf.formatarCpf(P.getCpf()) });
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

		p = pessoaDAO.consultar();
		for (Pessoa P : p) {
			tableModel.addRow(new Object[] { P.getId(), P.getNome(), P.getEmail(), Data.formatarParaTela(P.getData()), Cpf.formatarCpf(P.getCpf()) });
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

		JLabel labelEmail = new JLabel("Email:");
		painel.add(labelEmail);

		txtEmail = new JTextField();
		txtEmail.setColumns(25);
		painel.add(txtEmail);

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

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (txtNome.getText().isEmpty() || txtEmail.getText().isEmpty() || txtDtNasc.getText().isEmpty()
						|| txtCpf.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Dados incompletos!", "Erro!",
							JOptionPane.ERROR_MESSAGE);

				} else {
					Pessoa p = new Pessoa();
					p.setNome(txtNome.getText());
					p.setEmail(txtEmail.getText());
					
					String dataFormatada = Data.modificarFormatoData(txtDtNasc.getText());
					p.setData(Date.valueOf(dataFormatada));

					String cpfSemMascara = txtCpf.getText().replace(".", "");
					String cfpSemMascara2 = cpfSemMascara.replace("-", "");
					p.setCpf(cfpSemMascara2);
					
					List<Pessoa> pe = pessoaDAO.consultar();
					boolean existe = false;
					for (Pessoa pessoa : pe)
						if (pessoa.getCpf().equals(p.getCpf())) {
							existe = true;
							break;
						}
					if (existe == true) {
						JOptionPane.showMessageDialog(frame,
								"Erro ao inserir pessoa, já existe alguem com o mesmo cpf!", "Erro!",
								JOptionPane.ERROR_MESSAGE);
					} else {
						pessoaDAO.inserir(p);
						JOptionPane.showMessageDialog(frame, "Pessoa inserida com sucesso!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);
					}

					txtId.setText(null);
					txtNome.setText(null);
					txtEmail.setText(null);
					txtDtNasc.setText(null);
					txtCpf.setText(null);
				}
			}
		});
		painel.add(btnAdicionar);

		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtNome.getText().isEmpty() || txtEmail.getText().isEmpty() || txtDtNasc.getText().isEmpty()
						|| txtCpf.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Dados incompletos!", "Erro!",
							JOptionPane.ERROR_MESSAGE);

				} else {
					Pessoa p = new Pessoa();
					p.setId(Integer.valueOf(txtId.getText()));
					p.setNome(txtNome.getText());
					p.setEmail(txtEmail.getText());
					String dataFormatada = Data.modificarFormatoData(txtDtNasc.getText());
					p.setData(Date.valueOf(dataFormatada));
					
					String cpfSemMascara = txtCpf.getText().replace(".", "");
					String cfpSemMascara2 = cpfSemMascara.replace("-", "");
					p.setCpf(cfpSemMascara2);

					int n = JOptionPane.showConfirmDialog(frame, "Deseja realmente atualizar essa pessoa?",
							"Confirmação", JOptionPane.YES_NO_OPTION);
					if (n == JOptionPane.YES_OPTION) {

						pessoaDAO.alterar(p);

						JOptionPane.showMessageDialog(frame, "Pessoa alterada com sucesso!", "Sucesso!",
								JOptionPane.INFORMATION_MESSAGE);
						txtId.setText(null);
						txtNome.setText(null);
						txtEmail.setText(null);
						txtDtNasc.setText(null);
						txtCpf.setText(null);
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
				int n = JOptionPane.showConfirmDialog(parent, "Deseja realmente remover essa pessoa?", "Confirmação",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {

					Pessoa p = new Pessoa();
					p.setId(Integer.valueOf(txtId.getText()));

					pessoaDAO.remover(p.getId());
					JOptionPane.showMessageDialog(parent, "Pessoa removida com sucesso!", "Sucesso!",
							JOptionPane.INFORMATION_MESSAGE);

					txtId.setText(null);
					txtNome.setText(null);
					txtEmail.setText(null);
					txtDtNasc.setText(null);
					txtCpf.setText(null);

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
				Pessoa pessoa = p.get(row);
				txtId.setText(String.valueOf(pessoa.getId()));
				txtNome.setText(pessoa.getNome());
				txtEmail.setText(pessoa.getEmail());
				
				String dataFormatada = Data.formatarParaTela(pessoa.getData());
		        txtDtNasc.setText(dataFormatada);
		        
				txtCpf.setText(String.valueOf(pessoa.getCpf()));
			}
		});

		frame.setSize(775, 350);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.setVisible(true);
	}

}