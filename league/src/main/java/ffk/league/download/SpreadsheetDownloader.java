package ffk.league.download;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;

import ffk.league.input.InputParser;
import ffk.league.model.results.output.CompetitorAndPerformance;
import ffk.league.model.results.output.EditionResults;

public class SpreadsheetDownloader {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpreadsheetDownloader.class);

	@Autowired
	private Sheets sheets;
	@Autowired
	private InputParser inputParser;

	@Value("#{'${spreadsheetIds}'.split(';')}")
	private List<String> spreadsheetIds;
	@Value("#{'${sheetNames}'.split(';')}")
	private List<String> sheetNames;

	public List<EditionResults> downloadAllEditions() {
		List<EditionResults> res = new ArrayList<>();
		for (int i = 0; i < spreadsheetIds.size(); ++i) {
			res.add(downloadEdition(Integer.toString(i), spreadsheetIds.get(i), sheetNames.get(i)));
		}
		return res;
	}

	public EditionResults downloadEdition(String name, String spreadsheetId, String sheetName) {

		List<List<Object>> sheetData = getSheetData(spreadsheetId, sheetName);
		EditionResults editionResults = new EditionResults(name);
		editionResults.getPerformances().addAll(parseSheetData(sheetData));
		return editionResults;
	}

	private List<List<Object>> getSheetData(String spreadsheetId, String sheetName) {
		String range = "'" + sheetName + "'" + "!A3:R1000";
		ValueRange response;
		try {
			response = sheets.spreadsheets().values().get(spreadsheetId, range).execute();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		List<List<Object>> sheetData = response.getValues();
		if (CollectionUtils.isEmpty(sheetData)) {
			LOGGER.warn(String.format("No data found in the spreadsheet: %s range: %s", spreadsheetId, range));
			return Lists.emptyList();
		} else {
			LOGGER.info(String.format("Retrieving data from the spreadsheet: %s range: %s", spreadsheetId, range));
			return sheetData;
		}
	}

	private Collection<CompetitorAndPerformance> parseSheetData(List<List<Object>> sheetData) {
		Collection<CompetitorAndPerformance> res = new ArrayList<>();
		for (List<Object> sheetDataRow : sheetData) {
			CompetitorAndPerformance competitorAndPerformance = parseSheetDataRow(sheetDataRow);
			if (competitorAndPerformance == null) {
				break;
			}
			res.add(competitorAndPerformance);
		}
		return res;
	}

	private CompetitorAndPerformance parseSheetDataRow(List<Object> sheetDataRow) {
		List<String> fields = sheetDataRow.stream().map(Object::toString).collect(Collectors.toList());
		return inputParser.parseResultLine(fields);
	}

}
