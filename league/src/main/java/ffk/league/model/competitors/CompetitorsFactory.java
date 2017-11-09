package ffk.league.model.competitors;

import java.util.HashMap;
import java.util.Map;

public final class CompetitorsFactory {

	private static final Map<String, String> groupsMap = new HashMap<>();

	static {
		groupsMap.put("Dziewczęta 9-11 lat", "g9-11");
		groupsMap.put("Dziewczęta 12-13 lat", "g12-13");
		groupsMap.put("Dziewczęta 14-15 lat", "g14-15");
		groupsMap.put("Chłopcy 9-11 lat", "b9-11");
		groupsMap.put("Chłopcy 12-13 lat", "b12-13");
		groupsMap.put("Chłopcy 14-15 lat", "b14-15");
	}

	public static Competitor getOrCreateCompetitor(String name, String group) {
		return new Competitor(name, groupsMap.get(group));
	}

}
