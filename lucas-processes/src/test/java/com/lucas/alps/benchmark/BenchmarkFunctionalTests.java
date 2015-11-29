package com.lucas.alps.benchmark;

import org.junit.Test;

/**
 * This is a long running test to ensure that our benchmark does not break.
 * @author PankajTandon
 *
 */
public class BenchmarkFunctionalTests {
	@Test
	public void testBenchmark(){
		String[] iterations = new String[]{"2"};
		Benchmark.runBenchmark(iterations);
	}
}
