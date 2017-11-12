package ffk.league.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.jsoup.nodes.Element;

import com.hp.gagawa.java.elements.Td;
import com.hp.gagawa.java.elements.Tr;

import ffk.league.model.competitors.Competitor;
import ffk.league.model.results.input.BoulderResult;
import ffk.league.model.results.input.PerformncesByEdition;
import ffk.league.model.results.input.Results;
import ffk.league.model.results.input.Score;

public class HtmlGenerator {

	private static String createBoulderResultLabel(BoulderResult boulderResult) {
		String top = boulderResult.getTop() == 0 ? "--" : Integer.toString(boulderResult.getTop()) + "t";
		String bonus = boulderResult.getBonus() == 0 ? "--" : Integer.toString(boulderResult.getBonus()) + "b";
		return top + " / " + bonus;
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
		body.appendChild(createTop());
		body.appendChild(createTable(results));
		body.appendElement("script").attr("src", "functions.js");
		return body;
	}

	private Element createTop() {
		Element topDiv = new Element("div").addClass("container");
		topDiv.appendChild(createLogo());
		topDiv.appendChild(createTitle());
		topDiv.appendChild(createCategoryTabs());
		return topDiv;
	}

	private Element createLogo() {
		Element logo = new Element("div").addClass("logo");
		logo.appendElement("img").attr("src", "img/logo_poziom450.png");
		return logo;
	}

	private Element createTitle() {
		Element title = new Element("div").addClass("title-wrapper");
		title.appendElement("div").addClass("title");
		title.appendElement("div").addClass("category").appendElement("h1").text("Chłopcy 9 - 11");
		return title;
	}

	private Element createCategoryTabs() {
		Element tabs = new Element("div").addClass("category-tabs");
		tabs.appendElement("div").text("Chłopcy 9 - 11").attr("onclick", "main.switch(0,'stop');")
				.addClass("categoryButton");
		tabs.appendElement("div").text("Chłopcy 12 - 13").attr("onclick", "main.switch(1,'stop');")
				.addClass("categoryButton");
		tabs.appendElement("div").text("Chłopcy 14 - 15").attr("onclick", "main.switch(2,'stop');")
				.addClass("categoryButton");
		tabs.appendElement("div").text("Dziewczynki 9 - 11").attr("onclick", "main.switch(3,'stop');")
				.addClass("categoryButton");
		tabs.appendElement("div").text("Dziewczynki 12 - 13").attr("onclick", "main.switch(4,'stop');")
				.addClass("categoryButton");
		tabs.appendElement("div").text("Dziewczynki 14 - 15").attr("onclick", "main.switch(5,'stop');")
				.addClass("categoryButton");
		return tabs;
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
		tr.appendElement("th").addClass("medium").text("Suma");
		addEditionColumnHeads(tr, "colorful0");
		return thead;

	}

	private void addEditionColumnHeads(Element parent, String editionClass) {
		String cssColor = "boulder";
		for (int i = 1; i <= 8; ++i) {
			parent.appendChild(createProblemColumnHead(i, editionClass, cssColor));
		}
	}

	private Element createProblemColumnHead(int problemNumber, String editionClass, String cssColor) {
		Element column = new Element("th").addClass("single").addClass("cssColor").addClass(editionClass);
		column.text(Integer.toString(problemNumber));
		return column;
	}

	private Element createTableBody(Results results) {
		Element tbody = new Element("tbody");
		List<String> competitorRows = createCompetitiorRows(results);
		for (String row : competitorRows) {
			tbody.append(row);
		}
		return tbody;
	}

	public List<String> createCompetitiorRows(Results results) {
		List<String> res = new ArrayList<>();
		List<Competitor> competitors = new ArrayList<>(results.getPerformancesMapsByCompetitor().keySet());
		Collections.sort(competitors);

		for (Competitor competitor : competitors) {
			String competition = competitor.getGroup();
			res.add(createCompetitorRow(competitor, results.getPerformancesMapsByCompetitor().get(competitor),
					results.getEditions()));

		}
		return res;
	}

	private static String createCompetitorRow(Competitor competitor, PerformncesByEdition performncesByEdition,
			List<String> editions) {
		Tr tr = new Tr();
		tr.setCSSClass(competitor.getGroup());

		Td positionTd = new Td();
		positionTd.appendText("");
		positionTd.setCSSClass("single");
		tr.appendChild(positionTd);

		Td nameTd = new Td();
		nameTd.appendText(competitor.getName());
		nameTd.setCSSClass("wide");
		tr.appendChild(nameTd);

		Td fullResTd = new Td();
		fullResTd.appendText(createTopAndBonusesLabel(competitor.getScoreAll()));
		fullResTd.setCSSClass("medium");
		tr.appendChild(fullResTd);

		String edition = editions.get(0);
		for (int i = 0; i < 8; ++i) {
			Td boulderTd = new Td();
			if (performncesByEdition.getMap().containsKey(edition)) {
				BoulderResult boulderResult = performncesByEdition.getMap().get(edition).getBoulderResults().get(i);
				boulderTd.appendText(createBoulderResultLabel(boulderResult));
			}
			boulderTd.setCSSClass("colorful0 single");
			tr.appendChild(boulderTd);
		}

		return tr.write() + "\n";
	}

	private static String createTopAndBonusesLabel(Score score) {
		return Integer.toString(score.getTops()) + "t" + Integer.toString(score.getTopsTries()) + " "
				+ Integer.toString(score.getBonuses()) + "b" + Integer.toString(score.getBonusesTries());
	}
}
