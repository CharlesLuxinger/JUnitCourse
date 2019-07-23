package negocio;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GerenciadoraClientesTest_Ex3 {

	private GerenciadoraClientes gerClientes;

	@Test
	public void testPesquisaCliente() {

		/* ========== Montagem do cenário ========== */

		// criando alguns clientes
		Cliente cliente01 = new Cliente(1, "Gustavo Farias", 31, "gugafarias@gmail.com", 1, true);
		Cliente cliente02 = new Cliente(2, "Felipe Augusto", 34, "felipeaugusto@gmail.com", 1, true);

		// inserindo os clientes criados na lista de clientes do banco
		List<Cliente> clientesDoBanco = new ArrayList<>();
		clientesDoBanco.add(cliente01);
		clientesDoBanco.add(cliente02);

		// Método ao qual desejo testar
		gerClientes = new GerenciadoraClientes(clientesDoBanco);

		/* ========== Execução ========== */
		// Retorno do método em teste
		Cliente cliente = gerClientes.pesquisaCliente(1);

		/* ========== Verificações ========== */
		// Verifica se o ID e o Email são correspondetes
		assertThat(cliente.getId(), is(1));
		assertThat(cliente.getEmail(), is("gugafarias@gmail.com"));

	}

	@Test
	public void testRemoveCliente() {

		/* ========== Montagem do cenário ========== */

		// criando alguns clientes
		Cliente cliente01 = new Cliente(1, "Gustavo Farias", 31, "gugafarias@gmail.com", 1, true);
		Cliente cliente02 = new Cliente(2, "Felipe Augusto", 34, "felipeaugusto@gmail.com", 1, true);

		// inserindo os clientes criados na lista de clientes do banco
		List<Cliente> clientesDoBanco = new ArrayList<>();
		clientesDoBanco.add(cliente01);
		clientesDoBanco.add(cliente02);

		gerClientes = new GerenciadoraClientes(clientesDoBanco);

		/* ========== Execução ========== */
		// Retorno e Método ao qual desejo testar
		boolean clienteRemovido = gerClientes.removeCliente(2);

		/* ========== Verificações ========== */
		// Verifica o valor da variável
		assertThat(clienteRemovido, is(true));
		// Verifica o qtd de clientes
		assertThat(gerClientes.getClientesDoBanco().size(), is(1));
		// Verifica o retorno é nulo
		assertNull(gerClientes.pesquisaCliente(2));

	}

}
