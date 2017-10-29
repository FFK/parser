package ffk.league.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.hp.gagawa.java.elements.Td;
import com.hp.gagawa.java.elements.Tr;

import ffk.league.model.competitors.Competitor;
import ffk.league.model.competitors.Group;
import ffk.league.model.competitors.Sex;
import ffk.league.model.results.input.BoulderResult;
import ffk.league.model.results.input.Performance;
import ffk.league.model.results.input.PerformncesByEdition;
import ffk.league.model.results.input.Results;
import ffk.league.model.results.input.Score;
import ffk.league.model.results.output.Competition;

public class HtmlGenerator {

	private static String createBoulderResultLabel(BoulderResult boulderResult) {
		switch (boulderResult) {
		case TOP:
			return "T";
		case BONUS:
			return "B";
		default:
			return "";
		}
	}

	public Map<Competition, String> createCompetitiorRows(Results results) {
		Map<Competition, String> res = new EnumMap<>(Competition.class);

		List<Competitor> competitors = new ArrayList<>(results.getPerformancesMapsByCompetitor().keySet());
		Collections.sort(competitors);

		Group group = null;
		Sex sex = null;
		int position = 1;
		for (Competitor competitor : competitors) {
			if (!competitor.getGroup().equals(group) || !competitor.getSex().equals(sex)) {
				position = 1;
				group = competitor.getGroup();
				sex = competitor.getSex();
			}
			Competition competition = Competition.getCompetition(competitor.getGroup(), competitor.getSex());
			if (!res.containsKey(competition)) {
				res.put(competition, "");
			}
			res.put(competition, res.get(competition) + createCompetitorRow(competitor,
					results.getPerformancesMapsByCompetitor().get(competitor), results.getEditions(), position));
			position++;

		}
		return res;
	}

	private static String createCompetitorRow(Competitor competitor, PerformncesByEdition performncesByEdition,
			List<String> editions, int position) {
		Tr tr = new Tr();

		Td positionTd = new Td();
		positionTd.appendText(Integer.toString(position));
		positionTd.setCSSClass("single");
		tr.appendChild(positionTd);

		Td nameTd = new Td();
		nameTd.appendText(competitor.getName());
		nameTd.setCSSClass("wide");
		tr.appendChild(nameTd);

		Td clubTd = new Td();
		clubTd.appendText(competitor.getClub());
		clubTd.setCSSClass("wide");
		tr.appendChild(clubTd);

		Td pickResTd = new Td();
		pickResTd.appendText(createTopAndBonusesLabel(competitor.getScoreBest()));
		pickResTd.setCSSClass("medium");
		tr.appendChild(pickResTd);

		Td fullResTd = new Td();
		fullResTd.appendText(createTopAndBonusesLabel(competitor.getScoreAll()));
		fullResTd.setCSSClass("medium");
		tr.appendChild(fullResTd);

		int editionNo = 0;
		for (String edition : editions) {
			Td sumTd = new Td();
			sumTd.setCSSClass("colorful" + Integer.toString(editionNo) + " tight");
			if (performncesByEdition.getMap().containsKey(edition)) {
				Performance performance = performncesByEdition.getMap().get(edition);
				sumTd.appendText(createTopAndBonusesLabel(performance.countScore()));
			}
			tr.appendChild(sumTd);
			for (int i = 0; i < 20; ++i) {
				Td boulderTd = new Td();
				if (performncesByEdition.getMap().containsKey(edition)) {
					BoulderResult boulderResult = performncesByEdition.getMap().get(edition).getBoulderResults().get(i);
					boulderTd.appendText(createBoulderResultLabel(boulderResult));
				}
				boulderTd
						.setCSSClass("colorful" + Integer.toString(editionNo) + " " + creteBoulderClass(i) + " single");
				boulderTd.setStyle("display:none");
				tr.appendChild(boulderTd);
			}

			editionNo++;
		}
		return tr.write() + "\n";
	}

	private static String createTopAndBonusesLabel(Score score) {
		return Integer.toString(score.getTops()) + '(' + Integer.toString(score.getBonuses()) + ')';
	}

	private static String creteBoulderClass(int number) {
		if (number < 5) {
			return "yellow";
		} else if (number < 10) {
			return "blue";
		} else if (number < 15) {
			return "red";
		}
		return "black";
	}

}
