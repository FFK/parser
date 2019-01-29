package ffk.league.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import ffk.league.model.results.output.Competition;
import ffk.league.parser.Parser;

public class ResultWriter {

	@Autowired
	LineReader lineReader;

	private String season;

	public ResultWriter(String season) {
		this.season = season;
	}

	public void writeFile(String name, List<String> content) {
		Path file = Paths.get("output/" + season + "/" + name);
		try {
			Files.write(file, content, Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private void createResultFile(Competition competition, String rows) {
		ClassLoader classLoader = Parser.class.getClassLoader();
		File topContentFile = new File(classLoader.getResource(season + "/TopContent.html").getFile());
		File tableHeaderFile = new File(classLoader.getResource(season + "/TableHeader.html").getFile());
		File tableFooterFile = new File(classLoader.getResource(season + "/TableFooter.html").getFile());
		File bottomContentFile = new File(classLoader.getResource(season + "/BottomContent.html").getFile());

		List<String> lines = new ArrayList<>();
		lines.add(getMenuLine(competition));
		lines.addAll(lineReader.getLines(tableHeaderFile.toPath()));
		lines.addAll(lineReader.getLines(topContentFile.toPath()));
		lines.add(rows);
		lines.addAll(lineReader.getLines(tableFooterFile.toPath()));
		lines.addAll(lineReader.getLines(bottomContentFile.toPath()));

		writeFile(competition.name() + ".html", lines);
		if (competition == Competition.PRO_MEN) {
			writeFile("Ranking.html", lines);
		}

	}

	public void createResultFiles(Map<Competition, String> map) {
		for (Competition competition : Competition.values()) {
			createResultFile(competition, map.get(competition));
		}
	}

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

	private static String getMenuLine(Competition competition) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Competition c : Competition.values()) {
			stringBuilder.append("<a href=\"");
			stringBuilder.append(c.name());
			stringBuilder.append(".html\" class=\"tab");
			stringBuilder.append(c == competition ? " selected" : "");
			stringBuilder.append("\">");
			stringBuilder.append(getMenuLabel(c));
			stringBuilder.append("</a>\n");
		}
		stringBuilder.append("<button class=\"go\" onclick=\"go();\">go</button>");
		stringBuilder.append("<hr />");
		return stringBuilder.toString();
	}

}
