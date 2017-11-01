package ffk.league;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "league")
public class Settings {

	private int excludedEditions;
	private String gitPassword;
	private String gitUsername;
	private String season;

	public int getExcludedEditions() {
		return excludedEditions;
	}

	public void setExcludedEditions(int excludedEditions) {
		this.excludedEditions = excludedEditions;
	}

	public String getGitPassword() {
		return gitPassword;
	}

	public void setGitPassword(String gitPassword) {
		this.gitPassword = gitPassword;
	}

	public String getGitUsername() {
		return gitUsername;
	}

	public void setGitUsername(String gitUsername) {
		this.gitUsername = gitUsername;
	}

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

}
