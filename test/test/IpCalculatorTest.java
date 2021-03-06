package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import control.IpCalculator;

public class IpCalculatorTest {

	IpCalculator ip = new IpCalculator();
	
	@Test
	public void testDecimalToBinaryIp() {
		assertEquals(ip.decimalToBinaryDotedIp(new long[] {192, 168, 0, 1}), "11000000.10101000.00000000.00000001");
		assertEquals(ip.decimalToBinaryDotedIp(new long[] {175, 255, 4, 48}), "10101111.11111111.00000100.00110000");
		assertEquals(ip.decimalToBinaryDotedIp(new long[] {92, 0, 49, 88}), "01011100.00000000.00110001.01011000");
	}
	
	@Test
	public void testBinaryToDecimalIp(){
		assertTrue(Arrays.equals(ip.binaryToDecimalIp("11000000.10101000.00000000.00000001"), new long[] {192, 168, 0, 1}));
		assertTrue(Arrays.equals(ip.binaryToDecimalIp("10101111.11111111.00000100.00110000"), new long[] {175, 255, 4, 48}));
		assertTrue(Arrays.equals(ip.binaryToDecimalIp("01011100.00000000.00110001.01011000"), new long[] {92, 0, 49, 88}));
	}
	
	@Test
	public void testDivideOctects(){
		assertTrue(Arrays.equals(ip.divideOctets("192.168.4.18"), new long[] {192, 168, 4, 18}));
		assertTrue(Arrays.equals(ip.divideOctets("174.245.9.208"),new long[] {174, 245, 9, 208}));
	}

	
//	@Test
//	public void testFindIpClassString() {
//		fail("Not yet implemented");
//	}
	
	@Test
	public void testFindIpClassLongArray() {
		/* Testes de fronteira */
		assertEquals(ip.findIpClass(new long[] {1,5,13,112}), 'A');
		assertEquals(ip.findIpClass(new long[] {40,5,13,112}), 'A');
		assertEquals(ip.findIpClass(new long[] {126,5,13,112}), 'A');

		assertEquals(ip.findIpClass(new long[] {128,4,6,12}), 'B');
		assertEquals(ip.findIpClass(new long[] {191,4,6,12}), 'B');

		assertEquals(ip.findIpClass(new long[] {192,4,6,12}), 'C');
		assertEquals(ip.findIpClass(new long[] {223,4,6,12}), 'C');

		assertEquals(ip.findIpClass(new long[] {224,5,6,12}), 'D');
		assertEquals(ip.findIpClass(new long[] {239,4,6,12}), 'D');

		assertEquals(ip.findIpClass(new long[] {240,4,6,12}), 'E');
		assertEquals(ip.findIpClass(new long[] {255,4,6,12}), 'E');
		
		/* IPs reservados */
		assertEquals(ip.findIpClass(new long[] {10,4,6,12}), '0');
		assertEquals(ip.findIpClass(new long[] {127,4,6,12}), '0');
	}

	
	@Test
	public void testGetDefaultMask() {
		assertTrue(Arrays.equals(IpCalculator.getDefaultMask('A'), new long[] {255, 0, 0, 0}));
		assertTrue(Arrays.equals(IpCalculator.getDefaultMask('B'), new long[] {255, 255, 0, 0}));
		assertTrue(Arrays.equals(IpCalculator.getDefaultMask('C'), new long[] {255, 255, 255, 0}));
		assertTrue(Arrays.equals(IpCalculator.getDefaultMask('D'), new long[] {0, 0, 0, 0}));
		assertTrue(Arrays.equals(IpCalculator.getDefaultMask('E'), new long[] {0, 0, 0, 0}));
	}
	

//	@Test
//	public void testCalculateBroadcastIp() {
////		assertEquals("Broadcast: ", ipCalculator.calculateBroadcastIp("143.116.158.164", "255.255.255.240"), "143.116.158.175"); // questão 4 Exercícios IP
//////		assertEquals("Broadcast: ", ipCalculator.calculateBroadcastIp(new long[] {143,116,158,164}, "255.255.255.240"), "143.116.158.175"); // questão 4 Exercícios IP
////		assertEquals("Broadcast: ", ipCalculator.calculateBroadcastIp("172.31.136.0", "255.255.254.0"), "172.31.137.255");
////		assertEquals("Broadcast: ", ipCalculator.calculateBroadcastIp( "", ""), "");
//		
//	/*
//	 * "Pergunta: Quantas subredes e hosts por subrede pode-se obter usando a seguinte rede 172.27.0.0/26?
//		Resposta: 1024 subredes e 62 hosts"
//		"Pergunta: A qual subrede o host 172.31.57.221/23 pertece?
//		Resposta: 172.31.56.0"
//	 */
//		
//	}
//
//	@Test
//	public void testCalculateSubnetIp() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testIsInTheSameSubnet() {
//		fail("Not yet implemented");
//	}
//
	@Test
	public void testCalculateNumOfSubnetsLongArray() {
		assertEquals(ip.calculateNumOfSubnets(new long[] {255,255,255,0}), 254);
		assertEquals(ip.calculateNumOfSubnets(new long[] {255,255,192,0}), 16382);
		assertEquals(ip.calculateNumOfSubnets(new long[] {255,255,248,0}), 2046);
		assertEquals(ip.calculateNumOfSubnets(new long[] {255,255,0,0}), 65534);
		assertEquals(ip.calculateNumOfSubnets(new long[] {255,128,0,0}), 8388606);
		assertEquals(ip.calculateNumOfSubnets(new long[] {255,0,0,0}), 16777214);
	}

	@Test
	public void testCalculateNumOfSubnetsString() {
		assertEquals(ip.calculateNumOfSubnets("11111111.11111111.11111111.00000000"), 254);
		assertEquals(ip.calculateNumOfSubnets("11111111.11111111.11000000.00000000"), 16382);
		assertEquals(ip.calculateNumOfSubnets("11111111.11111111.11111000.00000000"), 2046);
		assertEquals(ip.calculateNumOfSubnets("11111111.11111111.00000000.00000000"), 65534);
		assertEquals(ip.calculateNumOfSubnets("11111111.10000000.00000000.00000000"), 8388606);
		assertEquals(ip.calculateNumOfSubnets("11111111.00000000.00000000.00000000"), 16777214);
	}

	@Test
	public void testNumBytesOfMaskForSubnet(){
		assertEquals(ip.numBitsOfMaskForSubnet("11111111.11111111.11111111.10000000"), 25);
		assertEquals(ip.numBitsOfMaskForSubnet("11111111.11110000.00000000.00000000"), 12);
		assertEquals(ip.numBitsOfMaskForSubnet("10000000.00000000.00000000.00000000"), 1);
		assertEquals(ip.numBitsOfMaskForSubnet("11111111.11111111.11111111.11111100"), 30);
		assertEquals(ip.numBitsOfMaskForSubnet("11111111.11111111.11111111.11111110"), 31);
	}
	
	@Test
	public void testCalculateNumOfHosts() {
		assertEquals(ip.calculateNumOfHostsForEachSubnet("11111111.11111111.11111111.11111110"), 0);
		assertEquals(ip.calculateNumOfHostsForEachSubnet("11111111.11111111.11111111.11111100"), 2);
		assertEquals(ip.calculateNumOfHostsForEachSubnet("11111111.11111111.11000000.00000000"), 16382);
		assertEquals(ip.calculateNumOfHostsForEachSubnet("11111111.11111111.00000000.00000000"), 65534);
		assertEquals(ip.calculateNumOfHostsForEachSubnet("11111111.00000000.00000000.00000000"), 16777214);
	}

//	@Test
//	public void testQueroXHosts() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testQueroXSubnets() {
//		fail("Not yet implemented");
//	}
	
	

}
