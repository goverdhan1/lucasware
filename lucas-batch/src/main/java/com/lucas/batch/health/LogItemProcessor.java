package com.lucas.batch.health;

import org.springframework.batch.item.ItemProcessor;

import com.lucas.entity.health.LogLine;

public class LogItemProcessor implements ItemProcessor<LogLine, LogLine > {

	@Override
	public LogLine process(LogLine logLine) throws Exception {
		LogLine ll = null;
		if (logLine != null) {
			if (logLine.getTimestampDecoration() != null){
				ll = null;
			} else {
				ll = logLine; //No processing for now
			}
		} 
		System.out.println("Processed logLineId: " + logLine.getLogData());
		return ll;
	}

}
