package ma.zyn.app.unit.service.impl.passenger.paiement;

import ma.zyn.app.bean.core.paiement.CarteBancaire;
import ma.zyn.app.dao.facade.core.paiement.CarteBancaireDao;
import ma.zyn.app.service.impl.passenger.paiement.CarteBancairePassengerServiceImpl;

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
class CarteBancairePassengerServiceImplTest {

    @Mock
    private CarteBancaireDao repository;
    private AutoCloseable autoCloseable;
    private CarteBancairePassengerServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CarteBancairePassengerServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllCarteBancaire() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveCarteBancaire() {
        // Given
        CarteBancaire toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteCarteBancaire() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetCarteBancaireById() {
        // Given
        Long idToRetrieve = 1L; // Example CarteBancaire ID to retrieve
        CarteBancaire expected = new CarteBancaire(); // You need to replace CarteBancaire with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        CarteBancaire result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private CarteBancaire constructSample(int i) {
		CarteBancaire given = new CarteBancaire();
        given.setTitulaire("titulaire-"+i);
        given.setNumero("numero-"+i);
        given.setDateExpiration(LocalDateTime.now());
        given.setCodeSecret("codeSecret-"+i);
        return given;
    }

}
