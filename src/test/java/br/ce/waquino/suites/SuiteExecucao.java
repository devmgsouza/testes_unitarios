package br.ce.waquino.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.ce.wcaquino.servicos.CalculoValorLocacaoTest;
import br.ce.wcaquino.servicos.LocacaoServiceTest;

@RunWith(Suite.class) //Executa os testes da classe de Testes
@SuiteClasses({
	CalculoValorLocacaoTest.class,
	LocacaoServiceTest.class
})
public class SuiteExecucao {
	
//	@BeforeClass
//	public static void before() {
//		System.out.println("Before");
//	}
//	
//	@AfterClass
//	public static void after() {
//		System.out.println("After");
//	}
}
