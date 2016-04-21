package id.co.blogspot.fathan.netanalytic;

import org.junit.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class ApplicationTest {

  @SuppressWarnings("static-access")
  @Test
  public void constructorTest() throws Exception {
    Application application = new Application();
    application.configure(new SpringApplicationBuilder(new Object()));
    application.main(new String[] {});
  }

}
