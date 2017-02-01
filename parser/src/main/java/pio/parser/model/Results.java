package pio.parser.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pio.parser.competitors.Competitor;

public class Results {

	private List<String> editions = new ArrayList<>();
	private Map<Competitor, PerformncesByEdition> performancesMapsByCompetitor = new HashMap<>();

	public List<String> getEditions() {
		return editions;
	}

	public Map<Competitor, PerformncesByEdition> getPerformancesMapsByCompetitor() {
		return performancesMapsByCompetitor;
	}


}
