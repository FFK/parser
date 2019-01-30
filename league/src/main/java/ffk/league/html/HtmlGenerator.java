package ffk.league.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Element;

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

	public List<String> generate(Results results) {
		return Arrays.asList(createHtml(results).toString().split("\\r?\\n"));
	}

	public Element createHtml(Results results) {
		Element html = new Element("html");
		html.appendChild(createHead());
		html.appendChild(createBody(results));
		return html;
	}

	private Element createHead() {
		Element head = new Element("head");
		head.appendChild(new Element("link").attr("rel", "icon").attr("href", "SLB.jpg"));
		head.appendChild(
				new Element("link").attr("rel", "stylesheet").attr("type", "text/css").attr("href", "styles.css"));
		head.appendChild(new Element("meta").attr("charset", "UTF-8"));
		return head;
	}

	private Element createBody(Results results) {
		Element body = new Element("body");
		body.appendChild(createSearch());
		body.appendChild(createTabs());
		body.appendChild(createTable(results));
		body.appendElement("script").attr("src", "functions.js");
		return body;
	}

	private Element createSearch() {
		Element search = new Element("div");
		search.appendElement("span").text("wciep tekst");
		search.appendElement("input").attr("type", "text");
		return search;
	}

	private Element createTabs() {
		Element tabs = new Element("span");
		tabs.appendChild(createTab(Competition.PRO_MEN).addClass("selected"));
		tabs.appendChild(createTab(Competition.HARD_MEN));
		tabs.appendChild(createTab(Competition.EASY_MEN));
		tabs.appendChild(createTab(Competition.HARD_WOMEN));
		tabs.appendChild(createTab(Competition.EASY_WOMEN));
		tabs.appendChild(createTab(Competition.JUNIOR_MEN));
		tabs.appendChild(createTab(Competition.JUNIOR_WOMEN));
		tabs.appendChild(createTab(Competition.VETERAN_MEN));
		tabs.appendChild(createTab(Competition.VETERAN_WOMEN));
		tabs.appendElement("button").addClass("go").text("link do nowe strony wyników (w budowie)").attr("onclick", "javascript:window.location.href='https://climbing-competitions.appspot.com/scoreCard'");
		tabs.appendElement("button").addClass("go").text("go").attr("onclick", "go();");
		tabs.appendElement("hr");
		return tabs;
	}

	private Element createTab(Competition competition) {
		return new Element("a").addClass("tab").text(getMenuLabel(competition)).attr("onclick",
				String.format("switchTab('%s');", competition.name()));

	}

	// TODO move to props
	private static String getMenuLabel(Competition competition) {
		switch (competition) {
		case PRO_MEN:
			return "Chopy Pro";
		case HARD_MEN:
			return "Chopy Trudna";
		case EASY_MEN:
			return "Chopy Lajt";
		case HARD_WOMEN:
			return "Dziołchy Trudna";
		case EASY_WOMEN:
			return "Dziołchy Lajt";
		case JUNIOR_MEN:
			return "Bajtle";
		case JUNIOR_WOMEN:
			return "Dziołszki";
		case VETERAN_MEN:
			return "Weterani";
		case VETERAN_WOMEN:
			return "Weteranki";
		default:
			return null;
		}
	}

	private Element createTable(Results results) {
		Element table = new Element("table");
		table.appendChild(createTableHead());
		table.appendChild(createTableBody(results));
		return table;
	}

	private Element createTableHead() {
		Element thead = new Element("thead");
		Element tr = new Element("tr");
		thead.appendChild(tr);
		tr.appendElement("th").addClass("single");
		tr.appendElement("th").addClass("wide").text("Zawodnik");
		tr.appendElement("th").addClass("wide").text("Klub");
		tr.appendElement("th").addClass("medium").text("Bez trzech rund");
		tr.appendElement("th").addClass("medium").text("Wszystkie rundy");
		addEditionColumnHeads(tr, "07.10", "BLO", "BLOKatowice", "colorful0");
		addEditionColumnHeads(tr, "21.10", "SKARPA", "Skarpa Bytom", "colorful1");
		addEditionColumnHeads(tr, "04.11", "POZIOM", "Poziom450, Sosnowiec", "colorful2");
		addEditionColumnHeads(tr, "18.11", "SALEWA", "Salewa Blok, Zabrze", "colorful3");
		addEditionColumnHeads(tr, "02.12", "TRAFO", "CW Transformator, Katowice", "colorful4");
		addEditionColumnHeads(tr, "16.12", "TOTEM", "CW Totem, Bielsko-Biala", "colorful5");
		addEditionColumnHeads(tr, "06.01", "BLO", "BLOKatowice", "colorful6");
		addEditionColumnHeads(tr, "20.01", "SALEWA", "Salewa Blok, Zabrze", "colorful7");
		addEditionColumnHeads(tr, "03.02", "SKARPA", "Skarpa Bytom", "colorful8");
		addEditionColumnHeads(tr, "17.02", "POZIOM", "Poziom450, Sosnowiec", "colorful9");
		addEditionColumnHeads(tr, "03.03", "PROGRES", "Progres, Siemianowice Sl.", "colorful10");
		addEditionColumnHeads(tr, "02.12", "TRAFO", "CW Transformator, Katowice", "colorful11");
		return thead;

	}

	private void addEditionColumnHeads(Element parent, String text, String subtext, String title, String editionClass) {
		parent.appendChild(createEditionColumnHead(text, subtext, title, editionClass));
		String cssColor = "yellow";
		for (int i = 1; i <= 20; ++i) {
			parent.appendChild(createProblemColumnHead(i, editionClass, cssColor));
			cssColor = i == 5 ? "blue" : cssColor;
			cssColor = i == 10 ? "red" : cssColor;
			cssColor = i == 15 ? "black" : cssColor;
		}
	}

	private Element createEditionColumnHead(String text, String subText, String title, String editionClass) {
		Element column = new Element("th").addClass("tight").addClass("pointer").addClass(editionClass);
		column.attr("title", title);
		column.attr("onclick", String.format("toggle('%s');", editionClass));
		column.text(text + "\n");
		column.appendElement("span").text(subText).addClass("subtitle");
		return column;
	}

	private Element createProblemColumnHead(int problemNumber, String editionClass, String cssColor) {
		Element column = new Element("th").addClass("single").addClass("cssColor").addClass(editionClass);
		column.text(Integer.toString(problemNumber));
		column.attr("onclick", String.format("toggle('%s');", editionClass));
		column.attr("style", "display: none");
		return column;
	}

	private Element createTableBody(Results results) {
		Element tbody = new Element("tbody");
		Map<Competition, String> competitorRows = createCompetitiorRows(results);
		for (Competition competition : Competition.values()) {
			tbody.append(competitorRows.get(competition));
		}
		return tbody;
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
		tr.setCSSClass(Competition.getCompetition(competitor.getGroup(), competitor.getSex()).name());

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
