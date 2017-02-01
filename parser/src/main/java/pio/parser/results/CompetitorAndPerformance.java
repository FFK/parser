package pio.parser.results;

import pio.parser.competitors.Competitor;
import pio.parser.model.Performance;

public class CompetitorAndPerformance {

	Competitor competitor;
	Performance performance;

	public CompetitorAndPerformance(Performance performance, Competitor competitor) {
		super();
		this.performance = performance;
		this.competitor = competitor;
	}

	public Competitor getCompetitor() {
		return competitor;
	}

	public Performance getResults() {
		return performance;
	}

}
