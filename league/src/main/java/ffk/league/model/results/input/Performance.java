package ffk.league.model.results.input;

import java.util.Collections;
import java.util.List;

import ffk.league.model.competitors.Group;

public class Performance implements Comparable<Performance> {

	List<BoulderResult> boulderResults;
	Group group;

	public Performance(Group group, List<BoulderResult> boulderResults) {
		super();
		this.group = group;
		this.boulderResults = boulderResults;
	}

	@Override
	public int compareTo(Performance o) {
		return countScore().compareTo(o.countScore());
	}

	public Score countScore() {
		int tops, bonuses;
		switch (group) {
		case PRO:
			tops = Collections.frequency(boulderResults.subList(10, 20), BoulderResult.TOP);
			bonuses = Collections.frequency(boulderResults.subList(10, 20), BoulderResult.BONUS);
			break;
		case HARD:
			tops = Collections.frequency(boulderResults.subList(5, 15), BoulderResult.TOP);
			bonuses = Collections.frequency(boulderResults.subList(5, 15), BoulderResult.BONUS);
			if (tops == 10 && 0 < Collections.frequency(boulderResults.subList(15, 20), BoulderResult.TOP)) {
				++tops;
			} else if (tops == 10 && 0 < Collections.frequency(boulderResults.subList(15, 20), BoulderResult.BONUS)) {
				++bonuses;
			}
			break;
		case EASY:
			tops = Collections.frequency(boulderResults.subList(0, 10), BoulderResult.TOP);
			bonuses = Collections.frequency(boulderResults.subList(0, 10), BoulderResult.BONUS);
			if (tops == 10 && 0 < Collections.frequency(boulderResults.subList(10, 15), BoulderResult.TOP)) {
				++tops;
			} else if (tops == 10 && 0 < Collections.frequency(boulderResults.subList(10, 15), BoulderResult.BONUS)) {
				++bonuses;
			}
			break;
		default:
			tops = Collections.frequency(boulderResults, BoulderResult.TOP);
			bonuses = Collections.frequency(boulderResults, BoulderResult.BONUS);
			break;
		}
		return new Score(tops, tops + bonuses);
	}

	public List<BoulderResult> getBoulderResults() {
		return boulderResults;
	}

}
