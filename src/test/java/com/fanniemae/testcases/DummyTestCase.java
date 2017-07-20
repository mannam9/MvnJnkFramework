package com.fanniemae.testcases;

import org.testng.annotations.Test;

import com.fanniemae.base.TestBase;

@Test
public class DummyTestCase extends TestBase{
	
	@Test(enabled=true)
	public void LoginTest(){
		
		//TestBase Tb = new TestBase();
		
		click("id_LoginBtn");
		type("xpath_uName", "sdet713@sdettraining.com");
		type("xpath_uPass", "password123");
		click("xpath_ActLogin");
				
	}
	

}
