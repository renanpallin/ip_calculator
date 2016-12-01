package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import control.IpCalculator;

public class TelaClasseIp {

	private JFrame frame;
	private JTextField tfIp;
	private JTextField tfClass;
	private JButton btnfindClass;
	private JPanel panel;
	
	
//	public static void main(String[] args) {
//		new TelaClasseIp();
//	}
	
	public TelaClasseIp(){
		frame = new JFrame("Encontrar Classe do IP");
		frame.setSize(800, 150);
		frame.setTitle("Encontrar Classe do IP");
		
		tfIp = new JTextField();
		tfIp.setText("171.4.0.8");
		
		tfClass = new JTextField();
		tfClass.setEditable(false);
		
		btnfindClass = new JButton("Encontrar classe");
		btnfindClass.addActionListener( e -> {
			tfClass.setText("Seu IP é de classe: " + IpCalculator.findIpClass(IpCalculator.divideOctets(getCleanText(tfIp))));
		});
		
		JPanel fieldPanel = new JPanel();
		fieldPanel.setLayout(new GridLayout(1,2));
		fieldPanel.add(tfIp);
		fieldPanel.add(btnfindClass);
		
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(2,1));
		panel.add(fieldPanel);
		panel.add(tfClass);
		
		
		frame.setContentPane(panel);
		frame.setVisible(true);
	}
	
	private String getCleanText(JTextField tf) {
		return tf.getText().trim().replace(" ", "").replaceAll("[^0-9.]", "");
	}
}
