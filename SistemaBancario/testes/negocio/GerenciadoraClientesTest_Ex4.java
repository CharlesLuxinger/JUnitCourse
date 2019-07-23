package negocio;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Classe de teste criada para garantir o funcionamento das principais opera��es
 * sobre clientes, realizadas pela classe {@link GerenciadoraClientes}.
 * 
 * @author Gustavo Farias
 * @date 21/01/2035
 */
public class GerenciadoraClientesTest_Ex4 {

	private GerenciadoraClientes gerClientes;

	/**
	 * Teste b�sico da pesquisa de um cliente a partir do seu ID.
	 * 
	 * @author Gustavo Farias
	 * @date 21/01/2035
	 */
	@Test
	public void testPesquisaCliente() {

		/* ========== Montagem do cen�rio ========== */

		// criando alguns clientes
		Cliente cliente01 = new Cliente(1, "Gustavo Farias", 31, "gugafarias@gmail.com", 1, true);
		Cliente cliente02 = new Cliente(2, "Felipe Augusto", 34, "felipeaugusto@gmail.com", 1, true);

		// inserindo os clientes criados na lista de clientes do banco
		List<Cliente> clientesDoBanco = new ArrayList<>();
		clientesDoBanco.add(cliente01);
		clientesDoBanco.add(cliente02);

		// M�todo ao qual desejo testar
		gerClientes = new GerenciadoraClientes(clientesDoBanco);

		/* ========== Execu��o ========== */
		// Retorno do m�todo em teste
		Cliente cliente = gerClientes.pesquisaCliente(1);

		/* ========== Verifica��es ========== */
		// Verifica se o ID e o Email s�o correspondetes
		assertThat(cliente.getId(), is(1));
		assertThat(cliente.getEmail(), is("gugafarias@gmail.com"));

	}

	/**
	 * Teste b�sico da remo��o de um cliente a partir do seu ID.
	 * 
	 * @author Gustavo Farias
	 * @date 21/01/2035
	 */
	@Test
	public void testRemoveCliente() {

		/* ========== Montagem do cen�rio ========== */

		// criando alguns clientes
		Cliente cliente01 = new Cliente(1, "Gustavo Farias", 31, "gugafarias@gmail.com", 1, true);
		Cliente cliente02 = new Cliente(2, "Felipe Augusto", 34, "felipeaugusto@gmail.com", 1, true);

		// inserindo os clientes criados na lista de clientes do banco
		List<Cliente> clientesDoBanco = new ArrayList<>();
		clientesDoBanco.add(cliente01);
		clientesDoBanco.add(cliente02);

		gerClientes = new GerenciadoraClientes(clientesDoBanco);

		/* ========== Execu��o ========== */
		// Retorno e M�todo ao qual desejo testar
		boolean clienteRemovido = gerClientes.removeCliente(2);

		/* ========== Verifica��es ========== */
		// Verifica o valor da vari�vel
		assertThat(clienteRemovido, is(true));
		// Verifica o qtd de clientes
		assertThat(gerClientes.getClientesDoBanco().size(), is(1));
		// Verifica o retorno � nulo
		assertNull(gerClientes.pesquisaCliente(2));

	}

}