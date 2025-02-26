package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import net.miginfocom.swing.MigLayout;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	public TelaPrincipal() {
		setTitle("BEM VINDO!\r\n");
		setBounds(100, 100, 610, 360);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);

		JButton btnPessoa = new JButton("Pessoa");
		btnPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaPessoa tp = new TelaPessoa(null);
			}
		});
		btnPessoa.setBounds(40, 72, 150, 40);
		contentPanel.add(btnPessoa);

		JButton btnAluno = new JButton("Aluno");
		btnAluno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaAluno ta = new TelaAluno(null);
			}
		});
		btnAluno.setBounds(230, 72, 150, 40);
		contentPanel.add(btnAluno);

		JButton btnProfessor_5 = new JButton("Professor");
		btnProfessor_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaProfessor tp = new TelaProfessor(null);
			}
		});
		btnProfessor_5.setBounds(413, 72, 150, 40);
		contentPanel.add(btnProfessor_5);

		JButton btnCurso = new JButton("Curso");
		btnCurso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCurso tc = new TelaCurso(null);
			}
		});
		btnCurso.setBounds(413, 138, 150, 40);
		contentPanel.add(btnCurso);

		JButton btnDisciplina = new JButton("Disciplina");
		btnDisciplina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaDisciplina td = new TelaDisciplina(null);
			}
		});
		btnDisciplina.setBounds(40, 138, 150, 40);
		contentPanel.add(btnDisciplina);

		JButton btnMatricula = new JButton("Matrícula");
		btnMatricula.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaMatricula tm = new TelaMatricula(null);
			}
		});
		btnMatricula.setBounds(230, 138, 150, 40);
		contentPanel.add(btnMatricula);

		JButton btnNota = new JButton("Nota");
		btnNota.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaNota tn = new TelaNota(null);
			}
		});
		btnNota.setBounds(40, 204, 150, 40);
		contentPanel.add(btnNota);

		JLabel lblNewLabel = new JLabel("O QUE VOCÊ DESEJA MODIFICAR?");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(195, 30, 220, 15);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
	}
}
