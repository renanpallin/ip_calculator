package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import control.IpCalculator;

public class TelaConvercoes {

	private JFrame frame;
	private JTextField tfDecimalIp;
	private JTextField tfBinaryIp;
	private JTextArea console;
	private JButton btnToDcimal;
	private JButton btnToBinary;
	
	private JPanel panel;
	
//	public static void main(String[] args) {
//		new TelaConvercoes();
//	}
	
	public TelaConvercoes(){
		frame = new JFrame();
		frame.setSize(800, 150);
		frame.setTitle("Converções");
		
		JLabel lblDecimalIp = new JLabel("IP Decimal : ");
		tfDecimalIp = new JTextField();
		
		JLabel lblBinaryIp = new JLabel("IP Binario : ");
		tfBinaryIp = new JTextField();
		
		btnToDcimal = new JButton("Converter");
		btnToBinary = new JButton("Converter");
		
		btnToDcimal.addActionListener(e -> {
			long[] ip = IpCalculator.divideOctets(getCleanText(tfDecimalIp));
			String binaryIp = IpCalculator.decimalToBinaryDotedIp(ip);
			tfBinaryIp.setText(binaryIp);
		});
		
		btnToBinary.addActionListener(e->{
			long[] ip = IpCalculator.binaryToDecimalIp(getCleanText(tfBinaryIp));
			
			StringBuilder sb = new StringBuilder();
			String ponto = "";
			for(long l: ip){
				sb.append(ponto);
				sb.append(l);
				ponto = ".";
			}
			
			tfDecimalIp.setText(sb.toString());
		});
		
		
		JPanel fieldsAndButtonsPanel = new JPanel();
		fieldsAndButtonsPanel.setLayout(new GridLayout(2, 3));
		
		fieldsAndButtonsPanel.add(lblDecimalIp);
		fieldsAndButtonsPanel.add(tfDecimalIp);
		fieldsAndButtonsPanel.add(btnToDcimal);
		
		fieldsAndButtonsPanel.add(lblBinaryIp);
		fieldsAndButtonsPanel.add(tfBinaryIp);
		fieldsAndButtonsPanel.add(btnToBinary);
		
//		console = new JTextArea();
//		console.setEditable(false);
//		
//		panel = new JPanel();
//		panel.setLayout(new BorderLayout());
//		panel.add(fieldsAndButtonsPanel, BorderLayout.NORTH);
//		panel.add(console, BorderLayout.CENTER);
		
		frame.setContentPane(fieldsAndButtonsPanel);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private String getCleanText(JTextField tf) {
		return tf.getText().trim().replace(" ", "").replaceAll("[^0-9.]", "");
	}
	
//	private void sendToConsole(String result){
//		console.setText(result);
//	}
}
