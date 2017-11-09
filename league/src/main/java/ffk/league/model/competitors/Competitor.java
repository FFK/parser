package ffk.league.model.competitors;

import ffk.league.model.results.input.Score;

public class Competitor implements Comparable<Competitor> {

	private String group;
	private String name;
	private Score scoreAll;

	Competitor(String name, String group) {
		this.name = name;
		this.group = group;
	}

	@Override
	public int compareTo(Competitor o) {
		int res = o.scoreAll.compareTo(scoreAll);
		if (res == 0) {
			res = name.compareTo(o.name);
		}
		return res;
	}

	public String getGroup() {
		return group;
	}

	public String getName() {
		return name;
	}

	public Score getScoreAll() {
		return scoreAll;
	}

	public void setScoreAll(Score scoreAll) {
		this.scoreAll = scoreAll;
	}

}
