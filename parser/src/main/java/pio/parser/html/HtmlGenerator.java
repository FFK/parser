package pio.parser.html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hp.gagawa.java.elements.Td;
import com.hp.gagawa.java.elements.Tr;

import pio.parser.competitors.Competitor;
import pio.parser.competitors.Group;
import pio.parser.competitors.Sex;
import pio.parser.model.BoulderResult;
import pio.parser.model.Performance;
import pio.parser.model.PerformncesByEdition;
import pio.parser.model.Results;
import pio.parser.model.Score;

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

	public static String createCompetitiorRows(Results results) {
		String res = "";

		List<Competitor> competitors = new ArrayList<Competitor>(results.getPerformancesMapsByCompetitor().keySet());
		Collections.sort(competitors);

		Group group = null;
		Sex sex = null;
		int position = 1;
		for (Competitor competitor : competitors) {
			if (!competitor.getGroup().equals(group) || !competitor.getSex().equals(sex)) {
				position = 1;
				group = competitor.getGroup();
				sex = competitor.getSex();
				res += "<tr style=\"height: 50px;\"></tr>";
			}
			res += createCompetitorRow(competitor, results.getPerformancesMapsByCompetitor().get(competitor),
					results.getEditions(), position);
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

		Td categoryTd = new Td();
		categoryTd.appendText(getCategoryLabel(competitor));
		categoryTd.setCSSClass("wide");
		tr.appendChild(categoryTd);

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
				boulderTd.setCSSClass("colorful" + Integer.toString(editionNo) + " " + creteBoulderClass(i) + " single");
				boulderTd.setStyle("display:none");
				tr.appendChild(boulderTd);
			}

			editionNo++;
		}
		return tr.write();
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

	private static String getCategoryLabel(Competitor competitor) {
		String sexLabel = competitor.getSex() == Sex.FEMELE ? "dzio³chy" : "chopy";
		switch (competitor.getGroup()) {
		case EASY:
			return "Lajtowa - " + sexLabel;
		case HARD:
			return "Trudna - " + sexLabel;
		case PRO:
			return "Pro - " + sexLabel;
		case JUNIOR:
			return "Bajtle - " + sexLabel;
		case VETERAN:
			return "Weteran - " + sexLabel;
		}
		throw new IllegalArgumentException();
	}

}
