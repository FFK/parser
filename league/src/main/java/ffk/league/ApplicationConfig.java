package ffk.league;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.services.sheets.v4.Sheets;

import ffk.league.download.SpreadsheetDownloader;
import ffk.league.download.SpreadsheetServiceProvider;
import ffk.league.html.HtmlGenerator;
import ffk.league.input.InputParser;
import ffk.league.io.reader.LineReader;
import ffk.league.io.reader.ResultsReader;
import ffk.league.io.writer.ResultWriter;
import ffk.league.parser.Parser;

@Configuration
@EnableConfigurationProperties(Settings.class)
public class ApplicationConfig {

	@Autowired
	Settings settings;

	@Bean
	public HtmlGenerator htmlGenerator() {
		return new HtmlGenerator();
	}

	@Bean
	public InputParser inputParser() {
		return new InputParser();
	}

	@Bean
	public LineReader lineReader() {
		return new LineReader();
	}

	@Bean
	public Parser parser() {
		return new Parser(settings.getExcludedEditions());
	}

	@Bean
	public ResultsReader resultsReader() {
		return new ResultsReader(settings.getSeason());
	}

	@Bean
	ResultWriter resultWriter() {
		return new ResultWriter(settings.getSeason());
	}

	@Bean
	Sheets sheetsService() {
		return SpreadsheetServiceProvider.getSheetsService();
	}

	@Bean
	SpreadsheetDownloader spreadsheetDownloader() {
		return new SpreadsheetDownloader();
	}

}
