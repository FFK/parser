package ffk.league.io.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ffk.league.input.InputParser;
import ffk.league.model.results.output.CompetitorAndPerformance;
import ffk.league.model.results.output.EditionResults;

public class ResultsReader {

	@Autowired
	LineReader lineReader;
	@Autowired
	InputParser inputParser;

	private String season;

	public ResultsReader(String season) {
		this.season = season;
	}

	private CompetitorAndPerformance parseResultLine(String resultLine) {
		String[] fields = resultLine.split("\t");
		return inputParser.parseResultLine(Arrays.asList(fields));
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
				if (editionPerformance != null) {
					edition.getPerformances().add(editionPerformance);
				}
			}
			res.add(edition);
		}
		return res;
	}
}
