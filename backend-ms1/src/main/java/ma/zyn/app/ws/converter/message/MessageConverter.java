package  ma.zyn.app.ws.converter.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zyn.app.utils.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;

import ma.zyn.app.ws.converter.driver.DriverConverter;
import ma.zyn.app.bean.core.driver.Driver;
import ma.zyn.app.ws.converter.passenger.PassengerConverter;
import ma.zyn.app.bean.core.passenger.Passenger;
import ma.zyn.app.bean.core.message.Conversation;
import ma.zyn.app.ws.converter.trajet.TrajetConverter;
import ma.zyn.app.bean.core.trajet.Trajet;



import ma.zyn.app.utils.util.StringUtil;
import ma.zyn.app.utils.util.DateUtil;
import ma.zyn.app.bean.core.message.Message;
import ma.zyn.app.ws.dto.message.MessageDto;

@Component
public class MessageConverter {

    @Autowired
    private DriverConverter driverConverter ;
    @Autowired
    private PassengerConverter passengerConverter ;
    @Autowired
    private ConversationConverter conversationConverter ;
    @Autowired
    private TrajetConverter trajetConverter ;
    private boolean trajet;
    private boolean driver;
    private boolean passenger;
    private boolean conversation;

    public  MessageConverter() {
        initObject(true);
    }

    public Message toItem(MessageDto dto) {
        if (dto == null) {
            return null;
        } else {
        Message item = new Message();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getContenu()))
                item.setContenu(dto.getContenu());
            if(StringUtil.isNotEmpty(dto.getDateEnvoi()))
                item.setDateEnvoi(DateUtil.stringEnToDate(dto.getDateEnvoi()));
            if(this.trajet && dto.getTrajet()!=null)
                item.setTrajet(trajetConverter.toItem(dto.getTrajet())) ;

            if(this.driver && dto.getDriver()!=null)
                item.setDriver(driverConverter.toItem(dto.getDriver())) ;

            if(this.passenger && dto.getPassenger()!=null)
                item.setPassenger(passengerConverter.toItem(dto.getPassenger())) ;

            if(this.conversation && dto.getConversation()!=null)
                item.setConversation(conversationConverter.toItem(dto.getConversation())) ;


            if(dto.getIsPassenger()!=null)
                item.setIsPassenger(dto.getIsPassenger());




        return item;
        }
    }


    public MessageDto toDto(Message item) {
        if (item == null) {
            return null;
        } else {
            MessageDto dto = new MessageDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getContenu()))
                dto.setContenu(item.getContenu());
            if(item.getDateEnvoi()!=null)
                dto.setDateEnvoi(DateUtil.dateTimeToString(item.getDateEnvoi()));
            if(this.trajet && item.getTrajet()!=null) {
                dto.setTrajet(trajetConverter.toDto(item.getTrajet())) ;

            }
            if(this.driver && item.getDriver()!=null) {
                dto.setDriver(driverConverter.toDto(item.getDriver())) ;

            }
            if(this.passenger && item.getPassenger()!=null) {
                dto.setPassenger(passengerConverter.toDto(item.getPassenger())) ;

            }
            if(this.conversation && item.getConversation()!=null) {
                dto.setConversation(conversationConverter.toDto(item.getConversation())) ;

            }

            if (item.getIsPassenger() != null)
                dto.setIsPassenger(item.getIsPassenger());


        return dto;
        }
    }

    public void init(boolean value) {
        initObject(value);
    }

    public void initObject(boolean value) {
        this.trajet = value;
        this.driver = value;
        this.passenger = value;
        this.conversation = value;
    }
	
    public List<Message> toItem(List<MessageDto> dtos) {
        List<Message> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (MessageDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<MessageDto> toDto(List<Message> items) {
        List<MessageDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Message item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(MessageDto dto, Message t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getTrajet() == null  && dto.getTrajet() != null){
            t.setTrajet(new Trajet());
        }else if (t.getTrajet() != null  && dto.getTrajet() != null){
            t.setTrajet(null);
            t.setTrajet(new Trajet());
        }
        if(t.getDriver() == null  && dto.getDriver() != null){
            t.setDriver(new Driver());
        }else if (t.getDriver() != null  && dto.getDriver() != null){
            t.setDriver(null);
            t.setDriver(new Driver());
        }
        if(t.getPassenger() == null  && dto.getPassenger() != null){
            t.setPassenger(new Passenger());
        }else if (t.getPassenger() != null  && dto.getPassenger() != null){
            t.setPassenger(null);
            t.setPassenger(new Passenger());
        }
        if(t.getConversation() == null  && dto.getConversation() != null){
            t.setConversation(new Conversation());
        }else if (t.getConversation() != null  && dto.getConversation() != null){
            t.setConversation(null);
            t.setConversation(new Conversation());
        }
        if (dto.getTrajet() != null)
        trajetConverter.copy(dto.getTrajet(), t.getTrajet());
        if (dto.getDriver() != null)
        driverConverter.copy(dto.getDriver(), t.getDriver());
        if (dto.getPassenger() != null)
        passengerConverter.copy(dto.getPassenger(), t.getPassenger());
        if (dto.getConversation() != null)
        conversationConverter.copy(dto.getConversation(), t.getConversation());
    }

    public List<Message> copy(List<MessageDto> dtos) {
        List<Message> result = new ArrayList<>();
        if (dtos != null) {
            for (MessageDto dto : dtos) {
                Message instance = new Message();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


    public DriverConverter getDriverConverter(){
        return this.driverConverter;
    }
    public void setDriverConverter(DriverConverter driverConverter ){
        this.driverConverter = driverConverter;
    }
    public PassengerConverter getPassengerConverter(){
        return this.passengerConverter;
    }
    public void setPassengerConverter(PassengerConverter passengerConverter ){
        this.passengerConverter = passengerConverter;
    }
    public ConversationConverter getConversationConverter(){
        return this.conversationConverter;
    }
    public void setConversationConverter(ConversationConverter conversationConverter ){
        this.conversationConverter = conversationConverter;
    }
    public TrajetConverter getTrajetConverter(){
        return this.trajetConverter;
    }
    public void setTrajetConverter(TrajetConverter trajetConverter ){
        this.trajetConverter = trajetConverter;
    }
    public boolean  isTrajet(){
        return this.trajet;
    }
    public void  setTrajet(boolean trajet){
        this.trajet = trajet;
    }
    public boolean  isDriver(){
        return this.driver;
    }
    public void  setDriver(boolean driver){
        this.driver = driver;
    }
    public boolean  isPassenger(){
        return this.passenger;
    }
    public void  setPassenger(boolean passenger){
        this.passenger = passenger;
    }
    public boolean  isConversation(){
        return this.conversation;
    }
    public void  setConversation(boolean conversation){
        this.conversation = conversation;
    }
}
