package ma.zyn.app.unit.service.impl.driver.passenger;

import ma.zyn.app.bean.core.passenger.Passenger;
import ma.zyn.app.dao.facade.core.passenger.PassengerDao;
import ma.zyn.app.service.impl.driver.passenger.PassengerDriverServiceImpl;

import ma.zyn.app.bean.core.paiement.CarteBancaire ;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDateTime;



import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class PassengerDriverServiceImplTest {

    @Mock
    private PassengerDao repository;
    private AutoCloseable autoCloseable;
    private PassengerDriverServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new PassengerDriverServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllPassenger() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSavePassenger() {
        // Given
        Passenger toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeletePassenger() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetPassengerById() {
        // Given
        Long idToRetrieve = 1L; // Example Passenger ID to retrieve
        Passenger expected = new Passenger(); // You need to replace Passenger with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Passenger result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
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
