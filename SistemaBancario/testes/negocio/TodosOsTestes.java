package negocio;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

// Informa que a Classe e uma Suite Class
@RunWith(Suite.class)

// Informa quais classes serão executadas pela classe Suite
@SuiteClasses({ GerenciadoraClientesTest_Ex1.class, 
				GerenciadoraClientesTest_Ex2.class,
				GerenciadoraClientesTest_Ex3.class, 
				GerenciadoraClientesTest_Ex4.class, 
				GerenciadoraClientesTest_Ex5.class,
				GerenciadoraClientesTest_Ex7.class, 
				GerenciadoraClientesTest_Ex8.class, 
				GerenciadoraClientesTest_Ex10.class,
				GerenciadoraContasTest_Ex3.class, 
				GerenciadoraContasTest_Ex4.class, 
				GerenciadoraContasTest_Ex6.class,
				GerenciadoraContasTest_Ex11.class })

// Nome da Suite Class
public class TodosOsTestes {

}

