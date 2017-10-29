package ffk.league;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "parser")
public class Settings {

	private int excludedEditions;
	private String season;

	public int getExcludedEditions() {
		return excludedEditions;
	}

	public void setExcludedEditions(int excludedEditions) {
		this.excludedEditions = excludedEditions;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

}
