package ma.zyn.app.unit.ws.facade.passenger.vehicule;

import ma.zyn.app.bean.core.vehicule.Vehicule;
import ma.zyn.app.service.impl.passenger.vehicule.VehiculePassengerServiceImpl;
import ma.zyn.app.ws.facade.passenger.vehicule.VehiculeRestPassenger;
import ma.zyn.app.ws.converter.vehicule.VehiculeConverter;
import ma.zyn.app.ws.dto.vehicule.VehiculeDto;
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
public class VehiculeRestPassengerTest {

    private MockMvc mockMvc;

    @Mock
    private VehiculePassengerServiceImpl service;
    @Mock
    private VehiculeConverter converter;

    @InjectMocks
    private VehiculeRestPassenger controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllVehiculeTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<VehiculeDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<VehiculeDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSaveVehiculeTest() throws Exception {
        // Mock data
        VehiculeDto requestDto = new VehiculeDto();
        Vehicule entity = new Vehicule();
        Vehicule saved = new Vehicule();
        VehiculeDto savedDto = new VehiculeDto();

        // Mock the converter to return the vehicule object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved vehicule DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<VehiculeDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        VehiculeDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved vehicule DTO
        assertEquals(savedDto.getMarque(), responseBody.getMarque());
        assertEquals(savedDto.getModele(), responseBody.getModele());
        assertEquals(savedDto.getAnnee(), responseBody.getAnnee());
        assertEquals(savedDto.getCouleur(), responseBody.getCouleur());
        assertEquals(savedDto.getPlaqueImmatriculation(), responseBody.getPlaqueImmatriculation());
        assertEquals(savedDto.getCapacite(), responseBody.getCapacite());
        assertEquals(savedDto.getImage(), responseBody.getImage());
    }

}
