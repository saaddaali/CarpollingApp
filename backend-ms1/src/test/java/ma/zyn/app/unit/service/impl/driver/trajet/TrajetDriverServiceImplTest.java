package ma.zyn.app.unit.service.impl.driver.trajet;

import ma.zyn.app.bean.core.trajet.Trajet;
import ma.zyn.app.dao.facade.core.trajet.TrajetDao;
import ma.zyn.app.service.impl.driver.trajet.TrajetDriverServiceImpl;

import ma.zyn.app.bean.core.driver.Driver ;
import ma.zyn.app.bean.core.trajet.Ville ;
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
class TrajetDriverServiceImplTest {

    @Mock
    private TrajetDao repository;
    private AutoCloseable autoCloseable;
    private TrajetDriverServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new TrajetDriverServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllTrajet() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveTrajet() {
        // Given
        Trajet toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteTrajet() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetTrajetById() {
        // Given
        Long idToRetrieve = 1L; // Example Trajet ID to retrieve
        Trajet expected = new Trajet(); // You need to replace Trajet with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Trajet result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Trajet constructSample(int i) {
		Trajet given = new Trajet();
        given.setVilleDepart(new Ville(1L));
        given.setVilleDestination(new Ville(1L));
        given.setHoraireDepart(LocalDateTime.now());
        given.setHoraireArrive(LocalDateTime.now());
        given.setPlacesDisponibles(i);
        given.setDriver(new Driver(1L));
        given.setDateCreation(LocalDateTime.now());
        given.setLocalisationSource(new Ville(1L));
        given.setLocalisationDestination(new Ville(1L));
        return given;
    }

}
