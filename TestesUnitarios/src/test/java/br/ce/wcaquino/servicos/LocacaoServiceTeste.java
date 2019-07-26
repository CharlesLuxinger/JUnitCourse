package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

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

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void alugarFilme_Teste() throws Exception {
		// C�nario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// A��o

		Locacao locacao = service.alugarFilme(usuario, filme);

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

	}

	// Tratando exce��es
	// N�o garante que a excess�o e a esperada, por n�o tratar qual mensagem �
	// enviada, sendo necess�rio criar uma excess�o espefica
	// @Test(expected = Exception.class)
	@Test(expected = FilmesSemEstoqueException.class)
	public void alugarFilme_SemEstoque_Teste() throws Exception {
		// C�nario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// A��o
		service.alugarFilme(usuario, filme);
	}

	@Test
	public void alugarFilme_SemEstoque2_Teste() throws LocadoraException {
		// C�nario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// A��o
		try {
			service.alugarFilme(usuario, filme);
			fail("Deveria lan�ar excess�o");
		} catch (FilmesSemEstoqueException e) {
			assertEquals(e.getMessage(), null);
		}
	}

	@Test
	public void alugarFilme_SemEstoque3_Teste() throws Exception {
		// C�nario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);

		// Deve ser declarado antes da a��o
		expectedException.expect(FilmesSemEstoqueException.class);
		//expectedException.expectMessage(); -- n�o aceita mensagem null

		// A��o
		service.alugarFilme(usuario, filme);

	}

	@Test
	public void locacao_usuarioVazio_Test() throws FilmesSemEstoqueException {
		// C�nario
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme 1", 2, 5.0);

		// A��o
		try {
			service.alugarFilme(null, filme);
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
		
		//o codigo continua ap�s a verifica��o da excess�o
	}

	@Test
	public void locacao_filmeVazio_Test() throws FilmesSemEstoqueException, LocadoraException {
		// C�nario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Usuario 1");

		expectedException.expect(Exception.class);
		expectedException.expectMessage("Filme vazio");
		
		//o codigo n�o continua ap�s a verifica��o da excess�o
		// A��o
		service.alugarFilme(usuario, null);
	}
}