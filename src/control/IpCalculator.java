package control;

import java.util.ArrayList;
import java.util.Arrays;
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
	 * Classe que representa todos os rages de IP, do primeiro ao fim de cada subrede
	 * NÃO registra hosts, registra IP de rede 
	 * e de Broadcast.
	 * Para primiero e último host, usar métodos
	 * getFirstAndLastHosts
	 * 
	 */
	public class IpRages{
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
		
		/**
		 * Monta e devolve uma lista de IpRage onde start é o primeiro host
		 * e end é o último host da subrede
		 * @return
		 */
		public IpRages getFirstAndLastHosts(){
			IpRages ipRages = new IpRages();
			
			for(IpRage rage: rages)
				ipRages.addRage(rage.getFirstHost(), rage.getLastHost());
			
			return ipRages;
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

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("IpRages | Mask: ");
			
			if(mask != null){
				String ponto = "";
				for(long l: mask){
					sb.append(ponto);
					sb.append(l);
					ponto = ".";
				}				
			}
			
			sb.append('\n');
			
			sb.append("    Sub-rede    ~     Broadcast" + "\n");
			for(IpRage rage: rages)
				sb.append(rage + "\n");
			
			return sb.toString();
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

			public long[] getFirstHost(){
				long[] firstHost = start.clone();
				firstHost[firstHost.length-1]++;
				return firstHost;
			}
			
			public long[] getLastHost(){
				long[] lastHost = end.clone();
				lastHost[lastHost.length-1]--;
				return lastHost;
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
				StringBuilder sb = new StringBuilder();
				
				// Técnica para não colocar pontos no último octeto
				String ponto = "";
				for(long l: start){
					sb.append(ponto);
					sb.append(l);
					ponto = ".";
				}
				
				sb.append(" ~ ");
				
				// Maneira tradicional de impedir ponto on último octeto
				for(int i=0; i< end.length; i++){
					sb.append(end[i]);
					if(i != end.length -1)
						sb.append('.');
				}
				
				return sb.toString();
//				return "Ip rage: " + Arrays.toString(start) + " ~ " + Arrays.toString(end);
			}
		}
	}


	

/*  Funções de cálculo de rede */
	/**
	 * Overload to findIpClass(long[])
	 * 
	 * Find the IP class of given IP 
	 * @param ip in decimal system
	 * @return the IP class or a char with 0 if an error occurs
	 */
	public static char findIpClass(String dotedIp){
		return findIpClass(divideOctets(dotedIp));
	}

	/**
	 * Find the IP class of given IP 
	 * @param ip in decimal system
	 * @return the IP class or a char with 0 if an error occurs
	 */
	public static char findIpClass(long[] ip){
		long firstOctect = ip[0];
		// Tira IPs reservados, 10 e 127
		if(firstOctect == 127 ||
		   firstOctect == 10 ){
			return '0';
		}
	
		if (firstOctect < 127)
			return 'A';
		else if (firstOctect <= 191)
			return 'B'; 
		else if (firstOctect <= 223)
			return 'C';
		else if (firstOctect <= 239)
			return 'D';
		else if (firstOctect <= 255)
			return 'E';
		else
			return '0'; // or throw new IllegalArgumentExcepton();
	}
	
	/**
	 * Retorna a máscara padrão para a classe de IP recebida
	 * @param ipClass
	 * @return
	 */
	public static long[] getDefaultMask(char ipClass){
		// O findIpClass retorna o char 0 caso seja um IP reservado
		// ou tenha algum erro
		if(ipClass == '0')
			return null;
		
		int numOctectsFull = getNumberOfOctetcsFullInDefaltMask(ipClass);
		long[] defaultMask = new long[4];
	
		for(int i = 0; i < defaultMask.length; i++){
			if(i < numOctectsFull)
				defaultMask[i] = 255;
			else
				defaultMask[i] = 0;
		}
		
		return defaultMask;
	}
	
	/**
	 * Retorna o número de octetos com o valor 255 de uma máscara 
	 * padrão para uma classe recebida
	 * @return
	 */
	public static int getNumberOfOctetcsFullInDefaltMask(char ipClass){
		// O findIpClass retorna o char 0 caso seja um IP reservado
		// ou tenha algum erro
//		if(ipClass == '0')
//			return 0;
		
		if(ipClass == 'A'){
			return 1;
		} else if(ipClass == 'B'){
			return 2;
		} else if(ipClass == 'C'){
			return 3;
		}
		
		return 0;
	}

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
	public IpRages calculateIpRages(long[] ipNet, int numDesiredSubnets, int numDesiredHosts){
		IpRages ipRages = new IpRages();

//		char ipClass = IpCalculator.findIpClass(ipNet);
//		long[] defaultMask = IpCalculator.getDefaultMask(ipClass);
		
		// Não preciso mais da quantidade de bits para net, uma vez que somo depois pra pegar o prefixo total
		// int bitsForNet = IpCalculator.numBitsOfMaskForSubnet(defaultMask);
		/* Pega a diferença entre o usado para rede do totao, resultando  o usado para rede */
		int bitsForSubnet = getNumOfBitsForSubnet(numDesiredSubnets);
		int bitsForHosts = getNumOfBitsForHost(numDesiredHosts);
		System.out.println("bitsForSubnet : " + bitsForSubnet);
		System.out.println("bitsForHosts : " + bitsForHosts);
		

		int bitsNetPrefix = 32 - (bitsForSubnet + bitsForHosts);
		
		
		/* todos os bits que não são hosts */
		// int numBitsOfPrefixNet = bitsForNet + bitsForSubnet;
		
//		int numOfSubnets = (int) Math.pow(2, bitsForSubnet);
		
		// long[] prefixNet = new long[4];
		
		// Aqui podemos substituir o char[] e o while por uma String e utilizar substring (tira) OU 
		// por um StrinBuffer, limitando o tamanho dela para o bitsNetPrefix
//		char[] binaryNet = decimalToBinaryDotedIp(ipNet).trim().replaceAll(" ", "").replaceAll("\\.", "").toCharArray();
		
//		StringBuilder prefixBinaryNet = new StringBuilder();
		// int numFullOctets = getNumberOfOctetcsFullInDefaltMask(ipClass);

//		int i = 0;
//		while(prefixBinaryNet.length() <= bitsNetPrefix){
//			prefixBinaryNet.append(binaryNet[i]);
//			i++;
//		}

		/* Passando o prefixo para uma String final, para não ser alterada */
//		final String binaryPrefix = prefixBinaryNet.toString();
		final String binaryPrefix = decimalToBinaryDotedIp(ipNet).trim().replaceAll(" ", "")
				.replaceAll("\\.", "").substring(0, bitsNetPrefix);
		
		// Neste ponto temos o prefixo no tamanho correto e conehcemos os bits que usaremos para net e hosts.
		// Agora podemos calcular os hosts e colocar o início e o fim nos hosts.
		
		System.out.println("binaryPrefix : " + binaryPrefix);
//		System.out.println(binaryPrefix.length() + bitsForSubnet);
//		System.out.println(32 - bitsForHosts);
		
		int bitsTrueInMask = 32 - bitsForHosts;
//		int bitsTrueInMask = binaryPrefix.length() + bitsForSubnet;
		System.out.println("bitsTrueInMask: " + bitsTrueInMask);
		String binaryMask = getXchars('1', bitsTrueInMask) + getXchars('0', 32 - bitsTrueInMask );
		System.out.println("BINARYMASK: " + binaryMask);
		long[] mask = binaryToDecimalIp(putDotsInBinaryUndotedIp(binaryMask));
		ipRages.setMask(mask);
		
		/* Encontramos o número da maior subrede, para podermos iterar transformando em binário e colocando na currentSubnet */
		// TODO: Trocar este bloco por uma concatenação com getXchars();
		StringBuilder justToFindMaxSubnet = new StringBuilder();
		int maxSubnet;
		while(justToFindMaxSubnet.length() < bitsForSubnet){
			justToFindMaxSubnet.append('1');
		}
		
		maxSubnet = Integer.parseInt(justToFindMaxSubnet.toString(), 2) +1; //+1 para contar a subnet 0 também

		System.out.println("maxSubnet: " + maxSubnet);

		StringBuilder currentIpNet = new StringBuilder();

		int currentNumberSubnet = 0;
		do {
			// Unimos a Rede com a Subrede - Faltam os hosts
			currentIpNet.append(binaryPrefix); // Prefixo de rede
//			System.out.println("PREFIXO NET " + currentIpNet);
			
//			currentIpNet.append(" ");
			currentIpNet.append(zerosAEsquerdaRecursivo(Integer.toBinaryString(currentNumberSubnet), bitsForSubnet)); // Subrede atual com zeros a esquerda
//			System.out.println("CURRENT NET + SUBNET " + currentIpNet);
			// Nesta linha tempos o prefixo de NET + SUBNET, restando apenas os hosts (AQUI ESTÁ A MÁSCARA) 
			

			String start = currentIpNet.toString() + getXchars('0', bitsForHosts); // Primeiro host // Como já esa dando o primeiro dígito, -1
//			System.out.println("START " + start);
			
			String end = currentIpNet.toString() + getXchars('1', bitsForHosts); // Ultimo host 

//			System.out.println("END " + end);
			String dotedStart = putDotsInBinaryUndotedIp(start);
			String dotedEnd = putDotsInBinaryUndotedIp(end);

			// Adicionar na lista
			ipRages.addRage(binaryToDecimalIp(dotedStart), binaryToDecimalIp(dotedEnd));
			currentIpNet = new StringBuilder();
		} while(++currentNumberSubnet < maxSubnet);

		return ipRages;
		
	}
	
	
	
	
	private static int getNumOfBitsForSubnet(int numDesiredSubnets){
		int i = 0;
		while((((int) Math.pow(2, ++i))-2) < numDesiredSubnets);

		return i;
	}

	private static int getNumOfBitsForHost(int numDesiredHosts){
		int i = 1;
		while((((int) Math.pow(2, ++i))-2) < numDesiredHosts); // -2 (broadcast e subnet)

		return i;
	}

	/**
	 * Separa os octetos com pontos para uma string com 32 números binários sem pontos
	 * @param  undotedIp [description]
	 * @return           [description]
	 */
	private static String putDotsInBinaryUndotedIp(String undotedIp){
		undotedIp = undotedIp.trim().replace(" ", "");
		int start = 0;
		int end = 8;
		StringBuilder dotedIp = new StringBuilder();
		while(end <= undotedIp.length()){
			dotedIp.append(undotedIp.substring(start, end));
			if(end != undotedIp.length())
				dotedIp.append(".");
			start += 8;
			end += 8;
		}
		return dotedIp.toString();
	}

	
//	private IpRages calculateIpRages(int numSubnets){
//		IpRages ipRages = new IpRages();
//		
//		int hosts = 256 / numSubnets;
//		System.out.println(hosts);
//		int currentStart = 1;
//		int currentEnd = hosts - 2;
//		while (numSubnets-- > 0){
//		// for (int i = numSubnets; i >= 0; i--){
//			ipRages.addRage(currentStart, currentEnd);
//			currentStart += hosts;
//			currentEnd += hosts;
//		}
//		return ipRages;
//	}
	
//	public static void main(String[] args) {
//		IpCalculator ip = new IpCalculator();
//		
////		long[] ipNet = {191,110,38,0};
//		long[] ipNet = {172,16,0,0};
//		
//		int subnets = 4;
//		int hosts = 100;
//
//		
////		long[] mask = {255,255,254,0};
////		System.out.println(ip.calculateIpRages(ipNet, mask));
//		
////		long[] prefixNet = new long[4];
////		String binaryNet = decimalToBinaryDotedIp(ipNet).replace("\\.", "");
//		
////		System.out.println(Math.pow(2, 12));
//		
////		System.out.println(new IpCalculator().calculateIpRages(ipNet, 4, 258));
//		System.out.println(new IpCalculator().calculateIpRages(ipNet, subnets, hosts).getFirstAndLastHosts());
//		
//		// Que loko! Operadores unários, + ou - ante da variável, muda o sinal dela mesmo hehe
////		int i = 80;
////		System.out.println((-+i));
////		for (int i = 0; i < prefixNet.length; i++){
////			prefixNet[i] = ipNet[i] & mask[i];
////			System.out.println(prefixNet[i]);
////		}
//		
//	}
	
/* END - Funções de cálculo de rede */


/* Funções de cálculo e máscaras */

/* END - Funções de cálculo e máscaras */


//	 public static void main(String[] args) {
//	 	IpCalculator ipCalculator = new IpCalculator();
//	 	for (IpRage rage : ipCalculator.calculateIpRages(4)) {
//	 		System.out.println(rage.toString());
//	 	}
//	 }

//	public static void main(String[] args) {
////		int x = 7;
////		int y = 1;
////		System.out.println("x: " + IpCalculator.zerosAEsquerdaRecursivo(Integer.toBinaryString(255),3));
////		System.out.println("y: " + IpCalculator.zerosAEsquerdaRecursivo(Integer.toBinaryString(255),3));
////		/* Este é o jeito que quero fazer para achar a melhor mascara, isto fica dentro de um while calculando o número de hosts desejados e comparando com o fornecido */
////		System.out.println(IpCalculator.zerosAEsquerdaRecursivo(Integer.toBinaryString(255 << 1),8).substring(1));
////		System.out.println(255 << 1);
//
//		IpCalculator ip = new IpCalculator();
//
//		long[] decimal = new long[] {255,255,192,0};
//		String doted = ip.decimalToBinaryDotedIp(decimal);
////		System.out.println(doted);
////		System.out.println(ip.numBytesOfMaskForSubnet(doted));
////		System.out.println(ip.numBytesOfMaskForSubnet(decimal));
//		
////		System.out.println(ip.numBytesOfMaskForSubnet(decimal));
////		System.out.println(ip.calculateNumOfHostsForEachSubnet(doted));
//		
////		System.out.println(Math.pow(2, 24));
//		
////		/**
////		 * "BUG" do java encontrado
////		 * de eu somar com o caractere . ('.'),
////		 * ele soma o valor int ao long... fudendo tudo
////		 * UTILIZE STRING . 
////		 */
////		for(long l: ip.getDefaultMask('A')){
////			System.out.print(l + ".");
////		}
////		System.out.println();
////		for(long l: ip.getDefaultMask('B')){
////			System.out.print(l + ".");
////		}
////		System.out.println();
////		for(long l: ip.getDefaultMask('C')){
////			System.out.print(l + ".");
////		}
////		System.out.println();
////		for(long l: ip.getDefaultMask('D')){
////			System.out.print(l + ".");
////		}
////		System.out.println();
////		for(long l: ip.getDefaultMask('E')){
////			System.out.print(l + ".");
////		}
////		System.out.println();
////		
//	}
	

	



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
			.trim().replaceAll(" ", "")
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
	 * Função que devolve uma string com a quantidade x de chars c recebido
	 * Exeplo:
	 * getXChars(4, 'b') -> "bbbb"
	 * @param c
	 * @param x
	 * @return
	 */
	private String getXchars(char c, int x){
		StringBuilder zeros = new StringBuilder();
	
		while(zeros.length() < x){
			zeros.append(c);
		}
		return zeros.toString();
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
//		System.out.println(dotedBinaryIp.trim().replaceAll(" ", ""));
		String[] binaryOctets = dotedBinaryIp.trim().replaceAll(" ", "").split("\\.");
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
		String[] ipOctetsString = dotedDecimalIp.trim().replaceAll(" ", "").split("\\.");
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
		String unDotedMask = dotedMask.trim().replaceAll(" ", "").replace(".", "");
		return unDotedMask.length() - unDotedMask.replace("1", "").length();
	}
	
	
	
	/**
	 * Calcula o número de hosts suportados para uma máscara
	 * @param dotedMask
	 * @return
	 */
	public static int calculateNumOfHostsForEachSubnet(String dotedMask){
		String unDotedMask = dotedMask.trim().replaceAll(" ", "").replace(".", "");
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
