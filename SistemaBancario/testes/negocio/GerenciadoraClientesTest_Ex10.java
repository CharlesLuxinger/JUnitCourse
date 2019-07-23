package negocio;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de teste criada para garantir o funcionamento das principais operações
 * sobre clientes, realizadas pela classe {@link GerenciadoraClientes}.
 * 
 * @author Gustavo Farias
 * @date 21/01/2035
 */
public class GerenciadoraClientesTest_Ex10 {

	private GerenciadoraClientes gerClientes;
	private int idCLiente01 = 1;
	private int idCLiente02 = 2;

	@Before
	public void setUp() {

		/* ========== Montagem do cenário ========== */

		// criando alguns clientes
		Cliente cliente01 = new Cliente(idCLiente01, "Gustavo Farias", 31, "gugafarias@gmail.com", 1, true);
		Cliente cliente02 = new Cliente(idCLiente02, "Felipe Augusto", 34, "felipeaugusto@gmail.com", 1, true);

		// inserindo os clientes criados na lista de clientes do banco
		List<Cliente> clientesDoBanco = new ArrayList<>();
		clientesDoBanco.add(cliente01);
		clientesDoBanco.add(cliente02);

		gerClientes = new GerenciadoraClientes(clientesDoBanco);

		// a) Abriu conexão com o BD? Então...
		// b) Criou arquivos e pastas aqui? Então...
		// c) Inseriu clientes fictícios na base de dados especificamente para os testes
		// desta classe? Então...
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
	 * Teste básico da pesquisa de um cliente a partir do seu ID.
	 * 
	 * @author Gustavo Farias
	 * @date 21/01/2035
	 */
	@Test
	public void testPesquisaCliente() {

		/* ========== Execução ========== */
		// Retorno do método em teste
		Cliente cliente = gerClientes.pesquisaCliente(idCLiente01);

		/* ========== Verificações ========== */
		// Verifica se o ID e o Email são correspondetes
		assertThat(cliente.getId(), is(idCLiente01));
		assertThat(cliente.getEmail(), is("gugafarias@gmail.com"));

	}

	/**
	 * Teste básico da remoção de um cliente a partir do seu ID.
	 * 
	 * @author Gustavo Farias
	 * @date 21/01/2035
	 */
	@Test
	public void testRemoveCliente() {

		/* ========== Execução ========== */
		// Retorno e Método ao qual desejo testar
		boolean clienteRemovido = gerClientes.removeCliente(idCLiente02);

		/* ========== Verificações ========== */
		// Verifica o valor da variável
		assertThat(clienteRemovido, is(true));
		// Verifica o qtd de clientes
		assertThat(gerClientes.getClientesDoBanco().size(), is(1));
		// Verifica o retorno é nulo
		assertNull(gerClientes.pesquisaCliente(idCLiente02));

	}

	/**
	 * Teste da tentativa de remoção de um cliente inexistente.
	 * 
	 * @author Gustavo Farias
	 * @date 21/01/2035
	 */

	// Variação do Teste testRemoveCliente()
	@Test
	public void testRemoveClienteInexistente() {

		/* ========== Execução ========== */
		boolean clienteRemovido = gerClientes.removeCliente(1001);

		/* ========== Verificações ========== */
		assertThat(clienteRemovido, is(false));
		assertThat(gerClientes.getClientesDoBanco().size(), is(2));

	}

	/**
	 * Validação da idade de um cliente quando a mesma está no intervalo permitido.
	 * 
	 * @author Gustavo Farias
	 * @throws IdadeNaoPermitidaException
	 * @date 21/01/2035
	 */

	// Validação com retorno de uma exceção gerada pelo método.
	@Test
	public void testClienteIdadeAceitave_0l() throws IdadeNaoPermitidaException {

		/* ========== Montagem do Cenário ========== */
		Cliente cliente = new Cliente(1, "Gustavo", 25, "guga@gmail.com", 1, true);

		/* ========== Execução ========== */
		boolean idadeValida = gerClientes.validaIdade(cliente.getIdade());

		/* ========== Verificações ========== */
		assertTrue(idadeValida);
	}

	/**
	 * Validação da idade de um cliente quando a mesma está no intervalo permitido.
	 * 
	 * @author Gustavo Farias
	 * @throws IdadeNaoPermitidaException
	 * @date 21/01/2035
	 */
	// Validação com retorno de uma exceção gerada pelo método.
	@Test
	public void testClienteIdadeAceitavel_02() throws IdadeNaoPermitidaException {

		/* ========== Montagem do Cenário ========== */
		Cliente cliente = new Cliente(1, "Gustavo", 18, "guga@gmail.com", 1, true);

		/* ========== Execução ========== */
		boolean idadeValida = gerClientes.validaIdade(cliente.getIdade());

		/* ========== Verificações ========== */
		assertTrue(idadeValida);
	}

	/**
	 * Validação da idade de um cliente quando a mesma está no intervalo permitido.
	 * 
	 * @author Gustavo Farias
	 * @throws IdadeNaoPermitidaException
	 * @date 21/01/2035
	 */
	// Validação com retorno de uma exceção gerada pelo método.
	@Test
	public void testClienteIdadeAceitavel_03() throws IdadeNaoPermitidaException {

		/* ========== Montagem do Cenário ========== */
		Cliente cliente = new Cliente(1, "Gustavo", 65, "guga@gmail.com", 1, true);

		/* ========== Execução ========== */
		boolean idadeValida = gerClientes.validaIdade(cliente.getIdade());

		/* ========== Verificações ========== */
		assertTrue(idadeValida);
	}

	/**
	 * Validação da idade de um cliente quando a mesma está abaixo intervalo
	 * permitido.
	 * 
	 * @author Gustavo Farias
	 * @throws IdadeNaoPermitidaException
	 * @date 21/01/2035
	 */
	// Validação com retorno de uma exceção gerada pelo método capturando exceção e verificando.
	@Test
	public void testClienteIdadeAceitavel_04() throws IdadeNaoPermitidaException {

		/* ========== Montagem do Cenário ========== */
		Cliente cliente = new Cliente(1, "Gustavo", 17, "guga@gmail.com", 1, true);

		/* ========== Execução ========== */
		try {
			gerClientes.validaIdade(cliente.getIdade());
			fail();
		} catch (Exception e) {
			/* ========== Verificações ========== */
			assertThat(e.getMessage(), is(IdadeNaoPermitidaException.MSG_IDADE_INVALIDA));
		}
	}

	/**
	 * Validação da idade de um cliente quando a mesma está acima intervalo
	 * permitido.
	 * 
	 * @author Gustavo Farias
	 * @throws IdadeNaoPermitidaException
	 * @date 21/01/2035
	 */
	// Validação com retorno de uma exceção gerada pelo método capturando exceção e verificando.
	@Test
	public void testClienteIdadeAceitavel_05() throws IdadeNaoPermitidaException {

		/* ========== Montagem do Cenário ========== */
		Cliente cliente = new Cliente(1, "Gustavo", 66, "guga@gmail.com", 1, true);
		/* ========== Execução ========== */
		try {
			gerClientes.validaIdade(cliente.getIdade());
			fail();
		} catch (Exception e) {
			/* ========== Verificações ========== */
			assertThat(e.getMessage(), is(IdadeNaoPermitidaException.MSG_IDADE_INVALIDA));
		}
	}

}