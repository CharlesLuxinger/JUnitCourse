package br.ce.wcaquino.servicos;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmesSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.servicos.daos.LocacaoDAO;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {

	private LocacaoDAO locacaoDAO;

	private SPCService spcService;

	private EmailService emailService;

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes)
			throws FilmesSemEstoqueException, LocadoraException {
		if (usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}

		if (filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme vazio");
		}

		for (Filme filme : filmes) {
			if (filme.getEstoque() == 0) {
				throw new FilmesSemEstoqueException();
			}
		}

		boolean negativado;
		
		try {
			negativado = spcService.possuiNegativacao(usuario);
		} catch (Exception e) {
			throw new LocadoraException("Problemas com SPS, tente novamente!");
		}

		if (negativado) {
			throw new LocadoraException("Usu�rio Negativado!");
		}

		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());

		Double valotTotal = 0.0;
		for (int i = 0; i < filmes.size(); i++) {
			Filme filme = filmes.get(i);
			Double valorFilme = filme.getPrecoLocacao();

			switch (i) {
			case 2:
				valorFilme = valorFilme * 0.75;
				break;
			case 3:
				valorFilme = valorFilme * 0.5;
				break;
			case 4:
				valorFilme = valorFilme * 0.25;
				break;
			case 5:
				valorFilme = 0.0;
				break;
			}
			valotTotal += valorFilme;
		}

		locacao.setValor(valotTotal);

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);

		if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);
		}

		locacao.setDataRetorno(dataEntrega);

		// Salvando a locacao...
		locacaoDAO.salvar(locacao);

		return locacao;
	}

	public void notificarAtrasos() {
		List<Locacao> locacoes = locacaoDAO.obterLocacoesPendentes();
		for (Locacao locacao : locacoes) {
			if (locacao.getDataRetorno().before(new Date())) {
				emailService.notificarAtraso(locacao.getUsuario());
			}
		}
	}

	/*
	 * Substituido pelo MockitoAnnotations public void setEmailService(EmailService
	 * email) { this.emailService = email; }
	 * 
	 * public void setLocacaoDAO(LocacaoDAO dao) { this.locacaoDAO = dao; }
	 * 
	 * public void setSPCService(SPCService spc) { this.spcService = spc; }
	 */
	
	public void porrogarLocacao(Locacao locacao, int dias) {
		Locacao novaLocacao = new Locacao();
		novaLocacao.setUsuario(locacao.getUsuario());
		novaLocacao.setFilmes(locacao.getFilmes());
		novaLocacao.setDataLocacao(new Date());
		novaLocacao.setDataRetorno(DataUtils.obterDataComDiferencaDias(dias));
		novaLocacao.setValor(locacao.getValor()* dias);
		
		locacaoDAO.salvar(novaLocacao);
	}
}