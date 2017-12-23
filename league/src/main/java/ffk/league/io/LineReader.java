package ffk.league.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LineReader {

	public List<String> getLines(Path path) {
		try (Stream<String> stream = Files.lines(path)) {
			return stream.collect(Collectors.toList());
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
}
