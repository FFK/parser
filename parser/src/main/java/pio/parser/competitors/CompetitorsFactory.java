package pio.parser.competitors;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public final class CompetitorsFactory {

	private static Map<String, Competitor> competitors = new HashMap<>();
	private static Map<String, String> replacementPartsMap = new HashMap<>();
	static {
		replacementPartsMap.put("AKOWACZ \\?", "AKOWACZ");
		replacementPartsMap.put("ALEKSNDER", "ALEKSANDER");
		replacementPartsMap.put("AMANADA", "AMANDA");
		replacementPartsMap.put("ANASTASIJA", "ANASTASIYA");
		replacementPartsMap.put("BANDOLERAS", "BANDERAS");
		replacementPartsMap.put("BART£OMIEJ", "BARTEK");
		replacementPartsMap.put("BOGUSTOWICZ", "BOGUS£OWICZ");
		replacementPartsMap.put("DARIUSZ", "DAREK");
		replacementPartsMap.put("FRANCISZEK", "FRANEK");
		replacementPartsMap.put("GOSLO", "GORLO");
		replacementPartsMap.put("GRZEG”RZ", "GRZEGORZ");
		replacementPartsMap.put("GRZEå", "GRZEGORZ");
		replacementPartsMap.put("GW”ØDè", "GW”èDè");
		replacementPartsMap.put("GW”ØDØ", "GW”èDè");
		replacementPartsMap.put("GWIØDØ", "GW”èDè");
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
		replacementPartsMap.put("MA£GORZATA", "GOSIA");
		replacementPartsMap.put("MARTNA", "MARTYNA");
		replacementPartsMap.put("MATUSZEK", "MATUSZAK");
		replacementPartsMap.put("M DRZAK", "KOPERWAS");
		replacementPartsMap.put("MIRZEJEWSKI", "MIERZEJEWSKI");
		replacementPartsMap.put("MI TUS", "MIENTUS");
		replacementPartsMap.put("PI TKOWSKA", "PI•TKOWSKA");
		replacementPartsMap.put("PIOTA", "PIOTR");
		replacementPartsMap.put("RADOS£AW", "RADEK");
		replacementPartsMap.put("SAS NOWOSIELSKI", "SAS-NOWOSIELSKI");
		replacementPartsMap.put("SYMON", "SZYMON");
		replacementPartsMap.put("TROJNAK", "TROJNAR");
		replacementPartsMap.put("TYNALIK", "TYRALIK");
		replacementPartsMap.put("TYRDLIK\\?", "TYRALIK");
		replacementPartsMap.put("WALDEMAR", "WALDEK");
		replacementPartsMap.put("WOJCIECH", "WOJTEK");
		replacementPartsMap.put("WYCIåCIK", "WYCIåLIK");
		replacementPartsMap.put("ZBIGNIEW", "ZBYSZEK");
		replacementPartsMap.put("Z£ACHOWSKA", "Z£AKOWSKA");
		replacementPartsMap.put("ZUZANNA", "ZUZA");
		replacementPartsMap.put("ZUZIA", "ZUZA");
	}
	private static Map<String, String> renamingMap = new HashMap<>();
	static{
		renamingMap.put("ADAM S", "ADAM SIE—KO");
		renamingMap.put("ARTU W”JCIK", "ARTUR W”JCIK");
		renamingMap.put("MA£ORZATA DOBISZ", "GOSIA DOBISZ");
		renamingMap.put("JACEK J”ZEF CZECH", "JACEK CZECH");
		renamingMap.put("JOANNA NIECHWIEDOWICZ", "JOANNA NIECHWIEDOWIC");
		renamingMap.put("KUBA W", "KUBA WYBIERALSKI");
		renamingMap.put("RAFA£ PI TAK", "DZIK");
	}

	private CompetitorsFactory() {
		//prevents creating an instance
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

	private static String standarizeString(String string) {
		String res = string.toUpperCase().trim();
		for (Map.Entry<String, String> entry : replacementPartsMap.entrySet()) {
			res = renamingMap.containsKey(res) ? renamingMap.get(res) : res;
			res = res.replaceAll(entry.getKey(), entry.getValue());
		}
		return res;
	}

}
