package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;

public class CalculadoraTest {
	private Calculadora calc;

	@Before
	public void setUp() {
		calc = new Calculadora();
	}

	@Test
	public void deveSomarDoisValores() {
		// Cen�rio
		int a = 5;
		int b = 3;
		int expected = a + b;

		// A��o
		int resultado = calc.soma(a, b);

		// Verifica��o
		assertEquals(expected, resultado);
	}

	@Test
	public void deveSubtrairDoisValores() {
		// Cen�rio
		int a = 5;
		int b = 3;
		int expected = a - b;

		// A��o
		int resultado = calc.subtrair(a, b);

		// Verifica��o
		assertEquals(expected, resultado);
	}

	@Test
	public void deveMultiplicarDoisValores() {
		// Cen�rio
		int a = 5;
		int b = 3;
		int expected = a * b;

		// A��o
		int resultado = calc.multiplica(a, b);

		// Verifica��o
		assertEquals(expected, resultado);
	}

	@Test
	public void deveDividirDoisValores() throws NaoPodeDividirPorZeroException {
		// Cen�rio
		int a = 5;
		int b = 3;
		int expected = a / b;

		// A��o
		int resultado = calc.dividi(a, b);

		// Verifica��o
		assertEquals(expected, resultado);
	}

	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void deveLancaExcessaoDividirPorZero() throws NaoPodeDividirPorZeroException {
		// Cen�rio
		int a = 5;
		int b = 0;

		// A��o
		calc.dividi(a, b);
	}
}
