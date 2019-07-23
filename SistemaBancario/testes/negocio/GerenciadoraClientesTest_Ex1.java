package negocio;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GerenciadoraClientesTest_Ex1 {

	@Test
	public void testPesquisaCliente() {

		// Criando alguns clientes
		Cliente cliente01 = new Cliente(1, "Gustavo Farias", 31, "gugafarias@gmail.com", 1, true);
		Cliente cliente02 = new Cliente(2, "Felipe Augusto", 34, "felipeaugusto@gmail.com", 2, true);

		// Inserindo os clientes criados na lista de clientes do banco
		List<Cliente> clientesDoBanco = new ArrayList<>();
		clientesDoBanco.add(cliente01);
		clientesDoBanco.add(cliente02);

		// Método ao qual desejo testar
		GerenciadoraClientes gerClientes = new GerenciadoraClientes(clientesDoBanco);

		// Retorno do método em teste
		Cliente cliente = gerClientes.pesquisaCliente(1);

		// Verifica se o ID e o Email são correspondetes
		assertThat(cliente.getId(), is(1));
		assertThat(cliente.getEmail(), is("gugafarias@gmail.com"));

	}

}
