package ffk.league;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.services.sheets.v4.Sheets;

import ffk.league.download.InputParser;
import ffk.league.download.SpreadsheetDownloader;
import ffk.league.download.SpreadsheetServiceProvider;
import ffk.league.git.GitCommiter;
import ffk.league.html.HtmlGenerator;
import ffk.league.io.LineReader;
import ffk.league.io.ResultWriter;
import ffk.league.parser.Parser;

@Configuration
@EnableConfigurationProperties(Settings.class)
public class ApplicationConfig {

	@Autowired
	Settings settings;

	@Bean
	public GitCommiter gitCommiter() {
		return new GitCommiter(settings.getGitUsername(), settings.getGitPassword());
	}

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
		return new Parser(settings.isAutoCommit(), settings.getExcludedEditions());
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
