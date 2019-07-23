package negocio;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de teste criada para garantir o funcionamento das principais opera��es
 * sobre clientes, realizadas pela classe {@link GerenciadoraClientes}.
 * 
 * @author Gustavo Farias
 * @date 21/01/2035
 */
public class GerenciadoraClientesTest_Ex8 {

	private GerenciadoraClientes gerClientes;
	private int idCLiente01 = 1;
	private int idCLiente02 = 2;

	@Before
	public void setUp() {

		/* ========== Montagem do cen�rio ========== */

		// criando alguns clientes
		Cliente cliente01 = new Cliente(idCLiente01, "Gustavo Farias", 31, "gugafarias@gmail.com", 1, true);
		Cliente cliente02 = new Cliente(idCLiente02, "Felipe Augusto", 34, "felipeaugusto@gmail.com", 1, true);

		// inserindo os clientes criados na lista de clientes do banco
		List<Cliente> clientesDoBanco = new ArrayList<>();
		clientesDoBanco.add(cliente01);
		clientesDoBanco.add(cliente02);

		gerClientes = new GerenciadoraClientes(clientesDoBanco);

		// a) Abriu conex�o com o BD? Ent�o...
		// b) Criou arquivos e pastas aqui? Ent�o...
		// c) Inseriu clientes fict�cios na base de dados especificamente para os testes
		// desta classe? Ent�o...
	}

	@After
	public void tearDown() {
		// Limpa a lista de cliente
		gerClientes.limpa();

		// a) Fecha aqui!!!
		// b) Apaga todos eles aqui!!!
		// c) Apaga eles aqui!!!
	}

	/**
	 * Teste b�sico da pesquisa de um cliente a partir do seu ID.
	 * 
	 * @author Gustavo Farias
	 * @date 21/01/2035
	 */
	@Test
	public void testPesquisaCliente() {

		/* ========== Execu��o ========== */
		// Retorno do m�todo em teste
		Cliente cliente = gerClientes.pesquisaCliente(idCLiente01);

		/* ========== Verifica��es ========== */
		// Verifica se o ID e o Email s�o correspondetes
		assertThat(cliente.getId(), is(idCLiente01));
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

		/* ========== Execu��o ========== */
		// Retorno e M�todo ao qual desejo testar
		boolean clienteRemovido = gerClientes.removeCliente(idCLiente02);

		/* ========== Verifica��es ========== */
		// Verifica o valor da vari�vel
		assertThat(clienteRemovido, is(true));
		// Verifica o qtd de clientes
		assertThat(gerClientes.getClientesDoBanco().size(), is(1));
		// Verifica o retorno � nulo
		assertNull(gerClientes.pesquisaCliente(idCLiente02));

	}

}
// A Independ�ncia do Teste