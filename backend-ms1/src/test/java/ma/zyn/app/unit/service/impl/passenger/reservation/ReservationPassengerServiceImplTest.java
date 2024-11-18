package ma.zyn.app.unit.service.impl.passenger.reservation;

import ma.zyn.app.bean.core.reservation.Reservation;
import ma.zyn.app.dao.facade.core.reservation.ReservationDao;
import ma.zyn.app.service.impl.passenger.reservation.ReservationPassengerServiceImpl;

import ma.zyn.app.bean.core.driver.Driver ;
import ma.zyn.app.bean.core.passenger.Passenger ;
import ma.zyn.app.bean.core.paiement.CarteBancaire ;
import ma.zyn.app.bean.core.message.Conversation ;
import ma.zyn.app.bean.core.trajet.Trajet ;
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
class ReservationPassengerServiceImplTest {

    @Mock
    private ReservationDao repository;
    private AutoCloseable autoCloseable;
    private ReservationPassengerServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ReservationPassengerServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllReservation() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveReservation() {
        // Given
        Reservation toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteReservation() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetReservationById() {
        // Given
        Long idToRetrieve = 1L; // Example Reservation ID to retrieve
        Reservation expected = new Reservation(); // You need to replace Reservation with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Reservation result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Reservation constructSample(int i) {
		Reservation given = new Reservation();
        given.setTrajet(new Trajet(1L));
        given.setPassenger(new Passenger(1L));
        given.setDriver(new Driver(1L));
        given.setDateReservation(LocalDateTime.now());
        given.setMontant(BigDecimal.TEN);
        given.setDatePaiement(LocalDateTime.now());
        given.setCarteBancaire(new CarteBancaire(1L));
        given.setEvaluation(BigDecimal.TEN);
        given.setConversation(new Conversation(1L));
        return given;
    }

}
