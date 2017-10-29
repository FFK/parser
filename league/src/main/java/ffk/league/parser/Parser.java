package ffk.league.parser;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import ffk.league.html.HtmlGenerator;
import ffk.league.io.reader.ResultsReader;
import ffk.league.io.writer.ResultWriter;
import ffk.league.model.competitors.Competitor;
import ffk.league.model.results.input.Performance;
import ffk.league.model.results.input.PerformncesByEdition;
import ffk.league.model.results.input.Results;
import ffk.league.model.results.input.Score;
import ffk.league.model.results.output.CompetitorAndPerformance;
import ffk.league.model.results.output.EditionResults;

public class Parser {

	@Autowired
	HtmlGenerator htmlGenerator;
	@Autowired
	ResultsReader resultsReader;
	@Autowired
	ResultWriter resultWriter;

	private int excludedEditions;

	public Parser(int excludedProperties) {
		this.excludedEditions = excludedProperties;
	}

	public void parse() {
		List<EditionResults> editionResults = resultsReader.readResultFiles();
		Results results = sumEditionResults(editionResults);
		calculateScores(results);
		resultWriter.createResultFiles(htmlGenerator.createCompetitiorRows(results));

	}

	private void calculateScores(Results results) {
		for (Competitor competitor : results.getPerformancesMapsByCompetitor().keySet()) {
			int allTops = results.getPerformancesMapsByCompetitor().get(competitor).countTops();
			int allBonuses = results.getPerformancesMapsByCompetitor().get(competitor).countBonuses();
			int bestTops = results.getPerformancesMapsByCompetitor().get(competitor)
					.countBestEditionsTops(results.getEditions().size() - excludedEditions);
			int bestBonuses = results.getPerformancesMapsByCompetitor().get(competitor)
					.countBestEditionsBonuses(results.getEditions().size() - excludedEditions);
			competitor.setScoreAll(new Score(allTops, allBonuses));
			competitor.setScoreBest(new Score(bestTops, bestBonuses));
		}
	}

	private static Results sumEditionResults(List<EditionResults> editionResults) {
		Results results = new Results();
		for (EditionResults er : editionResults) {
			String editionName = er.getName();
			results.getEditions().add(editionName);
			for (CompetitorAndPerformance competitorAndPerformance : er.getPerformances()) {
				Map<Competitor, PerformncesByEdition> peformanceMap = results.getPerformancesMapsByCompetitor();
				if (!peformanceMap.containsKey(competitorAndPerformance.getCompetitor())) {
					peformanceMap.put(competitorAndPerformance.getCompetitor(), new PerformncesByEdition());
				}
				Map<String, Performance> performanceByEdition = peformanceMap
						.get(competitorAndPerformance.getCompetitor()).getMap();
				performanceByEdition.put(editionName, competitorAndPerformance.getResults());
			}
		}
		return results;
	}

}
