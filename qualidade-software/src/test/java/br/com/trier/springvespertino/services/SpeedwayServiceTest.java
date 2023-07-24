package br.com.trier.springvespertino.services;

import br.com.trier.springvespertino.BaseTest;
import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Speedway;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SpeedwayServiceTest extends BaseTest {
    @Autowired
    SpeedwayService speedwayService;
    @Autowired
    CountryService countryService;

    @Test
    @DisplayName("Should get speedway by id")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/speedway.sql"})
    void findByIdTest() {
        Speedway speedway = speedwayService.findById(1);
        assertNotNull(speedway);
        assertEquals(1, speedway.getId());
        assertEquals("Interlagos", speedway.getName());
        speedway = speedwayService.findById(2);
        assertEquals(2, speedway.getId());
        assertEquals("Algarve", speedway.getName());
    }


    @Test
    @DisplayName("should try to get speedway with invalid id")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/speedway.sql"})
    void findByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> speedwayService.findById(3));
        assertEquals("Pista 3 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Should delete speedway")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/speedway.sql"})
    void removeSpeedwayTest() {
        speedwayService.delete(2);
        List<Speedway> list = speedwayService.listAll();
        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getId());
    }

    @Test
    @DisplayName("Should list all speedways")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/speedway.sql"})
    void listAllSpeedwayTest() {
        List<Speedway> list = speedwayService.listAll();
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Should try to list without register speedways")
    @Sql({"/sqls/limpa_tabelas.sql"})
    void listAllSpeedwayEmptyTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> speedwayService.listAll());
        assertEquals("Nenhuma pista cadastrada", exception.getMessage());
    }

    @Test
    @DisplayName("Should update speedway")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/speedway.sql"})
    void updateSpeedwayTest() {
        Speedway speedway = speedwayService.findById(2);
        Country country = countryService.findById(2);
        assertEquals("Algarve", speedway.getName());
        Speedway newSpeedway = new Speedway(2, "Lisboa", 2500, country);
        speedwayService.update(newSpeedway);
        speedway = speedwayService.findById(2);
        assertEquals("Lisboa", speedway.getName());
        assertEquals("Portugal", speedway.getCountry().getName());
    }
}
