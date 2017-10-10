package de.hegmanns.test.utils.rules.logcheck;

import java.util.ArrayList;
import java.util.List;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;

public class LogCheckAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

	private List<ILoggingEvent> loggingEvents = new ArrayList<>();
	
	@Override
	protected void append(ILoggingEvent eventObject) {
		loggingEvents.add(eventObject);
	}

	public void validate(String exptectedMessage) throws AssertionError{
		if (loggingEvents.stream().filter((l) -> l.getMessage().contains(exptectedMessage)).count() == 0) {
			throw new AssertionError("Expected message or part '" + exptectedMessage + "' didn't log");
		}
	}
}
