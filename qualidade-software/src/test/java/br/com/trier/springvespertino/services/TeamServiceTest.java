package br.com.trier.springvespertino.services;

import br.com.trier.springvespertino.BaseTest;
import br.com.trier.springvespertino.models.Team;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TeamServiceTest extends BaseTest {
    @Autowired
    TeamService teamService;

    @Test
    @DisplayName("Should get team by id")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/team.sql"})
    void findByIdTest() {
        Team team = teamService.findById(1);
        assertNotNull(team);
        assertEquals(1, team.getId());
        assertEquals("Ferrari", team.getName());
        team = teamService.findById(2);
        assertEquals(2, team.getId());
        assertEquals("Aston Martin", team.getName());
    }

    @Test
    @DisplayName("should try to get team with invalid id")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/team.sql"})
    void findByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> teamService.findById(3));
        assertEquals("Equipe 3 não encontrada", exception.getMessage());
    }

    @Test
    @DisplayName("Should delete team")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/team.sql"})
    void removeTeamTest() {
        teamService.delete(2);
        List<Team> list = teamService.listAll();
        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getId());
    }

    @Test
    @DisplayName("Should list all teams")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/team.sql"})
    void listAllTeamsTest() {
        List<Team> list = teamService.listAll();
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Should try to list without register teams")
    void listAllTeamsEmptyTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> teamService.listAll());
        assertEquals("Não existe equipes cadastradas", exception.getMessage());
    }

    @Test
    @DisplayName("Should update team")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/team.sql"})
    void updateTeamTest() {
        Team team = teamService.findById(2);
        assertEquals("Aston Martin", team.getName());
        Team newTeam = new Team(2, "Aston Martinn");
        teamService.update(newTeam);
        team = teamService.findById(2);
        assertEquals("Aston Martinn", team.getName());
    }
}
