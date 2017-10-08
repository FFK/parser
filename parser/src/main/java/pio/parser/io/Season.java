package pio.parser.io;

public enum Season {

	SEASON_16_17("16-17", 2), SEASON_17_18("17-18", 0);

	private int excludedEditions;
	private String folderName;

	private Season(final String folderName, final int excludedEditions) {
		this.excludedEditions = excludedEditions;
		this.folderName = folderName;
	}

	@Override
	public String toString() {
		return folderName;
	}

	public int getExcludedEditions() {
		return excludedEditions;
	}
}
