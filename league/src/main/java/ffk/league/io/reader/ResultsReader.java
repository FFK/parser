package ffk.league.io.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ffk.league.model.competitors.Competitor;
import ffk.league.model.competitors.CompetitorsFactory;
import ffk.league.model.competitors.Group;
import ffk.league.model.competitors.Sex;
import ffk.league.model.results.input.BoulderResult;
import ffk.league.model.results.input.Performance;
import ffk.league.model.results.output.CompetitorAndPerformance;
import ffk.league.model.results.output.EditionResults;

public class ResultsReader {

	@Autowired
	LineReader lineReader;

	private String season;

	public ResultsReader(String season) {
		this.season = season;
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

	private List<BoulderResult> parseBoulderResults(String[] fields) {
		List<BoulderResult> boulders = new ArrayList<>();
		final int shift = 3;
		for (int i = 0; i < 20; ++i) {
			boulders.add(parseBoulderResult(fields[shift + i]));
		}
		return boulders;
	}

	private Competitor parseCompetitor(String[] fields) {
		return CompetitorsFactory.getOrCreateCompetitor(fields[2], parseGroup(fields[23]), fields[1],
				parseSex(fields[24]));
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
		}
		return Group.EASY;
	}

	private CompetitorAndPerformance parseResultLine(String resultLine) {
		String[] fields = resultLine.split("\t");
		List<BoulderResult> boulderResults = parseBoulderResults(fields);
		Competitor competitor = parseCompetitor(fields);
		return new CompetitorAndPerformance(new Performance(competitor.getGroup(), boulderResults), competitor);
	}

	private Sex parseSex(String sex) {
		return "K".equals(sex.toUpperCase().trim()) ? Sex.FEMELE : Sex.MALE;
	}

	public List<EditionResults> readResultFiles() {
		List<EditionResults> res = new ArrayList<>();
		Collection<File> editionResultFiles = FileReader.getTsvFiles(FileReader.getCurrentDirectory(season));
		for (File file : editionResultFiles) {
			List<String> resultLines = lineReader.getLines(file.toPath());
			// cut the header
			resultLines = resultLines.subList(1, resultLines.size());
			EditionResults edition = new EditionResults(file.getName());
			for (String line : resultLines) {
				CompetitorAndPerformance editionPerformance = parseResultLine(line);
				edition.getPerformances().add(editionPerformance);
			}
			res.add(edition);
		}
		return res;
	}
}
