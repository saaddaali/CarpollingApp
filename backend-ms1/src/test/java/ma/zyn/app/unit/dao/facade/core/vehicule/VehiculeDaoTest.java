package ma.zyn.app.unit.dao.facade.core.vehicule;

import ma.zyn.app.bean.core.vehicule.Vehicule;
import ma.zyn.app.dao.facade.core.vehicule.VehiculeDao;

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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class VehiculeDaoTest {

@Autowired
    private VehiculeDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }


    @Test
    void shouldFindById(){
        Long id = 1L;
        Vehicule entity = new Vehicule();
        entity.setId(id);
        underTest.save(entity);
        Vehicule loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        Vehicule entity = new Vehicule();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        Vehicule loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<Vehicule> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<Vehicule> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        Vehicule given = constructSample(1);
        Vehicule saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
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
