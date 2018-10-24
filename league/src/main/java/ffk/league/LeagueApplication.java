package ffk.league;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import ffk.league.parser.Parser;

@SpringBootApplication
public class LeagueApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(Parser.class);

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(LeagueApplication.class, args);
		Parser parser = (Parser) context.getBean("parser");

//		while (true) { 
		try {
			parser.parse();
		} catch (RuntimeException e) {
			LOGGER.error("broken", e);
		}
//			try {
//				Thread.sleep(60000);
//			} catch (InterruptedException e) {
//				throw new IllegalStateException(e);
//			}
//		}

	}
}
