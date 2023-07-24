package br.com.trier.springvespertino.services;

import br.com.trier.springvespertino.BaseTest;
import br.com.trier.springvespertino.models.Country;
import br.com.trier.springvespertino.services.exceptions.ObjectNotFound;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CountryServiceTest extends BaseTest {
    @Autowired
    CountryService countryService;

    @Test
    @DisplayName("Should get country by id")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/country.sql"})
    void findByIdTest() {
        Country country = countryService.findById(1);
        assertNotNull(country);
        assertEquals(1, country.getId());
        assertEquals("Brasil", country.getName());
        country = countryService.findById(2);
        assertEquals(2, country.getId());
        assertEquals("Portugal", country.getName());
    }

    @Test
    @DisplayName("should try to get country with invalid id")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/country.sql"})
    void findByIdNonExistsTest() {
        var exception = assertThrows(ObjectNotFound.class, () -> countryService.findById(3));
        assertEquals("País não existe", exception.getMessage());
    }

    @Test
    @DisplayName("Should delete country")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/country.sql"})
    void removeCountryTest() {
        countryService.delete(2);
        List<Country> list = countryService.listAll();
        assertEquals(1, list.size());
        assertEquals(1, list.get(0).getId());
    }

    @Test
    @DisplayName("Should list all countries")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/country.sql"})
    void listAllCountriesTest() {
        List<Country> list = countryService.listAll();
        assertEquals(2, list.size());
    }

    @Test
    @DisplayName("Should update country")
    @Sql({"/sqls/limpa_tabelas.sql"})
    @Sql({"/sqls/country.sql"})
    void updateCountryTest() {
        Country country = countryService.findById(2);
        assertEquals("Portugal", country.getName());
        Country newCountry = new Country(2, "Angola");
        countryService.update(newCountry);
        country = countryService.findById(2);
        assertEquals("Angola", country.getName());
    }
}
