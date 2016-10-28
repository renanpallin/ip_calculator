package control;

import java.util.ArrayList;
import java.util.List;

/**
 * Chamadas HTTP para java
 * http://pt.wikihow.com/Implementar-Requisi%C3%A7%C3%B5es-HTTP-POST-no-Android
 */
/**
 * Funções:
 * Encontrar broadcast, endereço de rede de um ip e sua mascara
 * Descobrir se dois IPs são de uma mesma rede
 */
public class IpCalculator{

	/**
	 * Represents an rage of Ip for an submask
	 */
	class IpRage{
		private long start;
		private long end;

		public IpRage(long start, long end){
			this.start = start;
			this.end = end;
		}

		public long getStart(){
			return start;
		}

		public void setStart(long start){
			this.start = start;
		}

		public long getEnd(){
			return end;
		}

		public void setEnd(long end){
			this.end = end;
		}

		public boolean equals(Object o){
			if (!(o instanceof IpCalculator))
				return false;
			IpRage ipRage = (IpRage) o;
			return (
					this.getStart() == ipRage.getStart() 
					&&
					this.getEnd() == ipRage.getEnd()
				);
		}
	}
	
	/**
	 * Find the IP class of given IP 
	 * @param ip in decimal system
	 * @return the IP class or a char with 0 if an error occurs
	 */
	public char findIpClass(long ip){
		long firstOctect = Long.parseLong(String.valueOf(ip).substring(0,2));
		if (firstOctect <= 127)
			return 'A';
		else if (firstOctect <= 191)
			return 'B'; 
		else if (firstOctect <= 239)
			return 'C';
		else if (firstOctect <= 239)
			return 'D';
		else if (firstOctect <= 254)
			return 'E';
		else
			return '0';
	}

	/**
	 * Calculate the broadcast IP address for given IP and Mask
	 * @param  ip   [description]
	 * @param  mask [description]
	 * @return      [description]
	 */
	public long calculateBroadcastIp(long ip, long mask){
		int numSubnets = calculateNumOfSubnets(mask);
//		int hosts = 256 / numSubnets;
		return findRage(ip, calculateIpRages(numSubnets)).getEnd();
	}

	/**
	 * Calculate the subnet IP adress for given IP and Mask
	 * @param  ip   [description]
	 * @param  mask [description]
	 * @return      [description]
	 */
	public long calculateSubnetIp(long ip, long mask){
		int numSubnets = (int) calculateNumOfSubnets(mask);
		return findRage(ip, calculateIpRages(numSubnets)).getStart();
	}

	public boolean isInTheSameSubnet(long ip1, long mask1, long ip2, long mask2){
		if (mask1 != mask2) return false;
		return calculateSubnetIp(ip1, mask1) == calculateSubnetIp(ip1, mask1);
	}

	/**
	 * [findRage description]
	 * @param  ip    long IP in decimal mode
	 * @param  rages List of IpRages
	 * @return       [description]
	 */
	private IpRage findRage(long ip, List<IpRage> rages){
		for (IpRage rage: rages) {
			if (ip > rage.getStart() && ip < rage.getEnd()){
				return rage;
			}
		}
		return null;
	}

	/**
	 * Calculate all the rages of Ip of the given mask
	 * @return [description]
	 */
	private List<IpRage> calculateIpRages(int numSubnets){
		List<IpRage> rages = new ArrayList<>();
		int hosts = 256 / numSubnets;
		
		int currentStart = 0;
		int currentEnd = numSubnets;
		while (numSubnets-- > 0){
		// for (int i = numSubnets; i >= 0; i--){
			rages.add(new IpRage(currentStart, currentEnd));
			currentStart += hosts;
			currentEnd += hosts;
		}
		return rages;
	}

	/**
	 * Calculate the number of possible subnets in given mask
	 * @param  mask [description]
	 * @return      [description]
	 */
	public int calculateNumOfSubnets(long mask){
		return calculateNumOfSubnets(decimalToBinaryIp(mask));
	}

	/*
	 * calculateNumOfSubnets Test
	 */
//	public static void main(String[] args) {
//		IpCalculator ip = new IpCalculator();
//		System.out.println(ip.calculateNumOfSubnets("111111111111111111111100000000"));
//	}
	
	/**
	 * Calculate the number of possible subnets for given mask
	 * @param mask in binary system
	 * @return
	 */
	public int calculateNumOfSubnets(String mask){
		return (int) Math.pow(2, mask.replace("1","").length());
	}

	/**
	 * Transform decimal given IP to binary IP
	 * @param  ip [description]
	 * @return    [description]
	 */
	private String decimalToBinaryIp(long ip){
		String s = String.valueOf(ip);
		/* Gambiarra para não estourar o int */
		int firstPart = Integer.parseInt(s.substring(0, s.length() / 2));
		int secondPart = Integer.parseInt(s.substring(s.length() / 2 ));
		
		return String.valueOf(Integer.toBinaryString(firstPart)) + 
			   String.valueOf(Integer.toBinaryString(secondPart));
	}
	

	private long binaryToDecimalIp(String ip){
		// TODO: lê o nome da função e faz, viado
		return 0;
	}

	/**
	 * Retrun the number of bytes used in the given mask
	 * @param  decimalMask Mask in decimal
	 * @return bytesUsed 
	 */
	private int numBytesOfMask(long decimalMask){
		String binaryMask = decimalToBinaryIp(decimalMask);
		/* TODO: Utilizar um método que tenho para colocar 0 a esquerda */
		return binaryMask.length() - binaryMask.replace("1", "").length();
	}

	/*
	 * isMaskValid test
	 */
//	public static void main(String[] args) {
//		IpCalculator ip = new IpCalculator();
//		System.out.println(ip.isMaskValid("111111.11111.111.111111111111100000"));
//	}
	/**
	 * Check if given mask is a valid one
	 * @param  binaryMask Mask in binary way
	 * @return boolean           [description]
	 */
	private boolean isMaskValid(String binaryMask){
		/* Jeito complicado que comecei a fazer */
		// char[] caracteres = binaryMask.toCharArray();
		// for (char c: caracteres) {
		// 	if (c == '1'){
		// 	} else if(c == '0') {
		// 	} else {
		// 		throw new IllegalArgumentException();
		// 	}
		// }
		/* Jeito simples */
		System.out.println(binaryMask.split("0").length);
		return binaryMask.split("0").length <= 1 && binaryMask.replace(".", "").length() == 8*4;
	}
}