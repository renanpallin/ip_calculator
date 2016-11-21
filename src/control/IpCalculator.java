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


/**
 * Todos os ips em formato String devem ser binários pontuados, de nome dotedBinaryIp
 * Todos os ips em formato long[] devem ser decimais, onde cada membro do array guarda um octeto
 * @author renan
 *
 */
public class IpCalculator{

	/**
	 * Case seja necessário guardar a máscara
	 */
	class IpRages{
		private List<IpRage> rages;
		private long[] mask;
		
		public IpRages(){
			this.rages = new ArrayList<>();
		}

		/**
		 * A ideia é que os rages sejam adicionados apenas por aqui
		 * @param start [description]
		 * @param end   [description]
		 */
		public void addRage(long[] start, long[] end){
			getRages().add(new IpRage(start,end));
		}

		public void addRage(IpRage ipRage){
			getRages().add(ipRage);
		}
		
		public List<IpRage> getRages() {
			return rages;
		}

		public void setRages(List<IpRage> rages) {
			this.rages = rages;
		}

		public long[] getMask() {
			return mask;
		}

		public void setMask(long[] mask) {
			this.mask = mask;
		}



		/**
		 * Represents an rage of Ip for an submask
		 */
		class IpRage{
			private long[] start;
			private long[] end;

			public IpRage(long[] start, long[] end){
				this.start = start;
				this.end = end;
			}

			public long[] getStart(){
				return start;
			}

			public void setStart(long[] start){
				this.start = start;
			}

			public long[] getEnd(){
				return end;
			}

			public void setEnd(long[] end){
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

			@Override
			public String toString(){
				return "Ip rage: " + start + " ~ " + end;
			}
		}
	}


	

/*  Funções de cálculo de rede */
	/**
	 * Find the IP class of given IP 
	 * @param ip in decimal system
	 * @return the IP class or a char with 0 if an error occurs
	 */
//	public char findIpClass(String dotedIp){
//		return findIpClass(divideOctets(dotedIp));
//	}

//	public char findIpClass(long[] ip){
//		long firstOctect = ip[0];
//		if (firstOctect <= 127)
//			return 'A';
//		else if (firstOctect <= 191)
//			return 'B'; 
//		else if (firstOctect <= 239)
//			return 'C';
//		else if (firstOctect <= 239)
//			return 'D';
//		else if (firstOctect <= 254)
//			return 'E';
//		else
//			return '0'; // or throw new IllegalArgumentExcepton();
//	}

	/**
	 * Calculate the broadcast IP address for given IP and Mask
	 * @param  ip   [description]
	 * @param  mask [description]
	 * @return      [description]
	 */
//	public String calculateBroadcastIp(String dotedIp, String dotedMask){
//		int numSubnets = calculateNumOfSubnets(dotedMask);
//		return findRage(dotedIp, calculateIpRages(numSubnets)).getEnd();
//	}

	/**
	 * Calculate the subnet IP adress for given IP and Mask
	 * @param  ip   [description]
	 * @param  mask [description]
	 * @return      [description]
	 */
//	public long calculateSubnetIp(String dotedIp, String dotedMask){
//		int numSubnets = (int) calculateNumOfSubnets(dotedMask);
//		return findRage(dotedIp, calculateIpRages(numSubnets)).getStart();
//	}
//
//	public boolean isInTheSameSubnet(String dotedIp1, String dotedMask1, String dotedIp2, String dotedMask2){
//		if (!dotedMask1.equals(dotedMask2)) return false;
//		return calculateSubnetIp(dotedIp1, dotedMask1) == calculateSubnetIp(dotedIp2, dotedMask2);
//	}

	/**
	 * Descobre de qual rage o IP pertence 
	 * @param  ip    long IP in decimal mode
	 * @param  rages List of IpRages
	 * @return       [description]
	 */
//	private IpRage findRage(long[] ip, IpRages ipRages){
//		for (IpRage rage: ipRages.getRages()) {
//			for (int i = 0; i < ip.length; i++){
//				if (ip[i] > rage.getStart()[i] && ip[i] < rage.getEnd()[i]){
//					return rage;
//				}			
//			}
//		}
//		return null;
//	}
	
	/**
	 * Calculate all the rages of Ip of the given mask
	 * @return [description]
	 */
	// TODO: Colocar o prefixo da subtede baseado para os ips de rage
	// TODO: Resolver problema "Como colocar a máscara nesse objeto?",
	// para isto, trocar parâmetro do método pela mascara completa
	//
	// 
	//TODO: MÉTODO TODO ERRADO! CONSERTAR ************************************************************************
	private IpRages calculateIpRages(String dotedMask, String dotedBinaryIp){
		IpRages ipRages = new IpRages();
		ipRages.setMask(binaryToDecimalIp(dotedMask));

		int numSubnets = calculateNumOfSubnets(dotedMask);
		int hostsForEachSubnet =  calculateNumOfHostsForEachSubnet(dotedMask);
		int numOfBitsSubnet = numBitsOfMaskForSubnet(dotedMask);
		
		String prefixBinaryIp = zerosAEsquerdaRecursivo(dotedBinaryIp.substring(numOfBitsSubnet));
		long[] prefixIp = binaryToDecimalIp(prefixBinaryIp);
		
		
		long[] host = {};
		
		long[] ip = binaryToDecimalIp(dotedBinaryIp);
		
		long[] currentStart = {};
		long[] currentEnd = {};
		
		while (numSubnets-- > 0){
		// for (int i = numSubnets; i >= 0; i--){
			ipRages.addRage(currentStart, currentEnd);
			currentStart += hosts;
			currentEnd += hosts;
		}
		return ipRages;
	}
	
	private IpRages calculateIpRages(int numSubnets){
		IpRages ipRages = new IpRages();
		
		int hosts = 256 / numSubnets;
		System.out.println(hosts);
		int currentStart = 1;
		int currentEnd = hosts - 2;
		while (numSubnets-- > 0){
		// for (int i = numSubnets; i >= 0; i--){
			ipRages.addRage(currentStart, currentEnd);
			currentStart += hosts;
			currentEnd += hosts;
		}
	return ipRages;
}
	
/* END - Funções de cálculo de rede */


/* Funções de cálculo e máscaras */

/* END - Funções de cálculo e máscaras */


//	 public static void main(String[] args) {
//	 	IpCalculator ipCalculator = new IpCalculator();
//	 	for (IpRage rage : ipCalculator.calculateIpRages(4)) {
//	 		System.out.println(rage.toString());
//	 	}
//	 }

	public static void main(String[] args) {
//		int x = 7;
//		int y = 1;
//		System.out.println("x: " + IpCalculator.zerosAEsquerdaRecursivo(Integer.toBinaryString(255),3));
//		System.out.println("y: " + IpCalculator.zerosAEsquerdaRecursivo(Integer.toBinaryString(255),3));
//		/* Este é o jeito que quero fazer para achar a melhor mascara, isto fica dentro de um while calculando o número de hosts desejados e comparando com o fornecido */
//		System.out.println(IpCalculator.zerosAEsquerdaRecursivo(Integer.toBinaryString(255 << 1),8).substring(1));
//		System.out.println(255 << 1);

		IpCalculator ip = new IpCalculator();

		long[] decimal = new long[] {255,255,192,0};
		String doted = ip.decimalToBinaryDotedIp(decimal);
//		System.out.println(doted);
//		System.out.println(ip.numBytesOfMaskForSubnet(doted));
//		System.out.println(ip.numBytesOfMaskForSubnet(decimal));
		
//		System.out.println(ip.numBytesOfMaskForSubnet(decimal));
//		System.out.println(ip.calculateNumOfHostsForEachSubnet(doted));
		
		System.out.println(Math.pow(2, 24));
		
	}
	

	



/* FUNÇÕES DE CONVERSÕES BÁSICAS - TODAS REFATORADAS! */



/* END FUNÇÕES DE CONVERSÕES BÁSICAS */



	/*
	 * isMaskValid test
	 */
//	public static void main(String[] args) {
//		IpCalculator ip = new IpCalculator();
//		System.out.println(ip.isMaskValid("111111.11111.111.111111111111100000"));
//	}

/* Funções de validações */

	/**
	 * Check if given mask is a valid one
	 * @param  binaryMask Mask in binary way
	 * @return boolean           [description]
	 **/
//	private boolean isMaskValid(String binaryMask){
//		/* Jeito complicado que comecei a fazer */
//		// char[] caracteres = binaryMask.replace(".","").toCharArray();
//		// boolean encontrouZero = false
//		// for (char c: caracteres) {
//		// 	if (c == '1'){
//		// 		if(encontrouZero){
//		// 			return false;
//		// 		}
//		// 	} else if(c == '0') {
//		// 		encontrouZero = true;
//		// 	} else {
//		// 		throw new IllegalArgumentException();
//		// 	}
//		// }
//		// return true;
//		 
//		/* Debug */
//		// System.out.println(binaryMask.split("0").length);
//		// for (String s : binaryMask.split("0")) {
//		// 	System.out.println(s);
//		// }
//		/* Jeito simples */
//		// System.out.println(binaryMask.split("0").length);
//		return binaryMask.split("0").length <= 1 && binaryMask.replace(".", "").length() == 8*4;
//	} 

/* END - Funções de validações */

	
	
	
/* Funções úteis TESTADAS */
	/* Se está descomentado abaixo desta linha, está correta */
	
	
	
	/**
	 * Overload para aceitar binaryMask
	 * @param mask
	 * @return
	 */
	public int calculateNumOfSubnets(long[] mask){
		return calculateNumOfSubnets(decimalToBinaryDotedIp(mask));
	}

	/**
	 * Calculate the number of possible subnets for given mask
	 * @param binaryMask in binary system
	 * @return
	 */
	public int calculateNumOfSubnets(String binaryMask){
		return (int) Math.pow(2, binaryMask
			.trim()
			.replace(".", "")
			.replace("1","")
			.length()) -2; // Tira broadcast e rede do calculo
	}
	
	
	
	
	/**
	 * Overload to make 8 as default desired length
	 * @param s
	 * @return
	 */
	private static String zerosAEsquerdaRecursivo(String s){
		return zerosAEsquerdaRecursivo(s, 8);
	}
	
	/**
	 * Fill with zeros in the left until desire length
	 * @param s
	 * @param tamanhoDesejado
	 * @return
	 */
	private static String zerosAEsquerdaRecursivo(String s, int tamanhoDesejado){
		int lenght = s.length();
		if (lenght >= tamanhoDesejado)
			return s;
		else
			return zerosAEsquerdaRecursivo("0" + s, tamanhoDesejado);
	}
	
	
	/**
	 * Transform decimal given IP to binary IP
	 * @param  ip [description]
	 * @return    [description]
	 */
	public  static String decimalToBinaryDotedIp(long[] ip){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ip.length; i++) {
			String binaryOctet = Long.toBinaryString(ip[i]);
			String binaryOctetEigthDigits = IpCalculator.zerosAEsquerdaRecursivo(binaryOctet);
			
			sb.append(binaryOctetEigthDigits);
			if (i != ip.length-1) {
				sb.append('.');
			}
		}
		return sb.toString();
	}
	
	/**
	 * Receives an doted binary IP string and return a
	 * long array with decimal IP
	 * 
	 * @param dotedBinaryIp
	 * @return
	 */
	public static long[] binaryToDecimalIp(String dotedBinaryIp){
		String[] binaryOctets = dotedBinaryIp.trim().split("\\.");
		long[] decimalOctets = new long[binaryOctets.length];
		for (int i = 0; i < binaryOctets.length; i++) {
			decimalOctets[i] = Long.parseLong(binaryOctets[i], 2);
		}
		return decimalOctets;
	}

	/**
	 * Será a primeira função chamada do IP vindo da view,
	 * transforma uma string de IP em formato decimal para
	 * um aray de long
	 * 
	 * Divide os octetos dando um split no '.'
	 * @param  ip Ip em string com pontos
	 * @return long[] array de long
	 */
	public static long[] divideOctets(String dotedDecimalIp){
		String[] ipOctetsString = dotedDecimalIp.trim().split("\\.");
		long[] ipOctets = new long[ipOctetsString.length];
		for (int i = 0; i < ipOctetsString.length; i++) {
			ipOctets[i] = Long.parseLong(ipOctetsString[i]);
		}
		return ipOctets;
	}
	
	
	/**
	 * Overload to the long array decimal IP
	 * Retrun the number of bytes used for subnet in the given mask
	 * @param  decimalMask Mask in decimal
	 * @return bytesUsed 
	 */
	public static int numBitsOfMaskForSubnet(long[] decimalMask){
		return numBitsOfMaskForSubnet(decimalToBinaryDotedIp(decimalMask));
	}

	/**
	 * Retrun the number of bytes used for subnet in the given mask
	 * @param  decimalMask Mask in decimal
	 * @return bytesUsed 
	 */
	public static int numBitsOfMaskForSubnet(String dotedMask){
		String unDotedMask = dotedMask.trim().replace(".", "");
		return unDotedMask.length() - unDotedMask.replace("1", "").length();
	}
	
	
	
	/**
	 * Calcula o número de hosts suportados para uma máscara
	 * @param dotedMask
	 * @return
	 */
	public static int calculateNumOfHostsForEachSubnet(String dotedMask){
		String unDotedMask = dotedMask.trim().replace(".", "");
		return (int) Math.pow(2, unDotedMask.length() - 
				numBitsOfMaskForSubnet(dotedMask)) -2; // Broadcast e Rede
	}

	
	
	
	
/* DumbHelper(Experimental) */
	/**
	 * Encontra a máscara ideal para o número de hosts desejado
	 * @param hosts [description]
	 */
//	public String queroXHosts(int hosts){
//		StringBuilder binaryMaskForSubnet = new StringBuilder();
//		while(binaryMaskForSubnet.length() < 32)
//			binaryMaskForSubnet.append('1');
//
//		StringBuilder binaryMaskForHosts = new StringBuilder();
//		while (calculateNumOfHosts(binaryMaskForSubnet.toString() + binaryMaskForHosts.toString()) < hosts){
//			binaryMaskForSubnet.deleteCharAt(binaryMaskForSubnet.length() - 1);
//			binaryMaskForHosts.append('0');
//		}
//		return binaryMaskForSubnet.append(binaryMaskForHosts.toString()).toString();
//	}
//
//	public String queroXSubnets(int subnets){
//		StringBuilder binaryMaskForSubnet = new StringBuilder();
//		while(binaryMaskForSubnet.length() < 32)
//			binaryMaskForSubnet.append('1');
//
//		StringBuilder binaryMaskForHosts = new StringBuilder();
//		while (calculateNumOfSubnets(binaryMaskForSubnet.toString() + binaryMaskForHosts.toString()) < subnets){
//			binaryMaskForSubnet.deleteCharAt(binaryMaskForSubnet.length() - 1);
//			binaryMaskForHosts.append('0');
//		}
//		return binaryMaskForSubnet.append(binaryMaskForHosts.toString()).toString();
//	}






	/* Teste de "reaproveitamento" de código. Analizar se é uma solução boa ou é uma merda */
		/* Função que calcularia, seja o que for */
//		public String testeDeQueroXAlgumaCoisa(int x, char oQueVoceQuer){
//			StringBuilder binaryMaskForSubnet = new StringBuilder();
//			while(binaryMaskForSubnet.length() < 32)
//				binaryMaskForSubnet.append('1');
//
//			StringBuilder binaryMaskForHosts = new StringBuilder();
//
//			/* Hosts */
//			if(oQueVoceQuer == 'H'){
//				while (calculateNumOfHosts(binaryMaskForSubnet.toString() + binaryMaskForHosts.toString()) < hosts){
//					binaryMaskForSubnet.deleteCharAt(binaryMaskForSubnet.length() - 1);
//					binaryMaskForHosts.append('0');
//				}
//			/* Subnet */
//			} else if (oQueVoceQuer == 'S'){
//				while (calculateNumOfSubnets(binaryMaskForSubnet.toString() + binaryMaskForHosts.toString()) < hosts){
//					binaryMaskForSubnet.deleteCharAt(binaryMaskForSubnet.length() - 1);
//					binaryMaskForHosts.append('0');
//				}
//			}
//			return binaryMaskForSubnet.append(binaryMaskForHosts.toString()).toString();
//		}
//
//		/* 
//		Assim, as funções de cálculo apenas chamariam a de cima com o parâmetro correto
//		 */
//		public String queroXHosts(int hosts){
//			return testeDeQueroXAlgumaCoisa(hosts, 'H');
//		}
//
//		public String queroXSubnets(int subnets){
//			return testeDeQueroXAlgumaCoisa(subnets, 'S');
//		}

	/* END - Teste de "reaproveitamento" de código. Analizar se é uma solução boa ou é uma merda */


/* END - DumbHelper(Experimental) */
}
