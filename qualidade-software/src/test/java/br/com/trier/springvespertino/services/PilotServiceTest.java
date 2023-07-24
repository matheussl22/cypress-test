package br.com.trier.springvespertino.services;

import br.com.trier.springvespertino.BaseTest;
import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.models.Pilot;
import br.com.trier.springvespertino.models.Team;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PilotServiceTest extends BaseTest {
        @Autowired
    PilotService pilotService;
    @Autowired
    CountryService countryService;
    @Autowired
    TeamService teamService;

    @Test
    @DisplayName("Should get pilot by id")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/pilot.sql"})
    void findByIdTest() {
        Pilot pilot = pilotService.findById(1);
        assertNotNull(pilot);
        assertEquals(1, pilot.getId());
        assertEquals("JeDai", pilot.getName());
        pilot = pilotService.findById(2);
        assertEquals(2, pilot.getId());
        assertEquals("Piloto2", pilot.getName());
    }

    @Test
    @DisplayName("should try to get pilot with invalid id")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/pilot.sql"})
    void findByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> pilotService.findById(3));
        assertEquals("Pilot 3 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Should delete pilot")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/pilot.sql"})
    void removePilotTest() {
        pilotService.delete(2);
        List<Pilot> list = pilotService.listAll();
        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getId());
    }

    @Test
    @DisplayName("Should list all pilots")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/pilot.sql"})
    void listAllPilotsTest() {
        List<Pilot> list = pilotService.listAll();
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Should try to list without register pilots")
    @Sql({"/sqls/limpa_tabelas.sql"})
    void listAllPilotsEmptyTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> pilotService.listAll());
        assertEquals("Nenhum piloto cadastrado", exception.getMessage());
    }

    @Test
    @DisplayName("Should update pilot")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/pilot.sql"})
    void updatePilotTest() {
        Pilot pilot = pilotService.findById(2);
        Country country = countryService.findById(2);
        Team team = teamService.findById(2);
        assertEquals("Piloto2", pilot.getName());
        Pilot newPilot = new Pilot(2, "JeDai2", country, team);
        pilotService.update(newPilot);
        pilot = pilotService.findById(2);
        assertEquals("JeDai2", pilot.getName());
        assertEquals("Portugal", pilot.getCountry().getName());
        assertEquals("Aston Martin", pilot.getTeam().getName());
    }
}
