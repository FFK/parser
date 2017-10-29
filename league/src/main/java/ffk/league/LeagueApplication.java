package ffk.league;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import ffk.league.parser.Parser;

@SpringBootApplication
public class LeagueApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(LeagueApplication.class, args);
		Parser parser = (Parser) context.getBean("parser");
		parser.parse();
	}
}
