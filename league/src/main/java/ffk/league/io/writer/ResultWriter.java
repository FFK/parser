package ffk.league.io.writer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ffk.league.io.reader.LineReader;

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

}
