package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {
	@Test
	public void test() {
		// Espera retorno de uma condi��o
		assertTrue(true);
		assertFalse(false);

		// Os valores
		assertEquals("abc", "abc");
		assertNotEquals("abc", "cba");

		// Compara��o Uncase sensitive
		assertTrue("abc".equalsIgnoreCase("ABC"));

		// A compara��o entre double est� depreciada por conta precis�o, sendo assim
		// necessita de um delta(diferen�a) de compara��o
		assertEquals(0.51, 0.51, 0.01);

		// Compara��o de objetos, utiliza-se o m�todo equals da classe, caso n�o tenha
		// implementado procura na super classe.
		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 2");
		Usuario u3 = new Usuario("Usuario 1");
		Usuario u4 = u1;
		Usuario u5 = null;

		assertNotEquals(u1, u2);
		assertEquals(u1, u3);

		// Testar se objetos s�o da mesma inst�ncia
		assertSame(u1, u4);
		assertNotSame(u1, u2);

		// Testar se o objeto � nullo
		assertTrue(u5 == null);
		assertNull(u5);
		assertNotNull(u1);
		
		

	}
}
