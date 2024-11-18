package ma.zyn.app.unit.ws.facade.driver.paiement;

import ma.zyn.app.bean.core.paiement.CarteBancaire;
import ma.zyn.app.service.impl.driver.paiement.CarteBancaireDriverServiceImpl;
import ma.zyn.app.ws.facade.driver.paiement.CarteBancaireRestDriver;
import ma.zyn.app.ws.converter.paiement.CarteBancaireConverter;
import ma.zyn.app.ws.dto.paiement.CarteBancaireDto;
import org.aspectj.lang.annotation.Before;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarteBancaireRestDriverTest {

    private MockMvc mockMvc;

    @Mock
    private CarteBancaireDriverServiceImpl service;
    @Mock
    private CarteBancaireConverter converter;

    @InjectMocks
    private CarteBancaireRestDriver controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllCarteBancaireTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<CarteBancaireDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<CarteBancaireDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSaveCarteBancaireTest() throws Exception {
        // Mock data
        CarteBancaireDto requestDto = new CarteBancaireDto();
        CarteBancaire entity = new CarteBancaire();
        CarteBancaire saved = new CarteBancaire();
        CarteBancaireDto savedDto = new CarteBancaireDto();

        // Mock the converter to return the carteBancaire object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved carteBancaire DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<CarteBancaireDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        CarteBancaireDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved carteBancaire DTO
        assertEquals(savedDto.getTitulaire(), responseBody.getTitulaire());
        assertEquals(savedDto.getNumero(), responseBody.getNumero());
        assertEquals(savedDto.getDateExpiration(), responseBody.getDateExpiration());
        assertEquals(savedDto.getCodeSecret(), responseBody.getCodeSecret());
    }

}
