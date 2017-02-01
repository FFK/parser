package pio.parser;

import java.util.List;
import java.util.Map;

import pio.parser.competitors.Competitor;
import pio.parser.html.HtmlGenerator;
import pio.parser.io.ResultWriter;
import pio.parser.io.ResultsReader;
import pio.parser.model.Performance;
import pio.parser.model.PerformncesByEdition;
import pio.parser.model.Results;
import pio.parser.model.Score;
import pio.parser.results.CompetitorAndPerformance;
import pio.parser.results.EditionResults;

public class Parser {
	private static void calculateScores(Results results) {
		for (Competitor competitor : results.getPerformancesMapsByCompetitor().keySet()) {
			int allTops = results.getPerformancesMapsByCompetitor().get(competitor).countTops();
			int allBonuses = results.getPerformancesMapsByCompetitor().get(competitor).countBonuses();
			int bestTops = results.getPerformancesMapsByCompetitor().get(competitor)
					.countBestEditionsTops(results.getEditions().size() - 2);
			int bestBonuses = results.getPerformancesMapsByCompetitor().get(competitor)
					.countBestEditionsBonuses(results.getEditions().size() - 2);
			competitor.setScoreAll(new Score(allTops, allBonuses));
			competitor.setScoreBest(new Score(bestTops, bestBonuses));
		}
	};

	public static void main(String[] args) {
		List<EditionResults> editionResults = ResultsReader.readResultFiles();
		Results results = sumEditionResults(editionResults);
		calculateScores(results);
		ResultWriter.createResultFile(HtmlGenerator.createCompetitiorRows(results));
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
