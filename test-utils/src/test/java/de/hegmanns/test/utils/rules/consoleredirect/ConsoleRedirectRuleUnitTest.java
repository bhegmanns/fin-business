package de.hegmanns.test.utils.rules.consoleredirect;

import static org.hamcrest.CoreMatchers.is;
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
	public void rule_detects_consoleoutput(){
		String text = "HALLO HALLO";
		
		System.out.println(text);
		
		assertThat(consoleRedirectRule.getOutput(), is(text));
	}
}
