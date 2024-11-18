package ma.zyn.app.unit.dao.facade.core.driver;

import ma.zyn.app.bean.core.driver.Driver;
import ma.zyn.app.dao.facade.core.driver.DriverDao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.IntStream;
import java.time.LocalDateTime;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class DriverDaoTest {

@Autowired
    private DriverDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByEmail(){
        String email = "email-1";
        Driver entity = new Driver();
        entity.setEmail(email);
        underTest.save(entity);
        Driver loaded = underTest.findByEmail(email);
        assertThat(loaded.getEmail()).isEqualTo(email);
    }

    @Test
    void shouldDeleteByEmail() {
        String email = "email-12345678";
        int result = underTest.deleteByEmail(email);

        Driver loaded = underTest.findByEmail(email);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        Driver entity = new Driver();
        entity.setId(id);
        underTest.save(entity);
        Driver loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Driver entity = new Driver();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Driver loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Driver> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Driver> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Driver given = constructSample(1);
        Driver saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private Driver constructSample(int i) {
		Driver given = new Driver();
        given.setPhoto("photo-"+i);
        given.setAdresse("adresse-"+i);
        given.setDateInscription(LocalDateTime.now());
        given.setEvaluation(BigDecimal.TEN);
        given.setPassword("password-"+i);
        given.setAccountNonLocked(false);
        given.setPasswordChanged(false);
        given.setUsername("username-"+i);
        given.setAccountNonExpired(false);
        given.setCredentialsNonExpired(false);
        given.setEnabled(false);
        given.setEmail("email-"+i);
        return given;
    }

}
