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
 * 
 *  &#64;Rule
 *  private ConsoleRedirectRule consoleRedirectRule = ConsoleRedirectRule.instance();
 *  
 *  public void yourTestMethod(){
 *     System.out.println("HalloWelt");
 *     assertThat(consoleRedirectRule.getOutput(), containsString("HalloWelt"));
 *     assertThat(consoleRedirectRule.getOutput(), not("HalloWelt"));
 *  }
 *  
 *  public void yourSecondTestMethod(){
 *     System.out.print("HalloWelt");
 *     assertThat(consoleRedirectRule.getOutput(), is("HalloWelt"));
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
		consoleRedirectStatement = new ConsoleRedirectStatement();
		consoleRedirectStatement.statement = baseStatement;
		return consoleRedirectStatement;
	}

	/**
	 * Returns the Output.
	 * 
	 * <p>
	 * Note:<br>
	 * If the SUT make System.out.println(...), the output contains the linefeeds.
	 * </p>
	 * @return the String-output, done via System.out
	 */
	public String getOutput() {
		String returnString = null;
		if (consoleRedirectStatement.testOutputString != null){
			returnString = consoleRedirectStatement.testOutputString;
		}else{
			returnString = new String(consoleRedirectStatement.byteArrayOutputStream.toByteArray());
		}
		
		return returnString;
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
			}
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
