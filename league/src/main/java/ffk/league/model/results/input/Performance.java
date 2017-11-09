package ffk.league.model.results.input;

import java.util.List;

public class Performance implements Comparable<Performance> {

	List<BoulderResult> boulderResults;
	String group;

	public Performance(String group, List<BoulderResult> boulderResults) {
		super();
		this.group = group;
		this.boulderResults = boulderResults;
	}

	@Override
	public int compareTo(Performance o) {
		return countScore().compareTo(o.countScore());
	}

	public Score countScore() {
		int tops = (int) boulderResults.stream().filter(r -> r.getTop() > 0).count();
		int bonuses = (int) boulderResults.stream().filter(r -> r.getBonus() > 0).count();
		int topsTries = boulderResults.stream().mapToInt(r -> r.getTop()).sum();
		int bonusesTries = boulderResults.stream().mapToInt(r -> r.getBonus()).sum();
		return new Score(bonuses, bonusesTries, tops, topsTries);
	}

	public List<BoulderResult> getBoulderResults() {
		return boulderResults;
	}

}
