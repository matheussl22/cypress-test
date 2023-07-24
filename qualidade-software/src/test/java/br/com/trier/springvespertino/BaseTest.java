package br.com.trier.springvespertino;

import br.com.trier.springvespertino.services.TeamService;
import br.com.trier.springvespertino.services.UserService;
import br.com.trier.springvespertino.services.impl.TeamServiceImpl;
import br.com.trier.springvespertino.services.impl.UserServiceImpl;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTest {

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
}
