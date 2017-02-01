package pio.parser.io;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import pio.parser.Parser;

public class ResultWriter {

	public static void createResultFile(String resultRows) {
		ClassLoader classLoader = Parser.class.getClassLoader();
		File topContentFile = new File(classLoader.getResource("TopContent.html").getFile());
		File tableHeaderFile = new File(classLoader.getResource("TableHeader.html").getFile());
		File tableFooterFile = new File(classLoader.getResource("TableFooter.html").getFile());
		File bottomContentFile = new File(classLoader.getResource("BottomContent.html").getFile());
		
		List<String> lines = new ArrayList<String>();
		lines.addAll(LineReader.getLines(topContentFile.toPath()));
		lines.addAll(LineReader.getLines(tableHeaderFile.toPath()));
		lines.add(resultRows);
		lines.addAll(LineReader.getLines(tableFooterFile.toPath()));
		lines.addAll(LineReader.getLines(bottomContentFile.toPath()));
		Path file = Paths.get("output/Ranking.html");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
