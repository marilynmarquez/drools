package com.sample;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

import util.KnowledgeSessionHelper;
import util.OutputDisplay;

public class TestProduct {
	StatelessKieSession sessionStateless = null; 
	KieSession sessionStatefull = null; 
	static KieContainer kieContainer; 
	
	@BeforeClass
	public static void beforeClass(){
		kieContainer = KnowledgeSessionHelper.createRuleBase(); 
	}
	
	
	@Before
	public void setUp() throws Exception{
		System.out.println("------------------Before Product------------------");
	}
	
	@After
	public void tearDown() throws Exception{
		System.out.println("------------------After Product------------------");
	}
	
	
	@Test
	public void testFirstOne() {
		sessionStatefull = KnowledgeSessionHelper.getStatefullKnowledgeSesion(kieContainer, "ksession-product");
		Product product = new Product(); 
		product.setType("diamond");
		sessionStatefull.insert(product); 		
		sessionStatefull.fireAllRules(); 
		
		System.out.println("The discount for the jewellery product "
				+ product.getType() + " is " + product.getDiscount());
	}
}
