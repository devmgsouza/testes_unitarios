package br.ce.wcaquino.servicos;

import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.waquino.builder.FilmeBuilder;
import br.ce.waquino.builder.UsuarioBuilder;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.matchers.MatcherProprios;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {
	
	LocacaoService service;
	private static Integer count  = 0; //Contador;

	@Rule
	public ErrorCollector error = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		service = new LocacaoService();
	
	}
	
//	@After
//	public void tearDown() {
//		System.out.println("After...");
//	}
//	
//	@BeforeClass
//	public static void setupClass() {
//		System.out.println("Before class");
//	}
//	
//	@AfterClass
//	public static void tearDownClass() {
//		System.out.println("After class");
//	}
	
	
	public static Filme filme1 = FilmeBuilder.umFilme().agora();
	public static Filme filme2 = FilmeBuilder.umFilme().agora();
	public static Filme filme3 = FilmeBuilder.umFilme().agora();
	public static Filme filme4 = FilmeBuilder.umFilme().agora();
	public static Filme filme5 = FilmeBuilder.umFilme().agora();
	public static Filme filme6 = FilmeBuilder.umFilme().agora();
	public static Filme filme7 = FilmeBuilder.umFilme().agora();
	
	@Test
	public void devePagar75NoFilme3() {
		
	}
	
	@Test
	public void deveAlugarFilme() throws Exception {
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(0),	 Calendar.SATURDAY));
		
		
		//Cenário
		
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		
		List<Filme> filme = Arrays.asList(filme1);
		
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filme); 
			
		
		
		//Verificação
		
		Assert.assertEquals(locacao.getValor(), 5.0, 0.01);
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), CoreMatchers.is(true));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),CoreMatchers.is(true));
		error.checkThat(locacao.getDataRetorno(), MatcherProprios.ehHojeComDiferencaDeDias(1));
		error.checkThat(locacao.getDataLocacao(), MatcherProprios.ehHoje());
		
	}
	
	//MODO ELEGANTE
	@Test(expected= FilmeSemEstoqueException.class)
	public void deveLancarExcecaoAoAlugarFilmeSemEstoque() throws Exception { //Lança Exceção para JUNIT
		//Cenário
		
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filme = Arrays.asList(FilmeBuilder.umFilmeSemEstoque().agora());
		
		
		
		//acao
		Locacao locacao = service.alugarFilme(usuario, filme); 
			
		
	}
	
	
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		//cenário
		
		List<Filme> filme = Arrays.asList(filme1);
		
		//acao
		
			try {
				service.alugarFilme(null, filme);
				Assert.fail();
			} catch (LocadoraException e) {
				Assert.assertThat(e.getMessage(), CoreMatchers.is("Usuario vazio"));
			} 
		
		
	}
	
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException{ //Garante que a exceção seja lançada apenas pelos motivos citados
		
		//Cenário
		
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		
		
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		//acao
		Locacao locacao = service.alugarFilme(usuario, null); 	
		
	}
	
	@Test
	public void devePagar25pctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
		
		//Cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().comValor(4.0).agora(), 
				FilmeBuilder.umFilme().comValor(4.0).agora(), FilmeBuilder.umFilme().comValor(4.0).agora());
		//Acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//Verificacao
		assertThat(resultado.getValor(), CoreMatchers.is(11.0));
		
		
	}
	

	@Test
	public void devePagar50pctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
		
		//Cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), 
				new Filme("Filme 2", 2, 4.0), new Filme("Filme 3", 2, 4.0), 
				new Filme("Filme 4", 2, 4.0));
		//Acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//Verificacao
		assertThat(resultado.getValor(), CoreMatchers.is(13.0));
		
		
	}
	
	@Test
	public void devePagar25pctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
		
		//Cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), 
				new Filme("Filme 2", 2, 4.0), new Filme("Filme 3", 2, 4.0), 
				new Filme("Filme 4", 2, 4.0), new Filme("Filme 5", 2, 4.0));
		//Acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//Verificacao
		assertThat(resultado.getValor(), CoreMatchers.is(14.0));
		
		
	}
	
	@Test
	public void devePagar0pctNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
		
		//Cenario
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1", 2, 4.0), 
				new Filme("Filme 2", 2, 4.0), new Filme("Filme 3", 2, 4.0), 
				new Filme("Filme 4", 2, 4.0), new Filme("Filme 5", 2, 4.0), new Filme("Filme 6", 2, 4.0));
		//Acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//Verificacao
		assertThat(resultado.getValor(), CoreMatchers.is(14.0));
		
		
	}
	
	@Test //Só deve funcionar nos Sabados
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		//cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());
		
		//acao
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		//verificacao
//		boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY);
//		assertTrue(ehSegunda);
//		assertThat(retorno.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
//		assertThat(retorno.getDataRetorno(), MatcherProprios.caiEm(Calendar.SUNDAY));
		assertThat(retorno.getDataRetorno(), MatcherProprios.caiNumaSegunda());
		
		
	}
	
	
	
	
	
	
	
	
	
	//ROBUSTA
	//@Test
	public void testeLocacaoFilmeSemEstoque2() {
		//Cenário
		
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filme = Arrays.asList(FilmeBuilder.umFilme().agora());
		
		
		
		//acao
		Locacao locacao;
		try {
			locacao = service.alugarFilme(usuario, filme); 
			Assert.fail("Deveria haver uma falha");
		} catch (Exception e) {
			Assert.assertThat(e.getMessage(), CoreMatchers.is("Filme sem Estoque"));
		}
			
		
	}
	
//	
//	@Test
//	public void testeLocacaoFilmeSemEstoque3() throws Exception { //Lança Exceção para JUNIT
//		//Cenário
//		LocacaoService service = new LocacaoService();
//		Usuario usuario = new Usuario("Usuario 1");
//		Filme filme = new Filme("Filme 1", 0, 5.0);
//		
//		
//		exception.expect(Exception.class);
//		exception.expectMessage("Filme sem estoque (Esperado)");
//			
//		//acao
//		 service.alugarFilme(usuario, filme); 
//		 
//		
//	}
}
