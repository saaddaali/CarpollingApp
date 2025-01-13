package ma.zyn.app.unit.service.impl.passenger.driver;

import ma.zyn.app.bean.core.driver.Driver;
import ma.zyn.app.dao.facade.core.driver.DriverDao;
import ma.zyn.app.service.impl.passenger.driver.DriverPassengerServiceImpl;

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
class DriverPassengerServiceImplTest {

    @Mock
    private DriverDao repository;
    private AutoCloseable autoCloseable;
    private DriverPassengerServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new DriverPassengerServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllDriver() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

//    @Test
//    void itShouldSaveDriver() {
//        // Given
//        Driver toSave = constructSample(1);
//        when(repository.save(toSave)).thenReturn(toSave);
//
//        // When
//        underTest.create(toSave);
//
//        // Then
//        verify(repository).save(toSave);
//    }

    @Test
    void itShouldDeleteDriver() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetDriverById() {
        // Given
        Long idToRetrieve = 1L; // Example Driver ID to retrieve
        Driver expected = new Driver(); // You need to replace Driver with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Driver result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
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
