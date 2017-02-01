package pio.parser.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pio.parser.competitors.Competitor;
import pio.parser.competitors.CompetitorsFactory;
import pio.parser.competitors.Group;
import pio.parser.competitors.Sex;
import pio.parser.model.BoulderResult;
import pio.parser.model.Performance;
import pio.parser.results.CompetitorAndPerformance;
import pio.parser.results.EditionResults;

public class ResultsReader {

	private static BoulderResult parseBoulderResult(String result) {
		switch (result.toUpperCase().trim()) {
		case "T":
			return BoulderResult.TOP;
		case "B":
			return BoulderResult.BONUS;
		default:
			return BoulderResult.ZERO;
		}
	}

	private static List<BoulderResult> parseBoulderResults(String[] fields) {
		List<BoulderResult> boulders = new ArrayList<>();
		final int shift = 3;
		for (int i = 0; i < 20; ++i) {
			boulders.add(parseBoulderResult(fields[shift + i]));
		}
		return boulders;
	}

	private static Competitor parseCompetitor(String[] fields) {
		return CompetitorsFactory.getOrCreateCompetitor(fields[2], parseGroup(fields[23]), fields[1],
				parseSex(fields[24]));
	}

	private static Group parseGroup(String group) {
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

	private static CompetitorAndPerformance parseResultLine(String resultLine) {
		String[] fields = resultLine.split("\t");
		List<BoulderResult> boulderResults = parseBoulderResults(fields);
		Competitor competitor = parseCompetitor(fields);
		return new CompetitorAndPerformance(new Performance(competitor.getGroup(), boulderResults), competitor);
	}

	private static Sex parseSex(String sex) {
		return "K".equals(sex.toUpperCase().trim()) ? Sex.FEMELE : Sex.MALE;
	}

	public static List<EditionResults> readResultFiles() {
		List<EditionResults> res = new ArrayList<>();
		Collection<File> editionResultFiles = FileReader.getTsvFiles(FileReader.getCurrentDirectory());
		for (File file : editionResultFiles) {
			List<String> resultLines = LineReader.getLines(file.toPath());
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
