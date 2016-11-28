package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import control.IpCalculator;

public class TelaPrincipal {
	
	private JFrame frame;
	private JPanel panel;
	private JTextField tfIp;
	private JTextField tfSubnet;
	private JTextField tfHost;
	private JButton btnCalculate;
	private JTextArea console;
	
	
	private IpCalculator ipCalculator;
	
	
	public static void main(String[] args) {
		new TelaPrincipal();
	}
	
	public TelaPrincipal(){
		frame = new JFrame();
		frame.setSize(800, 200);
		frame.setTitle("Ip Calculator");
		
		panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		
		tfIp = new JTextField();
		tfSubnet = new JTextField();
		tfHost = new JTextField();
		
		tfIp.setText("IP");
		tfSubnet.setText("subnet");
		tfHost.setText("hosts");
		
		console = new JTextArea();
		console.setEditable(false);
		
		ipCalculator = new IpCalculator();
		
		btnCalculate = new JButton("Calculate");
		btnCalculate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				long [] ipNet = IpCalculator.divideOctets(tfIp.getText());
				int numDesiredSubnets = Integer.parseInt(tfSubnet.getText());
				int numDesiredHosts = Integer.parseInt(tfHost.getText());
				
				String result = ipCalculator.calculateIpRages(ipNet, numDesiredSubnets, numDesiredHosts).toString();
				console.setText(result);
			}
		});
		
		
		
		JPanel ipPanel = new JPanel();
		ipPanel.setLayout(new GridLayout(1, 3));
		ipPanel.add(tfSubnet);
		ipPanel.add(tfHost);
		ipPanel.add(btnCalculate);
		
		panel.add(tfIp);
		panel.add(ipPanel);
		panel.add(console);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setContentPane(panel);
		frame.setVisible(true);
	}
}
