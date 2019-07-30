package br.ce.wcaquino.servicos;

import br.ce.wcaquino.exceptions.NaoPodeDividirPorZeroException;

public class Calculadora {

	public int soma(int a, int b) {
		return a + b;
	}

	public int subtrair(int a, int b) {
		return a - b;
	}

	public int multiplica(int a, int b) {
		return a * b;
	}

	public int dividi(int a, int b) throws NaoPodeDividirPorZeroException {
		try {
			return a / b;
		}
		catch(ArithmeticException e) {
			throw new NaoPodeDividirPorZeroException();
		}
	}

}
