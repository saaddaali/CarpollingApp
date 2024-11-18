package ma.zyn.app.unit.dao.facade.core.message;

import ma.zyn.app.bean.core.message.Message;
import ma.zyn.app.dao.facade.core.message.MessageDao;

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

import ma.zyn.app.bean.core.driver.Driver ;
import ma.zyn.app.bean.core.passenger.Passenger ;
import ma.zyn.app.bean.core.message.Conversation ;
import ma.zyn.app.bean.core.trajet.Trajet ;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MessageDaoTest {

@Autowired
    private MessageDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }


    @Test
    void shouldFindById(){
        Long id = 1L;
        Message entity = new Message();
        entity.setId(id);
        underTest.save(entity);
        Message loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Message entity = new Message();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Message loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Message> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Message> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Message given = constructSample(1);
        Message saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
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
