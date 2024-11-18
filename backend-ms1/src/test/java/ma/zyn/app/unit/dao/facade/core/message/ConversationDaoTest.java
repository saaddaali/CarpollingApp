package ma.zyn.app.unit.dao.facade.core.message;

import ma.zyn.app.bean.core.message.Conversation;
import ma.zyn.app.dao.facade.core.message.ConversationDao;

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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class ConversationDaoTest {

@Autowired
    private ConversationDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }

    @Test
    void shouldFindByCode(){
        String code = "code-1";
        Conversation entity = new Conversation();
        entity.setCode(code);
        underTest.save(entity);
        Conversation loaded = underTest.findByCode(code);
        assertThat(loaded.getCode()).isEqualTo(code);
    }

    @Test
    void shouldDeleteByCode() {
        String code = "code-12345678";
        int result = underTest.deleteByCode(code);

        Conversation loaded = underTest.findByCode(code);
        assertThat(loaded).isNull();
        assertThat(result).isEqualTo(0);
    }

    @Test
    void shouldFindById(){
        Long id = 1L;
        Conversation entity = new Conversation();
        entity.setId(id);
        underTest.save(entity);
        Conversation loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Conversation entity = new Conversation();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Conversation loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Conversation> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Conversation> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Conversation given = constructSample(1);
        Conversation saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
    }

    private Conversation constructSample(int i) {
		Conversation given = new Conversation();
        given.setCode("code-"+i);
        given.setLibelle("libelle-"+i);
        given.setDescription("description-"+i);
        given.setDriver(new Driver(1L));
        given.setPassenger(new Passenger(1L));
        return given;
    }

}
