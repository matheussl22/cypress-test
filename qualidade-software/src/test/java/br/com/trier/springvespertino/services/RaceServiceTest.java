package br.com.trier.springvespertino.services;

import br.com.trier.springvespertino.BaseTest;
import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.models.Race;
import br.com.trier.springvespertino.models.Speedway;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RaceServiceTest extends BaseTest {
    @Autowired
    RaceService recRaceService;
    @Autowired
    ChampionshipService championshipService;
    @Autowired
    SpeedwayService speedwayService;

    @Test
    @DisplayName("Should get race by id")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/race.sql"})
    void findByIdTest() {
        Race race = recRaceService.findById(1);
        assertNotNull(race);
        assertEquals(1, race.getId());
        assertEquals("Blast", race.getChampionship().getDescription());
        assertEquals("Interlagos", race.getSpeedway().getName());
        race = recRaceService.findById(2);
        assertEquals(2, race.getId());
        assertEquals("Pro League", race.getChampionship().getDescription());
        assertEquals("Algarve", race.getSpeedway().getName());
    }

    @Test
    @DisplayName("should try to get race with invalid id")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/race.sql"})
    void findByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> recRaceService.findById(3));
        assertEquals("Corrida 3 não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Should delete race")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/race.sql"})
    void removeRaceTest() {
        recRaceService.delete(2);
        List<Race> list = recRaceService.listAll();
        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getId());
    }

    @Test
    @DisplayName("Should list all races")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/race.sql"})
    void listAllRacesTest() {
        List<Race> list = recRaceService.listAll();
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Should try to list without register races")
    @Sql({"/sqls/limpa_tabelas.sql"})
    void listAllRacesEmptyTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> recRaceService.listAll());
        assertEquals("Não existem corridas cadastradas", exception.getMessage());
    }

    @Test
    @DisplayName("Should update race")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/race.sql"})
    void updateRaceTest() {
        Race race = recRaceService.findById(2);
        Speedway speedway = speedwayService.findById(1);
        Championship championship = championshipService.findById(1);
        assertEquals("Algarve", race.getSpeedway().getName());
        assertEquals("Pro League", race.getChampionship().getDescription());
        Race newRace = new Race(2, race.getDate(), speedway, championship);
        recRaceService.update(newRace);
        race = recRaceService.findById(2);
        assertEquals("Interlagos", race.getSpeedway().getName());
        assertEquals("Blast", race.getChampionship().getDescription());
    }
}
