package test;

import static org.junit.Assert.*;

import org.junit.Test;

import control.IpCalculator;
import control.STring;

public class IpCalculatorTest {

	IpCalculator ipCalculator = new IpCalculator();
	
	@Test
	public void testFindIpClassString() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testFindIpClassLongArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testCalculateBroadcastIp() {
		assertEquals("Broadcast: ", ipCalculator.calculateBroadcastIp("143.116.158.164", "255.255.255.240"), "143.116.158.175"); // questão 4 Exercícios IP
		assertEquals("Broadcast: ", ipCalculator.calculateBroadcastIp("172.31.136.0", "255.255.254.0"), "172.31.137.255");
		assertEquals("Broadcast: ", ipCalculator.calculateBroadcastIp( "", ""), "");
		
	/*
	 * "Pergunta: Quantas subredes e hosts por subrede pode-se obter usando a seguinte rede 172.27.0.0/26?
		Resposta: 1024 subredes e 62 hosts"
		"Pergunta: A qual subrede o host 172.31.57.221/23 pertece?
		Resposta: 172.31.56.0"
	 */
		
	}

	@Test
	public void testCalculateSubnetIp() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsInTheSameSubnet() {
		fail("Not yet implemented");
	}

	@Test
	public void testCalculateNumOfSubnetsLongArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testCalculateNumOfSubnetsString() {
		fail("Not yet implemented");
	}

	@Test
	public void testCalculateNumOfHosts() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueroXHosts() {
		fail("Not yet implemented");
	}

	@Test
	public void testQueroXSubnets() {
		fail("Not yet implemented");
	}

}
