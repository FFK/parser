package pio.parser.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pio.parser.Parser;
import pio.parser.results.Competition;

public class ResultWriter {

	public static void createResultFile(Competition competition, String rows) {
		ClassLoader classLoader = Parser.class.getClassLoader();
		File topContentFile = new File(classLoader.getResource("TopContent.html").getFile());
		File tableHeaderFile = new File(classLoader.getResource("TableHeader.html").getFile());
		File tableFooterFile = new File(classLoader.getResource("TableFooter.html").getFile());
		File bottomContentFile = new File(classLoader.getResource("BottomContent.html").getFile());

		List<String> lines = new ArrayList<>();
		lines.addAll(LineReader.getLines(topContentFile.toPath()));
		lines.add(getMenuLine(competition));
		lines.addAll(LineReader.getLines(tableHeaderFile.toPath()));
		lines.add(rows);
		lines.addAll(LineReader.getLines(tableFooterFile.toPath()));
		lines.addAll(LineReader.getLines(bottomContentFile.toPath()));
		Path file = Paths.get("output/"+ Parser.SEASON + "/" + competition.name() + ".html");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
			if (competition == Competition.PRO_MEN) {
				Files.write(Paths.get("output/" + Parser.SEASON + "/Ranking.html"), lines, Charset.forName("UTF-8"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createResultFiles(Map<Competition, String> map) {
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
			return "Dzio³chy Trudna";
		case EASY_WOMEN:
			return "Dzio³chy Lajt";
		case JUNIOR_MEN:
			return "Bajtle";
		case JUNIOR_WOMEN:
			return "Dzio³szki";
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
			stringBuilder.append("</a>");
		}
		stringBuilder.append("<hr />");
		return stringBuilder.toString();
	}

}
