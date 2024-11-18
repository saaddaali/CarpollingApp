package ma.zyn.app.unit.ws.facade.driver.passenger;

import ma.zyn.app.bean.core.passenger.Passenger;
import ma.zyn.app.service.impl.driver.passenger.PassengerDriverServiceImpl;
import ma.zyn.app.ws.facade.driver.passenger.PassengerRestDriver;
import ma.zyn.app.ws.converter.passenger.PassengerConverter;
import ma.zyn.app.ws.dto.passenger.PassengerDto;
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
public class PassengerRestDriverTest {

    private MockMvc mockMvc;

    @Mock
    private PassengerDriverServiceImpl service;
    @Mock
    private PassengerConverter converter;

    @InjectMocks
    private PassengerRestDriver controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllPassengerTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<PassengerDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<PassengerDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSavePassengerTest() throws Exception {
        // Mock data
        PassengerDto requestDto = new PassengerDto();
        Passenger entity = new Passenger();
        Passenger saved = new Passenger();
        PassengerDto savedDto = new PassengerDto();

        // Mock the converter to return the passenger object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved passenger DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<PassengerDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        PassengerDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved passenger DTO
        assertEquals(savedDto.getPhoto(), responseBody.getPhoto());
        assertEquals(savedDto.getAdresse(), responseBody.getAdresse());
        assertEquals(savedDto.getDateInscription(), responseBody.getDateInscription());
        assertEquals(savedDto.getEvaluation(), responseBody.getEvaluation());
        assertEquals(savedDto.getPassword(), responseBody.getPassword());
        assertEquals(savedDto.getAccountNonLocked(), responseBody.getAccountNonLocked());
        assertEquals(savedDto.getPasswordChanged(), responseBody.getPasswordChanged());
        assertEquals(savedDto.getUsername(), responseBody.getUsername());
        assertEquals(savedDto.getAccountNonExpired(), responseBody.getAccountNonExpired());
        assertEquals(savedDto.getCredentialsNonExpired(), responseBody.getCredentialsNonExpired());
        assertEquals(savedDto.getEnabled(), responseBody.getEnabled());
        assertEquals(savedDto.getEmail(), responseBody.getEmail());
    }

}
