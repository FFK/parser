package ffk.league.model.results.input;

public class Score implements Comparable<Score> {

	private int bonuses;
	private int bonusesTries;
	private int tops;
	private int topsTries;

	public Score(int bonuses, int bonusesTries, int tops, int topsTries) {
		super();
		this.bonuses = bonuses;
		this.bonusesTries = bonusesTries;
		this.tops = tops;
		this.topsTries = topsTries;
	}

	@Override
	public int compareTo(Score o) {
		if (tops != o.tops) {
			return Integer.compare(tops, o.tops);
		}
		if (bonuses != o.bonuses) {
			return Integer.compare(bonuses, o.bonuses);
		}
		if (topsTries != o.topsTries) {
			return Integer.compare(topsTries, o.topsTries);
		}
		return Integer.compare(bonusesTries, o.bonusesTries);
	}

	public int getBonuses() {
		return bonuses;
	}

	public int getBonusesTries() {
		return bonusesTries;
	}

	public int getTops() {
		return tops;
	}

	public int getTopsTries() {
		return topsTries;
	}

}
