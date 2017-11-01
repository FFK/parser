package ffk.league.input;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import ffk.league.model.competitors.Competitor;
import ffk.league.model.competitors.CompetitorsFactory;
import ffk.league.model.competitors.Group;
import ffk.league.model.competitors.Sex;
import ffk.league.model.results.input.BoulderResult;
import ffk.league.model.results.input.Performance;
import ffk.league.model.results.output.CompetitorAndPerformance;

public class InputParser {

	public boolean isResultLineBlank(List<String> fields) {
		return fields.size() < 24 || StringUtils.isEmpty(fields.get(1));
	}

	private BoulderResult parseBoulderResult(String result) {
		switch (result.toUpperCase().trim()) {
		case "T":
			return BoulderResult.TOP;
		case "B":
			return BoulderResult.BONUS;
		default:
			return BoulderResult.ZERO;
		}
	}

	private List<BoulderResult> parseBoulderResults(List<String> fields) {
		List<BoulderResult> boulders = new ArrayList<>();
		final int shift = 3;
		for (int i = 0; i < 20; ++i) {
			boulders.add(parseBoulderResult(fields.get(shift + i)));
		}
		return boulders;
	}

	private Competitor parseCompetitor(List<String> fields) {
		return CompetitorsFactory.getOrCreateCompetitor(fields.get(2), parseGroup(fields.get(23)), fields.get(1),
				parseSex(fields.get(24)));
	}

	private Group parseGroup(String group) {
		switch (group.toUpperCase().trim()) {
		case "PRO":
			return Group.PRO;
		case "TRUDNA":
			return Group.HARD;
		case "LAJT":
			return Group.EASY;
		case "JUNIOR":
			return Group.JUNIOR;
		case "WETERAN":
			return Group.VETERAN;
		default:
			return Group.EASY;
		}
	}

	public CompetitorAndPerformance parseResultLine(List<String> fields) {
		if (isResultLineBlank(fields)) {
			return null;
		}
		List<BoulderResult> boulderResults = parseBoulderResults(fields);
		Competitor competitor = parseCompetitor(fields);
		return new CompetitorAndPerformance(new Performance(competitor.getGroup(), boulderResults), competitor);
	}

	private Sex parseSex(String sex) {
		return "K".equals(sex.toUpperCase().trim()) ? Sex.FEMELE : Sex.MALE;
	}
}
