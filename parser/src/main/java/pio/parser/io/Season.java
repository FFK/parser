package pio.parser.io;

public enum Season {

	SEASON_16_17("16-17"), SEASON_17_18("17-18");

	private String folderName;

	private Season(final String folderName) {
		this.folderName = folderName;
	}

	@Override
	public String toString() {
		return folderName;
	}
}
