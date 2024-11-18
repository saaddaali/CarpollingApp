package ma.zyn.app.unit.ws.facade.passenger.message;

import ma.zyn.app.bean.core.message.Message;
import ma.zyn.app.service.impl.passenger.message.MessagePassengerServiceImpl;
import ma.zyn.app.ws.facade.passenger.message.MessageRestPassenger;
import ma.zyn.app.ws.converter.message.MessageConverter;
import ma.zyn.app.ws.dto.message.MessageDto;
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
public class MessageRestPassengerTest {

    private MockMvc mockMvc;

    @Mock
    private MessagePassengerServiceImpl service;
    @Mock
    private MessageConverter converter;

    @InjectMocks
    private MessageRestPassenger controller;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void itShouldFindAllMessageTest() throws Exception {
        // Mock the service to return an empty list
        when(service.findAll()).thenReturn(Collections.emptyList());
        when(converter.toDto(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Call the controller method
        ResponseEntity<List<MessageDto>> result = controller.findAll();

        // Verify the result
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        // Response body should be empty list
        List<MessageDto> responseBody = result.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.size());
    }

    @Test
    public void itShouldSaveMessageTest() throws Exception {
        // Mock data
        MessageDto requestDto = new MessageDto();
        Message entity = new Message();
        Message saved = new Message();
        MessageDto savedDto = new MessageDto();

        // Mock the converter to return the message object when converting from DTO
        when(converter.toItem(requestDto)).thenReturn(entity);

        // Mock the service to return the saved client
        when(service.create(entity)).thenReturn(saved);

        // Mock the converter to return the saved message DTO
        when(converter.toDto(saved)).thenReturn(savedDto);

        // Call the controller method
        ResponseEntity<MessageDto> result = controller.save(requestDto);

        // Verify the result
        assertEquals(HttpStatus.CREATED, result.getStatusCode());

        // Verify the response body
        MessageDto responseBody = result.getBody();
        assertNotNull(responseBody);

        // Add assertions to compare the response body with the saved message DTO
        assertEquals(savedDto.getContenu(), responseBody.getContenu());
        assertEquals(savedDto.getDateEnvoi(), responseBody.getDateEnvoi());
    }

}
