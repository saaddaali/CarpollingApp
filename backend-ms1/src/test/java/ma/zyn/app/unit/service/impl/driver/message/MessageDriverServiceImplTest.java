package ma.zyn.app.unit.service.impl.driver.message;

import ma.zyn.app.bean.core.message.Message;
import ma.zyn.app.dao.facade.core.message.MessageDao;
import ma.zyn.app.service.impl.driver.message.MessageDriverServiceImpl;

import ma.zyn.app.bean.core.driver.Driver ;
import ma.zyn.app.bean.core.passenger.Passenger ;
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
class MessageDriverServiceImplTest {

    @Mock
    private MessageDao repository;
    private AutoCloseable autoCloseable;
    private MessageDriverServiceImpl underTest;



    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new MessageDriverServiceImpl(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void canGetAllMessage() {
         //when
        underTest.findAll();
        verify(repository).findAll();
    }

    @Test
    void itShouldSaveMessage() {
        // Given
        Message toSave = constructSample(1);
        when(repository.save(toSave)).thenReturn(toSave);

        // When
        underTest.create(toSave);

        // Then
        verify(repository).save(toSave);
    }

    @Test
    void itShouldDeleteMessage() {
        // Given
        Long idToDelete = 1L;
        when(repository.existsById(idToDelete)).thenReturn(true);

        // When
        underTest.deleteById(idToDelete);

        // Then
        verify(repository).deleteById(idToDelete);
    }
    @Test
    void itShouldGetMessageById() {
        // Given
        Long idToRetrieve = 1L; // Example Message ID to retrieve
        Message expected = new Message(); // You need to replace Message with your actual class
        expected.setId(idToRetrieve);
        when(repository.findById(idToRetrieve)).thenReturn(java.util.Optional.of(expected));

        // When
        Message result = underTest.findById(idToRetrieve);

        // Then
        assertEquals(expected, result);
    }
	
	private Message constructSample(int i) {
		Message given = new Message();
        given.setTrajet(new Trajet(1L));
        given.setDriver(new Driver(1L));
        given.setPassenger(new Passenger(1L));
        given.setContenu("contenu-"+i);
        given.setDateEnvoi(LocalDateTime.now());
        given.setConversation(new Conversation(1L));
        return given;
    }

}
