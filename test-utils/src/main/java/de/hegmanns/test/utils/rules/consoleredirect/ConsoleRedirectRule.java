package de.hegmanns.test.utils.rules.consoleredirect;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * This JUnit-Rule redirects the console-output to this rule-instance.
 * 
 * If your application do have code like "System.out.println(...)" and you want
 * to check this output, you can use this {@link ConsoleRedirectRule}.
 * 
 * <pre>
 * <code>
 * public class YourTest{
 *  Rule
 *  private ConsoleRedirectRule consoleRedirectRule = ConsoleRedirectRule.instance();
 *  
 *  public void yourTestMethod(){
 *     System.out.println("HalloWelt");
 *     assertThat(consoleRedirectRule.getConsoleoutput(), is("HalloWelt"));
 *  }
 * 
 * }
 * </code>
 * </pre>
 * 
 * @author B. Hegmanns
 */
public class ConsoleRedirectRule implements TestRule{
	
	private ConsoleRedirectStatement consoleRedirectStatement = new ConsoleRedirectStatement();
	
	/**
	 * 
	 * @return a new ConsoleRedirectRule-instance
	 */
	public static ConsoleRedirectRule instance() {
		return new ConsoleRedirectRule();
	}

	@Override
	public Statement apply(Statement baseStatement, Description description) {
		return consoleRedirectStatement;
	}

	public String getOutput() {
		if (consoleRedirectStatement.testOutputString != null){
			return consoleRedirectStatement.testOutputString;
		}else{
			String output = new String(consoleRedirectStatement.byteArrayOutputStream.toByteArray());
			return output;
		}
	}

	private class ConsoleRedirectStatement extends Statement{
		
		private PrintStream realPrintStream = null;
		private PrintStream testPrintStream = null;
		
		private ByteArrayOutputStream byteArrayOutputStream = null;
		private String testOutputString = null;
		private Statement statement;
		
		@Override
		public void evaluate() throws Throwable {
			realPrintStream = System.out;
			
			byteArrayOutputStream = new ByteArrayOutputStream();
			testPrintStream = new PrintStream(byteArrayOutputStream);
			System.setOut(testPrintStream);
			
			try{
				statement.evaluate();
			}catch(Throwable throwable){}
			finally{
				testOutputString = new String(byteArrayOutputStream.toByteArray());
				System.setOut(realPrintStream);
				testPrintStream.close();
				try{
					byteArrayOutputStream.close();
				}catch(IOException e){}
			}
		}
		
	}
}
