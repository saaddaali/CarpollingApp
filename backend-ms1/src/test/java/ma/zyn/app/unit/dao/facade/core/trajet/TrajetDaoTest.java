package ma.zyn.app.unit.dao.facade.core.trajet;

import ma.zyn.app.bean.core.trajet.Trajet;
import ma.zyn.app.dao.facade.core.trajet.TrajetDao;

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
import ma.zyn.app.bean.core.trajet.Ville ;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TrajetDaoTest {

@Autowired
    private TrajetDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }


    @Test
    void shouldFindById(){
        Long id = 1L;
        Trajet entity = new Trajet();
        entity.setId(id);
        underTest.save(entity);
        Trajet loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Trajet entity = new Trajet();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Trajet loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Trajet> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Trajet> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Trajet given = constructSample(1);
        Trajet saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
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
