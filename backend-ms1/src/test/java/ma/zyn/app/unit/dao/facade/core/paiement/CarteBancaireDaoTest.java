package ma.zyn.app.unit.dao.facade.core.paiement;

import ma.zyn.app.bean.core.paiement.CarteBancaire;
import ma.zyn.app.dao.facade.core.paiement.CarteBancaireDao;

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


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class CarteBancaireDaoTest {

@Autowired
    private CarteBancaireDao underTest;

    @BeforeEach
    void setUp() {
        underTest.deleteAll();
    }


    @Test
    void shouldFindById(){
        Long id = 1L;
        CarteBancaire entity = new CarteBancaire();
        entity.setId(id);
        underTest.save(entity);
        CarteBancaire loaded = underTest.findById(id).orElse(null);
        assertThat(loaded.getId()).isEqualTo(id);
    }

    @Test
    void shoulDeleteById() {
        Long id = 1L;
        CarteBancaire entity = new CarteBancaire();
        entity.setId(id);
        underTest.save(entity);

        underTest.deleteById(id);

        CarteBancaire loaded = underTest.findById(id).orElse(null);
        assertThat(loaded).isNull();
    }


    @Test
    void shouldFindAll() {
        // Given
        List<CarteBancaire> items = IntStream.rangeClosed(1, 10).mapToObj(i->constructSample(i)).collect(Collectors.toList());

        // Init
        items.forEach(underTest::save);

        // When
        List<CarteBancaire> loadedItems = underTest.findAll();

        // Then
        assertThat(loadedItems).isNotNull();
        assertThat(loadedItems.size()).isEqualTo(10);
    }

    @Test
    void shouldSave(){
        CarteBancaire given = constructSample(1);
        CarteBancaire saved = underTest.save(given);
        assertThat(saved.getId()).isNotNull();
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
