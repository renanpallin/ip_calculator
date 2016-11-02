package control;

public class IP {
//	public static void main(String[] args) {
////		ipMascara();
//		ipAll(255);
//		
//	}

	private static void ipMascara() {
		int[] ip = {0b00000000, 
		            0b10000000,
		            0b11000000,
		            0b11100000,
		            0b11110000,
		            0b11111000,
		            0b11111100,
		            0b11111110,
		            0b11111111,
					};
		
		for(int i: ip){
			System.out.println(i + " -> " + Integer.toBinaryString(i));
		}
	}

	private static void ipAll(int limite){
		for (int i=0; i <= limite; i++){
			System.out.println(zerosAEsquerda(String.valueOf(i), 3) + " -> "
					+ zerosAEsquerda(Integer.toBinaryString(i), 8));
		}
	}
	
	private static String zerosAEsquerda(String s, int tamanhoDesejado){
		int length = s.length();
		if (length >= tamanhoDesejado)
			return s;
		
		StringBuffer sb = new StringBuffer();
		
		for (int x = 0; x < tamanhoDesejado - length; x++)
			sb.append("0");
		
		return sb.append(s).toString();
	}
	
	
	public static void main(String[] args) {
		System.out.println(zerosAEsquerdaRecursivo(IpCalculator.decimalToBinaryIp(255255255255l), 32));
	}
	
	private static String zerosAEsquerdaRecursivo(String s, int tamanhoDesejado){
		int lenght = s.length();
		if (lenght >= tamanhoDesejado)
			return s;
		else
			return zerosAEsquerdaRecursivo("0" + s, tamanhoDesejado);
	}
}
