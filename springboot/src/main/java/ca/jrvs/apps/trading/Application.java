package ca.jrvs.apps.trading;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {JdbcTemplateAutoConfiguration.class,
    DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class Application implements CommandLineRunner {

  private final Logger logger = LoggerFactory.getLogger("Springboot Application");

  //@Value("${app.init.dailyList}")
  //private String[] initDailyList;

  /*@Autowired
  private QuoteService quoteService;
  @Autowired
  private TraderAccountService traderAccountService;*/

  public static void main(String[] args) {
    SpringApplication app = new SpringApplication(Application.class);
    app.setAdditionalProfiles("development");
    app.run(args);
  }

  /**
   * Callback used to run the bean.
   *
   * @param args incoming main method arguments
   * @throws Exception on error
   */
  @Override
  public void run(String... args) throws Exception {

  }
}
