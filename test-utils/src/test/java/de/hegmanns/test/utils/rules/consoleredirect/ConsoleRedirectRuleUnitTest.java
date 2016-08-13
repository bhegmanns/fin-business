package de.hegmanns.test.utils.rules.consoleredirect;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Rule;
import org.junit.Test;

/**
 * Tests for {@link ConsoleRedirectRule}.
 * 
 * @author B. Hegmanns
 *
 */
public class ConsoleRedirectRuleUnitTest {

	@Rule
	public ConsoleRedirectRule consoleRedirectRule = ConsoleRedirectRule.instance();
	
	@Test
	public void rule_detects_consoleoutput_with_newline(){
		String text = "HALLO HALLO";
		System.out.println(text);
		
		assertThat(consoleRedirectRule.getOutput(), containsString(text));
	}
	
	@Test
	public void rule_detects_consoleoutput_without_newline(){
		String text = "HALLO HALLO";
		System.out.print(text);
		
		assertThat(consoleRedirectRule.getOutput(), is(text));
	}
	
	
	
	@Test
	public void rule_dont_detects(){
		String text = "Hallo Hallo";
		
		System.out.println(text);
		assertThat(consoleRedirectRule.getOutput(), not("bla bla bla"));
		assertThat(consoleRedirectRule.getOutput(), not(containsString("bla bla bla")));
	}
}
