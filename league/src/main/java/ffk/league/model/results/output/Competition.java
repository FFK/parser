package ffk.league.model.results.output;

import ffk.league.model.competitors.Group;
import ffk.league.model.competitors.Sex;

public enum Competition {
	PRO_MEN, HARD_MEN, EASY_MEN, HARD_WOMEN, EASY_WOMEN, VETERAN_MEN, VETERAN_WOMEN, JUNIOR_MEN, JUNIOR_WOMEN;

	public static Competition getCompetition(Group group, Sex sex) {
		switch (group) {
		case PRO:
			return PRO_MEN;
		case HARD:
			return sex == Sex.MALE ? HARD_MEN : HARD_WOMEN;
		case EASY:
			return sex == Sex.MALE ? EASY_MEN : EASY_WOMEN;
		case JUNIOR:
			return sex == Sex.MALE ? JUNIOR_MEN : JUNIOR_WOMEN;
		case VETERAN:
			return sex == Sex.MALE ? VETERAN_MEN : VETERAN_WOMEN;
		default:
			return null;
		}
	}
}
