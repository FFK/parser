package ffk.league.model.results.input;

public class Score implements Comparable<Score> {

	private int bonuses;
	private int tops;

	public Score(int tops, int bonuses) {
		super();
		this.tops = tops;
		this.bonuses = bonuses;
	}

	@Override
	public int compareTo(Score o) {
		return tops != o.tops ? Integer.compare(tops, o.tops) : Integer.compare(bonuses, o.bonuses);
	}

	public int getBonuses() {
		return bonuses;
	}

	public int getTops() {
		return tops;
	}

}
