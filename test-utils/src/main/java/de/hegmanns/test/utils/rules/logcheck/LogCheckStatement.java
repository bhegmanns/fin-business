package de.hegmanns.test.utils.rules.logcheck;

import org.junit.runners.model.Statement;

import java.util.List;

public class LogCheckStatement extends Statement {
    private Statement statement;
    private List<String> expectedMessages;
    private LogCheckAppender appender;

    public LogCheckStatement(Statement statement, List<String> expectedMessages, LogCheckAppender appender) {
        this.statement = statement;
        this.expectedMessages = expectedMessages;
        this.appender = appender;
    }

    @Override
    public void evaluate() throws Throwable {
        statement.evaluate();
        expectedMessages.stream().forEach(appender::validate);
    }
}
