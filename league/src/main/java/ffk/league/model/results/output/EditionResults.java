package ffk.league.model.results.output;

import java.util.ArrayList;
import java.util.Collection;

public class EditionResults {

	private String name;
	private Collection<CompetitorAndPerformance> performances = new ArrayList<>();

	public EditionResults(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Collection<CompetitorAndPerformance> getPerformances() {
		return performances;
	}

}
