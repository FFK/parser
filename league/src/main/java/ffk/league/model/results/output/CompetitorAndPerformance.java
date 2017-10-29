package ffk.league.model.results.output;

import ffk.league.model.competitors.Competitor;
import ffk.league.model.results.input.Performance;

public class CompetitorAndPerformance {

	Competitor competitor;
	Performance performance;

	public CompetitorAndPerformance(Performance performance, Competitor competitor) {
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
