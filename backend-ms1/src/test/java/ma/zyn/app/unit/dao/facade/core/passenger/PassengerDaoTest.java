package ma.zyn.app.unit.dao.facade.core.passenger;

import ma.zyn.app.bean.core.passenger.Passenger;
import ma.zyn.app.dao.facade.core.passenger.PassengerDao;

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

import ma.zyn.app.bean.core.paiement.CarteBancaire ;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class PassengerDaoTest {

@Autowired
    private PassengerDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByEmail(){
        String email = "email-1";
        Passenger entity = new Passenger();
        entity.setEmail(email);
        underTest.save(entity);
        Passenger loaded = underTest.findByEmail(email);
        assertThat(loaded.getEmail()).isEqualTo(email);
    }

    @Test
    void shouldDeleteByEmail() {
        String email = "email-12345678";
        int result = underTest.deleteByEmail(email);

        Passenger loaded = underTest.findByEmail(email);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        Passenger entity = new Passenger();
        entity.setId(id);
        underTest.save(entity);
        Passenger loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Passenger entity = new Passenger();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Passenger loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Passenger> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Passenger> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Passenger given = constructSample(1);
        Passenger saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private Passenger constructSample(int i) {
		Passenger given = new Passenger();
        given.setPhoto("photo-"+i);
        given.setAdresse("adresse-"+i);
        given.setDateInscription(LocalDateTime.now());
        given.setEvaluation(BigDecimal.TEN);
        given.setCarteBancaire(new CarteBancaire(1L));
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
