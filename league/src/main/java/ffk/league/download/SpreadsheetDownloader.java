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

		// 'ŚLB 2017/18, 1 runda Blo'!B2:AA321
		// Prints the names and majors of students in a sample spreadsheet:
		// https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
		// String spreadsheetId = "1UGW47N81kOXISrHylksgVKRgLCbiR_ziUA73aJCe0Bk";
		// String range = "'ŚLB 2017/18, 3. runda-Poziom'!B1:B6";
		// String range = sheetName + "B2:AA321";
		// ValueRange response = service.spreadsheets().values().get(spreadsheetId,
		// range).execute();
		// List<List<Object>> values = response.getValues();
		// if (values == null || values.size() == 0) {
		// LOGGER.warn("No data found.");
		// } else {
		// LOGGER.info("Retrieving data from the spreadsheet: " + spreadsheetId + "
		// range: " + range);
		//
		// for (List row : values) {
		// // Print columns A and E, which correspond to indices 0 and 4.
		// System.out.printf("%s, %s\n", row.get(0), row.get(4));
		// }
		// }
	}

	private List<List<Object>> getSheetData(String spreadsheetId, String sheetName) {
		String range = "'" + sheetName + "'" + "!A2:AA321";
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
