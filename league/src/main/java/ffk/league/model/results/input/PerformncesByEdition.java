package ffk.league.model.results.input;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PerformncesByEdition {

	private Map<String, Performance> map = new HashMap<>();

	public int countBestEditionsBonuses(int editionsNumber) {
		return countBonuses(pickBestEditions(map.values(), editionsNumber));
	}

	public int countBestEditionsTops(int editionsNumber) {
		return countTops(pickBestEditions(map.values(), editionsNumber));
	}

	private int countBonuses(Collection<Performance> performances) {
		int res = 0;
		for (Performance performance : performances) {
			res += performance.countScore().getBonuses();
		}
		return res;
	}

	public int countBonuses() {
		return countBonuses(map.values());
	}

	private int countTops(Collection<Performance> performances) {
		int res = 0;
		for (Performance performance : performances) {
			res += performance.countScore().getTops();
		}
		return res;
	}

	public int countTops() {
		return countTops(map.values());
	}

	public Map<String, Performance> getMap() {
		return map;
	}

	private List<Performance> pickBestEditions(Collection<Performance> editionPerformances, int editionsNumber) {
		List<Performance> res = new ArrayList<>(editionPerformances);
		Collections.sort(res);
		Collections.reverse(res);
		return res.subList(0, Math.min(editionsNumber, res.size()));
	}
}
