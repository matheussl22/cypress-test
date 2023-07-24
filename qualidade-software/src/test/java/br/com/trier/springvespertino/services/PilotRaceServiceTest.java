package br.com.trier.springvespertino.services;

import br.com.trier.springvespertino.BaseTest;
import br.com.trier.springvespertino.models.Pilot;
import br.com.trier.springvespertino.models.PilotRace;
import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PilotRaceServiceTest extends BaseTest {
    @Autowired
    PilotRaceService pilotRaceService;
    @Autowired
    PilotService pilotService;
    @Autowired
    RaceService raceService;

    @Test
    @DisplayName("Should get pilotRace by id")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/pilot_race.sql"})
    void findByIdTest() {
        PilotRace pilotRace = pilotRaceService.findById(1);
        assertNotNull(pilotRace);
        assertEquals(1, pilotRace.getId());
        assertEquals("JeDai", pilotRace.getPilot().getName());
        assertEquals("Interlagos", pilotRace.getRace().getSpeedway().getName());
        assertEquals("Blast", pilotRace.getRace().getChampionship().getDescription());
        pilotRace = pilotRaceService.findById(2);
        assertEquals(2, pilotRace.getId());
        assertEquals("Piloto2", pilotRace.getPilot().getName());
        assertEquals("Algarve", pilotRace.getRace().getSpeedway().getName());
        assertEquals("Pro League", pilotRace.getRace().getChampionship().getDescription());
    }

    @Test
    @DisplayName("should try to get pilotRace with invalid id")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/pilot_race.sql"})
    void findByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> pilotRaceService.findById(3));
        assertEquals("ID 3 inválido!", exception.getMessage());
    }

    @Test
    @DisplayName("Should delete pilotRace")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/pilot_race.sql"})
    void removePilotRaceTest() {
        pilotRaceService.delete(2);
        List<PilotRace> list = pilotRaceService.listAll();
        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getId());
    }

    @Test
    @DisplayName("Should list all pilotRace")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/pilot_race.sql"})
    void listAllPilotRacesTest() {
        List<PilotRace> list = pilotRaceService.listAll();
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Should try to list without register pilotRaces")
    void listAllPilotRacesEmptyTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> pilotRaceService.listAll());
        assertEquals("Nenhum PilotoCorrida cadastrado!", exception.getMessage());
    }

    @Test
    @DisplayName("Should update pilotRace")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/pilot_race.sql"})
    void updatePilotRaceTest() {
        PilotRace pilotRace = pilotRaceService.findById(1);
        Pilot pilot = pilotService.findById(2);
        Race race = raceService.findById(2);
        PilotRace newPilotRace = new PilotRace(1, 1, pilot, race);
        pilotRaceService.update(newPilotRace);
        pilotRace = pilotRaceService.findById(1);
        assertEquals("Piloto2", pilotRace.getPilot().getName());
        assertEquals("Algarve", pilotRace.getRace().getSpeedway().getName());
        assertEquals("Pro League", pilotRace.getRace().getChampionship().getDescription());
    }
}
