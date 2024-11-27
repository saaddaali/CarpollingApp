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



import ma.zyn.app.utils.util.StringUtil;
import ma.zyn.app.bean.core.message.Conversation;
import ma.zyn.app.ws.dto.message.ConversationDto;

@Component
public class ConversationConverter {

    @Autowired
    private DriverConverter driverConverter ;
    @Autowired
    private PassengerConverter passengerConverter ;
    private boolean driver;
    private boolean passenger;

    public  ConversationConverter() {
        initObject(true);
    }

    public Conversation toItem(ConversationDto dto) {
        if (dto == null) {
            return null;
        } else {
        Conversation item = new Conversation();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getCode()))
                item.setCode(dto.getCode());
            if(StringUtil.isNotEmpty(dto.getLibelle()))
                item.setLibelle(dto.getLibelle());
            if(StringUtil.isNotEmpty(dto.getDescription()))
                item.setDescription(dto.getDescription());
            if(this.driver && dto.getDriver()!=null)
                item.setDriver(driverConverter.toItem(dto.getDriver())) ;

            if(this.passenger && dto.getPassenger()!=null)
                item.setPassenger(passengerConverter.toItem(dto.getPassenger())) ;




        return item;
        }
    }


    public ConversationDto toDto(Conversation item) {
        if (item == null) {
            return null;
        } else {
            ConversationDto dto = new ConversationDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getCode()))
                dto.setCode(item.getCode());
            if(StringUtil.isNotEmpty(item.getLibelle()))
                dto.setLibelle(item.getLibelle());
            if(StringUtil.isNotEmpty(item.getDescription()))
                dto.setDescription(item.getDescription());
            if(this.driver && item.getDriver()!=null) {
                dto.setDriver(driverConverter.toDto(item.getDriver())) ;

            }
            if(this.passenger && item.getPassenger()!=null) {
                dto.setPassenger(passengerConverter.toDto(item.getPassenger())) ;

            }


        return dto;
        }
    }

    public void init(boolean value) {
        initObject(value);
    }

    public void initObject(boolean value) {
        this.driver = value;
        this.passenger = value;
    }
	
    public List<Conversation> toItem(List<ConversationDto> dtos) {
        List<Conversation> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (ConversationDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<ConversationDto> toDto(List<Conversation> items) {
        List<ConversationDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Conversation item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(ConversationDto dto, Conversation t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
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
        if (dto.getDriver() != null)
        driverConverter.copy(dto.getDriver(), t.getDriver());
        if (dto.getPassenger() != null)
        passengerConverter.copy(dto.getPassenger(), t.getPassenger());
    }

    public List<Conversation> copy(List<ConversationDto> dtos) {
        List<Conversation> result = new ArrayList<>();
        if (dtos != null) {
            for (ConversationDto dto : dtos) {
                Conversation instance = new Conversation();
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
}
