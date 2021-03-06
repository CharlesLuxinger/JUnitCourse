package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.FilmeBuilder.umFilmeSemEstoque;
import static br.ce.wcaquino.builders.LocacaoBuilder.umLocacao;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.matchers.DataDiferencaDiaMatcher.ehHojeComDiferencaDias;
import static br.ce.wcaquino.matchers.OwnMatchers.caiEm;
import static br.ce.wcaquino.matchers.OwnMatchers.caiNumaSegunda;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmesSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.servicos.daos.LocacaoDAO;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTeste {
	// O JUnit n�o garante � ordem de execu��o, ent�o � necess�rio manter os testes
	// independentes. Somente atrav�s da anota��o
	// @FixMethodOrder(MethodSorters.NAME_ASCENDING)

	@InjectMocks
	private LocacaoService service;
	
	@Mock
	private LocacaoDAO dao;
	
	@Mock
	private SPCService spc;
	
	@Mock
	private EmailService email;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@BeforeClass
	public static void setUpClass() {
		// Executa antes da classe Test se iniciada, necessitando que o m�todo seja
		// static
	}

	@AfterClass
	public static void tearDownClass() {
		// Executa depois da classe Test se finilizar, necessitando que o m�todo seja
		// static
	}

	@Before
	public void setUp() {
		
		// Executa antes de cada m�todo @Test
		
		/* Substituido pelo MockitoAnnotations
		 * service = new LocacaoService();
		 * 
		 * dao = mock(LocacaoDAO.class); service.setLocacaoDAO(dao);
		 * 
		 * spc = mock(SPCService.class); service.setSPCService(spc);
		 * 
		 * email = mock(EmailService.class); service.setEmailService(email);
		 */
		
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() {
		// Executa depois de cada m�todo @Test
	}

	@Test
	// @Ignore
	public void alugarFilme_Teste() throws Exception {

		assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		// C�nario

		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().comValor(5.0).agora());

		// A��o

		Locacao locacao = service.alugarFilme(usuario, filmes);

		// Verifica��o
		assertEquals(5.0, locacao.getValor(), 0.01);
		assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));

		// AssertThat (Verifique que...)
		assertThat(locacao.getValor(), is(equalTo(5.0)));
		assertThat(locacao.getValor(), is(not(6.0)));
		assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));

		// Checkthat Estoura a pilha de erros sem parar a execu��o, testando tds �s
		// assertivas
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(locacao.getValor(), is(not(6.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
				is(true));

		error.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));

	}

	// Tratando exce��es
	// N�o garante que a excess�o e a esperada, por n�o tratar qual mensagem �
	// enviada, sendo necess�rio criar uma excess�o espefica
	// @Test(expected = Exception.class)
	@Test(expected = FilmesSemEstoqueException.class)
	public void alugarFilme_SemEstoque_Teste() throws Exception {
		// C�nario

		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().semEstoque().agora());

		// A��o
		service.alugarFilme(usuario, filmes);
	}

	@Test
	public void alugarFilme_SemEstoque2_Teste() throws LocadoraException {
		// C�nario

		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilmeSemEstoque().agora());

		// A��o
		try {
			service.alugarFilme(usuario, filmes);
			fail("Deveria lan�ar excess�o");
		} catch (FilmesSemEstoqueException e) {
			assertEquals(e.getMessage(), null);
		}
	}

	@Test
	public void alugarFilme_SemEstoque3_Teste() throws Exception {
		// C�nario

		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().semEstoque().agora());

		// Deve ser declarado antes da a��o
		expectedException.expect(FilmesSemEstoqueException.class);
		// expectedException.expectMessage(); -- n�o aceita mensagem null

		// A��o
		service.alugarFilme(usuario, filmes);

	}

	@Test
	public void locacao_usuarioVazio_Test() throws FilmesSemEstoqueException {
		// C�nario

		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 5.0));

		// A��o
		try {
			service.alugarFilme(null, filmes);
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}

		// o codigo continua ap�s a verifica��o da excess�o
	}

	@Test
	public void locacao_filmeVazio_Test() throws FilmesSemEstoqueException, LocadoraException {
		// C�nario
		Usuario usuario = umUsuario().agora();

		expectedException.expect(Exception.class);
		expectedException.expectMessage("Filme vazio");

		// o codigo n�o continua ap�s a verifica��o da excess�o
		// A��o
		service.alugarFilme(usuario, null);
	}

	@Test
	public void devePagar75PctNoFilme3() throws FilmesSemEstoqueException, LocadoraException {
		// Cen�rio
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0));

		// A��o
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// Verificacao
		assertThat(resultado.getValor(), is(11.0));
	}

	@Test
	public void devePagar50PctNoFilme4() throws FilmesSemEstoqueException, LocadoraException {
		// Cen�rio
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0));

		// A��o
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// Verificacao
		assertThat(resultado.getValor(), is(13.0));
	}

	@Test
	public void devePagar25PctNoFilme5() throws FilmesSemEstoqueException, LocadoraException {
		// Cen�rio
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0), new Filme("Filme 5", 2, 4.0));

		// A��o
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// Verificacao
		assertThat(resultado.getValor(), is(14.0));
	}

	@Test
	public void devePagar0PctNoFilme6() throws FilmesSemEstoqueException, LocadoraException {
		// Cen�rio
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0), new Filme("Filme 5", 2, 4.0),
				new Filme("Filme 6", 2, 4.0));

		// A��o
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// Verificacao
		assertThat(resultado.getValor(), is(14.0));
	}

	@Test
	// @Ignore
	public void deveDevolverNaSegundaAoAlugarSabado() throws FilmesSemEstoqueException, LocadoraException {

		assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		// Cen�rio
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0));

		// A��o
		Locacao resultado = service.alugarFilme(usuario, filmes);

		// Verificacao
		boolean segunda = DataUtils.verificarDiaSemana(resultado.getDataRetorno(), Calendar.MONDAY);
		assertThat(resultado.getDataRetorno(), caiEm(Calendar.MONDAY));
		assertThat(resultado.getDataRetorno(), caiNumaSegunda());

		assertTrue(segunda);
	}

	@Test
	public void n�oDeveAlugarFilmeParaNegativadoSPC() throws Exception {
		// Cen�rio
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());

		when(spc.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);

		// A��o

		try {
			service.alugarFilme(usuario, filmes);
			fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usu�rio Negativado!"));
		}

		// Verifica��o
		verify(spc).possuiNegativacao(usuario);
	}

	@Test
	public void deveEnviarEmailParaLocacoesAtrasadas() {
		// Cen�rio
		Usuario usuario = umUsuario().agora();
		Usuario usuario2 = umUsuario().comNome("Usuario 2").agora();
		List<Locacao> locacoes = Arrays.asList(
				umLocacao().atrasado().comUsuario(usuario).agora(),
				umLocacao().comUsuario(usuario2).agora());

		when(dao.obterLocacoesPendentes()).thenReturn(locacoes);

		// A��o
		service.notificarAtrasos();

		// Verifica��o
		verify(email).notificarAtraso(usuario);
		verify(email, never()).notificarAtraso(usuario2);
		
		verifyNoMoreInteractions(email);
		verifyZeroInteractions(spc);
	}
	
	@Test
	public void deveTratarErroNoSPC() throws Exception {
		//Cen�rio
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());
		
		when(spc.possuiNegativacao(usuario)).thenThrow(new Exception("Falha no Sistema SPC!"));
		
		//Verifica��o
		expectedException.expect(LocadoraException.class);
		expectedException.expectMessage("Problemas com SPS, tente novamente!");
		
		//A��o
		service.alugarFilme(usuario, filmes);
		
		
	}
	
	@Test
	public void deveProrrogarUmaLocacao() {
		//Cen�rio
		Locacao locacao = umLocacao().agora();
		
		//A��o
		service.porrogarLocacao(locacao, 3);
		
		//Verifica��o
		ArgumentCaptor<Locacao> argCapt = ArgumentCaptor.forClass(Locacao.class);
		verify(dao).salvar(argCapt.capture());
		
		Locacao locacaoRetorno = argCapt.getValue();
		
		error.checkThat(locacaoRetorno.getValor(), is(12.0));
		error.checkThat(locacaoRetorno.getDataLocacao(), ehHojeComDiferencaDias(0));
		error.checkThat(locacaoRetorno.getDataRetorno(), ehHojeComDiferencaDias(3));
	}
}