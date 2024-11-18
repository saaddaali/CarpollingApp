package ma.zyn.app.unit.ws.facade.passenger.driver;

import ma.zyn.app.bean.core.driver.Driver;
import ma.zyn.app.service.impl.passenger.driver.DriverPassengerServiceImpl;
import ma.zyn.app.ws.facade.passenger.driver.DriverRestPassenger;
import ma.zyn.app.ws.converter.driver.DriverConverter;
import ma.zyn.app.ws.dto.driver.DriverDto;
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
public class DriverRestPassengerTest {

    private MockMvc mockMvc;

    @Mock
    private DriverPassengerServiceImpl service;
    @Mock
    private DriverConverter converter;

    @InjectMocks
    private DriverRestPassenger controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllDriverTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<DriverDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<DriverDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSaveDriverTest() throws Exception {
        // Mock data
        DriverDto requestDto = new DriverDto();
        Driver entity = new Driver();
        Driver saved = new Driver();
        DriverDto savedDto = new DriverDto();

        // Mock the converter to return the driver object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved driver DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<DriverDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        DriverDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved driver DTO
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
