package pio.parser.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.FilenameUtils;

public class FileReader {
	
	public static Collection<File> getTsvFiles(File directory) {
		File[] allFilesInDirectory = directory.listFiles();
		Collection<File> res = new ArrayList<>();
		for (File file : allFilesInDirectory) {
			if (file.isFile() && "tsv".equals(FilenameUtils.getExtension(file.getName()))) {
				res.add(file);
			}
		}
		return res;
	}
	
	public static File getCurrentDirectory() {
		return new File(System.getProperty("user.dir") + "\\input");
	}

}
