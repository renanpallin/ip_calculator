package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TelaPrincipal {

	private JFrame frame;
	private JPanel panel;
	
	private JButton btnDivideSubredes;
	private JButton btnConvercoes;
	private JButton btnIpClass;
	
	public static void main(String[] args) {
		new TelaPrincipal();
	}
	
	public TelaPrincipal(){
		frame = new JFrame();
		frame.setSize(800, 150);
		frame.setTitle("Calculadorinha IP");
		
		btnDivideSubredes = new JButton("Dividir Subredes");
		btnConvercoes = new JButton("Conversões");
		btnIpClass = new JButton("Classes IP");
		
		btnDivideSubredes.addActionListener( e -> {
			new TelaRages();
		});
		
		btnConvercoes.addActionListener( e ->  {
			new TelaConvercoes();
		});
		
		btnIpClass.addActionListener( e -> {
			new TelaClasseIp();
		});
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout());
		buttonsPanel.add(btnDivideSubredes);
		buttonsPanel.add(btnConvercoes);
		buttonsPanel.add(btnIpClass);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setText("Calculadora IP!!! \nOlá. Escolha sua opção abaixo e uma tela se abrirá. \n\n\nAutor: Renan Pallin de Azevedo\nRA:141033-4");
		
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(textArea, BorderLayout.CENTER);
		panel.add(buttonsPanel, BorderLayout.SOUTH);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setContentPane(panel);
		frame.setVisible(true);
	}
}
