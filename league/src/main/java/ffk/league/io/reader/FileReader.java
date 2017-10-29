package ffk.league.io.reader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FilenameUtils;

class FileReader {

	private FileReader() {
		// utils
	}

	static Collection<File> getTsvFiles(File directory) {
		File[] allFilesInDirectory = directory.listFiles();
		Collection<File> res = new ArrayList<>();
		for (File file : allFilesInDirectory) {
			if (file.isFile() && "tsv".equals(FilenameUtils.getExtension(file.getName()))) {
				res.add(file);
			}
		}
		return res;
	}

	static File getCurrentDirectory(String season) {
		return new File(System.getProperty("user.dir") + "\\input\\" + season);
	}

}
