package ffk.league.model.competitors;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

public final class CompetitorsFactory {

	private static Map<String, Competitor> competitors = new HashMap<>();
	private static Map<String, String> replacementPartsMap = new HashMap<>();
	static {
		replacementPartsMap.put("AKOWACZ \\?", "AKOWACZ");
		replacementPartsMap.put("ALEKSNDER", "ALEKSANDER");
		replacementPartsMap.put("AMANADA", "AMANDA");
		replacementPartsMap.put("ANASTASIJA", "ANASTASIYA");
		replacementPartsMap.put("BANDOLERAS", "BANDERAS");
		replacementPartsMap.put("BARTŁOMIEJ", "BARTEK");
		replacementPartsMap.put("BOGUSTOWICZ", "BOGUSŁOWICZ");
		replacementPartsMap.put("DARIUSZ", "DAREK");
		replacementPartsMap.put("FRANCISZEK", "FRANEK");
		replacementPartsMap.put("GOSLO", "GORLO");
		replacementPartsMap.put("GRZEGÓRZ", "GRZEGORZ");
		replacementPartsMap.put("GRZEŚ", "GRZEGORZ");
		replacementPartsMap.put("GWÓŻDŹ", "GWÓŹDŹ");
		replacementPartsMap.put("GWÓŻDŻ", "GWÓŹDŹ");
		replacementPartsMap.put("GWIŻDŻ", "GWÓŹDŹ");
		replacementPartsMap.put("HAJNUCZEK", "HAJDUCZEK");
		replacementPartsMap.put("HURTLING", "HERTLIG");
		replacementPartsMap.put("IRENEUSZ", "IREK");
		replacementPartsMap.put("JAKUB", "KUBA");
		replacementPartsMap.put("KACERT", "KACPER");
		replacementPartsMap.put("KAJETAN", "KAJTEK");
		replacementPartsMap.put("KATROLINA", "KAROLINA");
		replacementPartsMap.put("KIWIER", "KIWER");
		replacementPartsMap.put("KORNACKI", "KORNECKI");
		replacementPartsMap.put("MACIEJ", "MACIEK");
		replacementPartsMap.put("MAGIELA", "MAGIERA");
		replacementPartsMap.put("MAŁGORZATA", "GOSIA");
		replacementPartsMap.put("MARTNA", "MARTYNA");
		replacementPartsMap.put("MATUSZEK", "MATUSZAK");
		replacementPartsMap.put("MĘDRZAK", "KOPERWAS");
		replacementPartsMap.put("MIRZEJEWSKI", "MIERZEJEWSKI");
		replacementPartsMap.put("MIĘTUS", "MIENTUS");
		replacementPartsMap.put("PIĘTKOWSKA", "PIĄTKOWSKA");
		replacementPartsMap.put("PIOTA", "PIOTR");
		replacementPartsMap.put("RADOSŁAW", "RADEK");
		replacementPartsMap.put("SAS NOWOSIELSKI", "SAS-NOWOSIELSKI");
		replacementPartsMap.put("SYMON", "SZYMON");
		replacementPartsMap.put("TROJNAK", "TROJNAR");
		replacementPartsMap.put("TYNALIK", "TYRALIK");
		replacementPartsMap.put("TYRDLIK\\?", "TYRALIK");
		replacementPartsMap.put("WALDEMAR", "WALDEK");
		replacementPartsMap.put("WOJCIECH ", "WOJTEK ");
		replacementPartsMap.put("WYCIŚCIK", "WYCIŚLIK");
		replacementPartsMap.put("ZBIGNIEW", "ZBYSZEK");
		replacementPartsMap.put("ZŁACHOWSKA", "ZŁAKOWSKA");
		replacementPartsMap.put("ZUZANNA", "ZUZA");
		replacementPartsMap.put("ZUZIA", "ZUZA");
	}
	private static Map<String, String> renamingMap = new HashMap<>();
	static {
		renamingMap.put("ADAM S", "ADAM SIEŃKO");
		renamingMap.put("ARTU WÓJCIK", "ARTUR WÓJCIK");
		renamingMap.put("MAŁORZATA DOBISZ", "GOSIA DOBISZ");
		renamingMap.put("JACEK JÓZEF CZECH", "JACEK CZECH");
		renamingMap.put("KUBA W", "KUBA WYBIERALSKI");
		renamingMap.put("RAFAŁ PIĘTAK", "DZIK");
	}

	private CompetitorsFactory() {
		// prevents creating an instance
	}

	public static Competitor getOrCreateCompetitor(String club, Group group, String name, Sex sex) {
		String stadarizedName = standarizeString(name);
		if (competitors.containsKey(stadarizedName)) {
			Competitor competitor = competitors.get(stadarizedName);
			competitor.setClub(StringUtils.isEmpty(competitor.getClub()) ? club : competitor.getClub());
			competitor.setGroup(competitor.getGroup() == null ? group : competitor.getGroup());
			competitor.setSex(competitor.getSex() == null ? sex : competitor.getSex());
			return competitor;
		} else {
			Competitor competitor = new Competitor(club, group, stadarizedName, sex);
			competitors.put(stadarizedName, competitor);
			return competitor;
		}
	}

	public static void clear() {
		competitors = new HashMap<>();
	}

	private static String standarizeString(String string) {
		String res = string.toUpperCase().trim();
		for (Map.Entry<String, String> entry : replacementPartsMap.entrySet()) {
			res = renamingMap.containsKey(res) ? renamingMap.get(res) : res;
			res = res.replaceAll(entry.getKey(), entry.getValue());
		}
		return res;
	}

}
