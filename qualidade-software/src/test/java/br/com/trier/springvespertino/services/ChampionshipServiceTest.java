package br.com.trier.springvespertino.services;

import br.com.trier.springvespertino.BaseTest;
import br.com.trier.springvespertino.models.Championship;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChampionshipServiceTest extends BaseTest {

    @Autowired
    ChampionshipService championshipService;

    @Test
    @DisplayName("Should get championship by id")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/championship.sql"})
    void findByIdTest() {
        Championship championship = championshipService.findById(1);
        assertNotNull(championship);
        assertEquals(1, championship.getId());
        assertEquals("Blast", championship.getDescription());
        championship = championshipService.findById(2);
        assertEquals(2, championship.getId());
        assertEquals("Pro League", championship.getDescription());
    }

    @Test
    @DisplayName("should try to get championship with invalid id")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/championship.sql"})
    void findByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> championshipService.findById(3));
        assertEquals("Campeonato não encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Should delete championship")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/championship.sql"})
    void removeChampionshipTest() {
        championshipService.delete(2);
        List<Championship> list = championshipService.listAll();
        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getId());
    }

    @Test
    @DisplayName("Should list all championships")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/championship.sql"})
    void listAllChampionshipsTest() {
        List<Championship> list = championshipService.listAll();
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Should try to list without register championship")
    void listAllChampionshipsEmptyTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> championshipService.listAll());
        assertEquals("Não existe campeonato cadastrado", exception.getMessage());
    }

    @Test
    @DisplayName("Should update championship")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/championship.sql"})
    void updateChampionshipTest() {
        Championship championship = championshipService.findById(2);
        assertEquals("Pro League", championship.getDescription());
        assertEquals(2023, championship.getYear());
        Championship newChampionship = new Championship(2, "Rookie League", 2024);
        championshipService.update(newChampionship);
        championship = championshipService.findById(2);
        assertEquals("Rookie League", championship.getDescription());
        assertEquals(2024, championship.getYear());
    }
}
