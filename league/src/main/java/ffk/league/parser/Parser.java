package ffk.league.parser;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ffk.league.download.SpreadsheetDownloader;
import ffk.league.git.GitCommiter;
import ffk.league.html.HtmlGenerator;
import ffk.league.io.writer.ResultWriter;
import ffk.league.model.competitors.Competitor;
import ffk.league.model.results.input.Performance;
import ffk.league.model.results.input.PerformncesByEdition;
import ffk.league.model.results.input.Results;
import ffk.league.model.results.output.CompetitorAndPerformance;
import ffk.league.model.results.output.EditionResults;

public class Parser {

	private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);

	@Autowired
	private GitCommiter gitCommiter;
	@Autowired
	private HtmlGenerator htmlGenerator;
	@Autowired
	private SpreadsheetDownloader spreadsheetDownloader;
	@Autowired
	private ResultWriter resultWriter;

	private boolean autoCommit;
	private int excludedEditions;

	public Parser(boolean autoCommit, int excludedProperties) {
		this.autoCommit = autoCommit;
		this.excludedEditions = excludedProperties;
	}

	public void parse() {
		LOGGER.info("...............");
		LOGGER.info("Parsing started");
		LOGGER.info("...............");
		LOGGER.info("Retrieving data started");
		List<EditionResults> editionResults = spreadsheetDownloader.downloadAllEditions();
		LOGGER.info("Retrieving ended succesfully");
		LOGGER.info("Geathering results started");
		Results results = sumEditionResults(editionResults);
		LOGGER.info("Geathering results ended succesfully");
		LOGGER.info("Calculating scores started");
		calculateScores(results);
		LOGGER.info("Calculating scores ended succesfully");
		LOGGER.info("Writing files started");
		resultWriter.writeFile("TopHunters.html", htmlGenerator.generate(results));
		LOGGER.info("Writing files ended succesfully");
		if (autoCommit) {
			LOGGER.info("Persisiting started");
			if (gitCommiter.hasChanges()) {
				LOGGER.info("Changes detected, commiting and pushing started");
				gitCommiter.push();
				LOGGER.info("Commiting and pushing ended succesfully");
			} else {
				LOGGER.info("No changes detected");
			}
			LOGGER.info("Persisiting ended succesfully");
		}
		LOGGER.info(".........................");
		LOGGER.info("Parsing ended succesfully");
		LOGGER.info(".........................");
	}

	private void calculateScores(Results results) {
		for (Competitor competitor : results.getPerformancesMapsByCompetitor().keySet()) {
			competitor.setScoreAll(
					results.getPerformancesMapsByCompetitor().get(competitor).getMap().get("0").countScore());
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
