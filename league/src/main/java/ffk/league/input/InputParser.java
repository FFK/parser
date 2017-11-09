package ffk.league.input;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import ffk.league.model.competitors.Competitor;
import ffk.league.model.competitors.CompetitorsFactory;
import ffk.league.model.results.input.BoulderResult;
import ffk.league.model.results.input.Performance;
import ffk.league.model.results.output.CompetitorAndPerformance;

public class InputParser {

	private BoulderResult parseBoulderResult(String top, String bonus) {
		BoulderResult res = new BoulderResult();
		if (!StringUtils.isEmpty(top)) {
			res.setTop(Integer.parseInt(top));
		}
		if (!StringUtils.isEmpty(bonus)) {
			res.setBonus(Integer.parseInt(bonus));
		}
		return res;
	}

	private List<BoulderResult> parseBoulderResults(List<String> fields) {
		List<BoulderResult> boulders = new ArrayList<>();
		final int shift = 2;
		for (int i = 0; i < 8; ++i) {
			fields.add("");
			fields.add("");
			boulders.add(parseBoulderResult(fields.get(shift + i * 2), fields.get(shift + i * 2 + 1)));
		}
		return boulders;
	}

	private Competitor parseCompetitor(List<String> fields) {
		return CompetitorsFactory.getOrCreateCompetitor(fields.get(0), fields.get(1));
	}

	public CompetitorAndPerformance parseResultLine(List<String> fields) {
		if (fields.size() < 2 || StringUtils.isEmpty(fields.get(0)) || StringUtils.isEmpty(fields.get(1))) {
			return null;
		}
		List<BoulderResult> boulderResults = parseBoulderResults(fields);
		Competitor competitor = parseCompetitor(fields);
		return new CompetitorAndPerformance(new Performance(competitor.getGroup(), boulderResults), competitor);
	}

}
