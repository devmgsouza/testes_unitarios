package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {

	
	@Test
	public void teste() {
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		Assert.assertEquals("Erro de comparação", 1, 1);
		Assert.assertEquals(0.51234, 0.512, 0.001); //Valor 1 - Valor 2 - Margem de Erro
		Assert.assertEquals(Math.PI, 3.14, 0.01);
		
		int i = 5;
		Integer i2 = 5;
		
		Assert.assertEquals(Integer.valueOf(i), i2);
		
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
		
		
		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");
		
		Usuario u3 = null; //mesma instancia
		
		Assert.assertEquals(u1, u2);
		
		//Assert.assertSame(u3, u2); //Verifica se é mesma instancia
		
		
		Assert.assertNull(u3);
	}
	
}
