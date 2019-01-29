package ffk.league.model.results.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ffk.league.model.competitors.Competitor;

public class Results {

	private List<String> editions = new ArrayList<>();
	private Map<Competitor, PerformncesByEdition> performancesMapsByCompetitor = new HashMap<>();

	public List<String> getEditions() {
		return editions;
	}

	public Map<Competitor, PerformncesByEdition> getPerformancesMapsByCompetitor() {
		return performancesMapsByCompetitor;
	}

	public String toString() {
		StringBuilder res = new StringBuilder();
		for (Competitor competitor : performancesMapsByCompetitor.keySet()) {
			res.append(competitor.getName());
			res.append(';');
			res.append(competitor.getClub());
			res.append(';');
			res.append(competitor.getSex());
			res.append(';');
			res.append(competitor.getGroup());
			res.append(';');
			PerformncesByEdition performncesByEdition = performancesMapsByCompetitor.get(competitor);
			for (String edition : editions) {
				Performance performance = performncesByEdition.getMap().get(edition);
				res.append(edition);
				res.append(';');
				if (performance != null) {
					for (BoulderResult boulderResult : performance.getBoulderResults()) {
						res.append(boulderResult.name());
						res.append(' ');
					}
				}
				res.append(';');
			}
			res.append('\n');
		}
		return res.toString();
	}
}
