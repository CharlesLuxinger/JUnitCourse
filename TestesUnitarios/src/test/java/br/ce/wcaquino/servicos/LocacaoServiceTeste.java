package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmesSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTeste {
	// O JUnit não garante à ordem de execução, então é necessário manter os testes
	// independentes. Somente através da anotação
	// @FixMethodOrder(MethodSorters.NAME_ASCENDING)

	LocacaoService service;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@BeforeClass
	public static void setUpClass() {
		// Executa antes da classe Test se iniciada, necessitando que o método seja
		// static
	}

	@AfterClass
	public static void tearDownClass() {
		// Executa depois da classe Test se finilizar, necessitando que o método seja
		// static
	}

	@Before
	public void setUp() {
		// Executa antes de cada método @Test
		// service = new LocacaoService();
		service = new LocacaoService();
	}

	@After
	public void tearDown() {
		// Executa depois de cada método @Test
	}

	@Test
	public void alugarFilme_Teste() throws Exception {
		// Cénario

		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));

		// Ação

		Locacao locacao = service.alugarFilme(usuario, filmes);

		// Verificação
		assertEquals(5.0, locacao.getValor(), 0.01);
		assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));

		// AssertThat (Verifique que...)
		assertThat(locacao.getValor(), is(equalTo(5.0)));
		assertThat(locacao.getValor(), is(not(6.0)));
		assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));

		// Checkthat Estoura a pilha de erros sem parar a execução, testando tds às
		// assertivas
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(locacao.getValor(), is(not(6.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				is(true));

	}

	// Tratando exceções
	// Não garante que a excessão e a esperada, por não tratar qual mensagem é
	// enviada, sendo necessário criar uma excessão espefica
	// @Test(expected = Exception.class)
	@Test(expected = FilmesSemEstoqueException.class)
	public void alugarFilme_SemEstoque_Teste() throws Exception {
		// Cénario

		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		// Ação
		service.alugarFilme(usuario, filmes);
	}

	@Test
	public void alugarFilme_SemEstoque2_Teste() throws LocadoraException {
		// Cénario

		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		// Ação
		try {
			service.alugarFilme(usuario, filmes);
			fail("Deveria lançar excessão");
		} catch (FilmesSemEstoqueException e) {
			assertEquals(e.getMessage(), null);
		}
	}

	@Test
	public void alugarFilme_SemEstoque3_Teste() throws Exception {
		// Cénario

		Usuario usuario = new Usuario("Usuario 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 0, 5.0));

		// Deve ser declarado antes da ação
		expectedException.expect(FilmesSemEstoqueException.class);
		// expectedException.expectMessage(); -- não aceita mensagem null

		// Ação
		service.alugarFilme(usuario, filmes);

	}

	@Test
	public void locacao_usuarioVazio_Test() throws FilmesSemEstoqueException {
		// Cénario

		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));

		// Ação
		try {
			service.alugarFilme(null, filmes);
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}

		// o codigo continua após a verificação da excessão
	}

	@Test
	public void locacao_filmeVazio_Test() throws FilmesSemEstoqueException, LocadoraException {
		// Cénario
		Usuario usuario = new Usuario("Usuario 1");

		expectedException.expect(Exception.class);
		expectedException.expectMessage("Filme vazio");

		// o codigo não continua após a verificação da excessão
		// Ação
		service.alugarFilme(usuario, null);
	}
}