package pio.parser.competitors;

import pio.parser.model.Score;

public class Competitor implements Comparable<Competitor> {
	private String club;
	private Group group;
	private String name;
	private Score scoreAll;
	private Score scoreBest;

	private Sex sex;

	Competitor(String club, Group group, String name, Sex sex) {
		this.club = club;
		this.group = group;
		this.name = name;
		this.sex = sex;
	}

	@Override
	public int compareTo(Competitor o) {
		int res = Integer.compare(group.ordinal(), o.getGroup().ordinal());
		if (res == 0) {
			res = Integer.compare(sex.ordinal(), o.getSex().ordinal());
		}
		if (res == 0) {
			res = o.scoreBest.compareTo(scoreBest);
		}
		if (res == 0) {
			res = o.scoreAll.compareTo(scoreAll);
		}
		if (res == 0) {
			res = name.compareTo(o.name);
		}
		return res;
	}

	public String getClub() {
		return club;
	}
	public Group getGroup() {
		return group;
	}

	public String getName() {
		return name;
	}

	public Score getScoreAll() {
		return scoreAll;
	}

	public Score getScoreBest() {
		return scoreBest;
	}

	public Sex getSex() {
		return sex;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public void setScoreAll(Score scoreAll) {
		this.scoreAll = scoreAll;
	}

	public void setScoreBest(Score scoreBest) {
		this.scoreBest = scoreBest;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

}
