package ffk.league.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

public class SpreadsheetServiceProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpreadsheetServiceProvider.class);

	/** Directory to store user credentials for this application. */
	private static final File DATA_STORE_DIR = new File(System.getProperty("user.dir"), ".credentials/ffk.league");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory dataStoreFactory;

	/** Global instance of the JSON factory. */
	private static JsonFactory jsonFactory;

	/** Global instance of the HTTP transport. */
	private static HttpTransport httpTransport;

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/sheets.googleapis.com-java-quickstart
	 */
	private static final List<String> SCOPES = Arrays.asList(SheetsScopes.SPREADSHEETS_READONLY);

	static {

		try {
			jsonFactory = JacksonFactory.getDefaultInstance();
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (GeneralSecurityException | IOException e) {
			throw new SecurityException(e);
		}

	}

	private SpreadsheetServiceProvider() {
		// prevents creating an instance
	}

	private static Credential authorize() {
		// Load client secrets.
		InputStream in = SpreadsheetDownloader.class.getResourceAsStream("/client_id.json");
		GoogleClientSecrets clientSecrets;
		try {
			clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));
			// Build flow and trigger user authorization request.
			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory,
					clientSecrets, SCOPES).setDataStoreFactory(dataStoreFactory).setAccessType("offline").build();
			Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
					.authorize("user");
			LOGGER.info("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
			return credential;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

	}

	/**
	 * Build and return an authorized Sheets API client service.
	 * 
	 * @return an authorized Sheets API client service
	 * @throws IOException
	 */
	public static Sheets getSheetsService() {
		Credential credential = authorize();
		return new Sheets.Builder(httpTransport, jsonFactory, credential).setApplicationName("FFK league").build();
	}
}
