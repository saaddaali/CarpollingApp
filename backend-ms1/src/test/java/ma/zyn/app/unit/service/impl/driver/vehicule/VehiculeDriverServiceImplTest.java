package ma.zyn.app.unit.service.impl.driver.vehicule;

import ma.zyn.app.bean.core.vehicule.Vehicule;
import ma.zyn.app.dao.facade.core.vehicule.VehiculeDao;
import ma.zyn.app.service.impl.driver.vehicule.VehiculeDriverServiceImpl;

import ma.zyn.app.bean.core.driver.Driver ;
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
class VehiculeDriverServiceImplTest {

    @Mock
    private VehiculeDao repository;
    private AutoCloseable autoCloseable;
    private VehiculeDriverServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new VehiculeDriverServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllVehicule() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveVehicule() {
        // Given
        Vehicule toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteVehicule() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetVehiculeById() {
        // Given
        Long idToRetrieve = 1L; // Example Vehicule ID to retrieve
        Vehicule expected = new Vehicule(); // You need to replace Vehicule with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Vehicule result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Vehicule constructSample(int i) {
		Vehicule given = new Vehicule();
        given.setDriver(new Driver(1L));
        given.setMarque("marque-"+i);
        given.setModele("modele-"+i);
        given.setAnnee(i);
        given.setCouleur("couleur-"+i);
        given.setPlaqueImmatriculation("plaqueImmatriculation-"+i);
        given.setCapacite(i);
        given.setImage("image-"+i);
        return given;
    }

}
