package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import control.IpCalculator;
import control.IpCalculator.IpRages;

public class TelaRages {
	
	private JFrame frame;
	private JPanel panel;
	private JTextField tfIp;
	private JTextField tfSubnet;
	private JTextField tfHost;
	private JButton btnCalculate;
	private JTextArea console;
	
	private JScrollPane scrollPane;
	
	
	private IpCalculator ipCalculator;
	
	
	public static void main(String[] args) {
		new TelaRages();
	}
	
	public TelaRages(){
		frame = new JFrame();
		frame.setSize(800, 600);
		frame.setTitle("Dividir Subredes");
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
//		JLabel lblIp = new JLabel("IP");
//		JLabel lblSubnet = new JLabel("Subnet");
//		JLabel lblHosts = new JLabel("Hosts");
		
		tfIp = new JTextField();
		tfSubnet = new JTextField();
		tfHost = new JTextField();
		
		tfIp.setText("191.110.38.0");
		tfSubnet.setText("4 subnets");
		tfHost.setText("258 hosts");
		
		console = new JTextArea();
		console.setEditable(false);
		
		ipCalculator = new IpCalculator();
		
		btnCalculate = new JButton("Calcular");
		btnCalculate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				long [] ipNet = IpCalculator.divideOctets(tfIp.getText());
				int numDesiredSubnets = Integer.parseInt(tfSubnet.getText().trim().replace(" ", "").replaceAll("[^0-9.]", ""));
				int numDesiredHosts = Integer.parseInt(tfHost.getText().trim().replace(" ", "").replaceAll("[^0-9.]", ""));
				
				StringBuilder sb = new StringBuilder();
				sb.append("Lista de Redes");
				sb.append("Classe: " + IpCalculator.findIpClass(ipNet) + "\n");
				
				IpRages ipRages = ipCalculator.calculateIpRages(ipNet, numDesiredSubnets, numDesiredHosts);
				sb.append("\n");
				sb.append(ipRages.toString());
				sb.append("\n");
				sb.append("\n");
				sb.append("Lista de Hosts");
				sb.append("\n");
				sb.append("    Primeiro    ~     Último");
				sb.append(ipRages.getFirstAndLastHosts().toString().replaceAll("IpRages | Mask:", "")
						.replaceAll("Sub-rede    ~     Broadcast", "")
						.replaceAll("\\|", ""));
				console.setText(sb.toString());
			}
		});
		
		
		
		JPanel fieldsPanel = new JPanel();
		fieldsPanel.setLayout(new GridLayout(1, 3));
		fieldsPanel.add(tfSubnet);
		fieldsPanel.add(tfHost);
		fieldsPanel.add(btnCalculate);
		
		JPanel ipAndFieldsPanel = new JPanel();
		ipAndFieldsPanel.setLayout(new GridLayout(2, 1));
		ipAndFieldsPanel.add(tfIp);
		ipAndFieldsPanel.add(fieldsPanel);
		
		scrollPane = new JScrollPane(console);
		
//		panel.add(tfIp);
		panel.add(ipAndFieldsPanel, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);
		
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setLocationRelativeTo(null);
		frame.setContentPane(panel);
		frame.setVisible(true);
	}
}
