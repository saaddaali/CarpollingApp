package  ma.zyn.app.ws.converter.reservation;

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
import ma.zyn.app.ws.converter.paiement.CarteBancaireConverter;
import ma.zyn.app.bean.core.paiement.CarteBancaire;
import ma.zyn.app.ws.converter.message.ConversationConverter;
import ma.zyn.app.bean.core.message.Conversation;
import ma.zyn.app.ws.converter.trajet.TrajetConverter;
import ma.zyn.app.bean.core.trajet.Trajet;



import ma.zyn.app.utils.util.StringUtil;
import ma.zyn.app.utils.util.DateUtil;
import ma.zyn.app.bean.core.reservation.Reservation;
import ma.zyn.app.ws.dto.reservation.ReservationDto;

@Component
public class ReservationConverter {

    @Autowired
    private DriverConverter driverConverter ;
    @Autowired
    private PassengerConverter passengerConverter ;
    @Autowired
    private CarteBancaireConverter carteBancaireConverter ;
    @Autowired
    private ConversationConverter conversationConverter ;
    @Autowired
    private TrajetConverter trajetConverter ;
    private boolean trajet;
    private boolean passenger;
    private boolean driver;
    private boolean carteBancaire;
    private boolean conversation;

    public  ReservationConverter() {
        initObject(true);
    }

    public Reservation toItem(ReservationDto dto) {
        if (dto == null) {
            return null;
        } else {
        Reservation item = new Reservation();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getDateReservation()))
                item.setDateReservation(DateUtil.stringEnToDate(dto.getDateReservation()));
            if(StringUtil.isNotEmpty(dto.getMontant()))
                item.setMontant(dto.getMontant());
            if(StringUtil.isNotEmpty(dto.getDatePaiement()))
                item.setDatePaiement(DateUtil.stringEnToDate(dto.getDatePaiement()));
            if(StringUtil.isNotEmpty(dto.getEvaluation()))
                item.setEvaluation(dto.getEvaluation());
            if(this.trajet && dto.getTrajet()!=null)
                item.setTrajet(trajetConverter.toItem(dto.getTrajet())) ;

            if(this.passenger && dto.getPassenger()!=null)
                item.setPassenger(passengerConverter.toItem(dto.getPassenger())) ;

            if(this.driver && dto.getDriver()!=null)
                item.setDriver(driverConverter.toItem(dto.getDriver())) ;

            if(this.carteBancaire && dto.getCarteBancaire()!=null)
                item.setCarteBancaire(carteBancaireConverter.toItem(dto.getCarteBancaire())) ;

            if(this.conversation && dto.getConversation()!=null)
                item.setConversation(conversationConverter.toItem(dto.getConversation())) ;




        return item;
        }
    }


    public ReservationDto toDto(Reservation item) {
        if (item == null) {
            return null;
        } else {
            ReservationDto dto = new ReservationDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(item.getDateReservation()!=null)
                dto.setDateReservation(DateUtil.dateTimeToString(item.getDateReservation()));
            if(StringUtil.isNotEmpty(item.getMontant()))
                dto.setMontant(item.getMontant());
            if(item.getDatePaiement()!=null)
                dto.setDatePaiement(DateUtil.dateTimeToString(item.getDatePaiement()));
            if(StringUtil.isNotEmpty(item.getEvaluation()))
                dto.setEvaluation(item.getEvaluation());
            if(this.trajet && item.getTrajet()!=null) {
                dto.setTrajet(trajetConverter.toDto(item.getTrajet())) ;

            }
            if(this.passenger && item.getPassenger()!=null) {
                dto.setPassenger(passengerConverter.toDto(item.getPassenger())) ;

            }
            if(this.driver && item.getDriver()!=null) {
                dto.setDriver(driverConverter.toDto(item.getDriver())) ;

            }
            if(this.carteBancaire && item.getCarteBancaire()!=null) {
                dto.setCarteBancaire(carteBancaireConverter.toDto(item.getCarteBancaire())) ;

            }
            if(this.conversation && item.getConversation()!=null) {
                dto.setConversation(conversationConverter.toDto(item.getConversation())) ;

            }


        return dto;
        }
    }

    public void init(boolean value) {
        initObject(value);
    }

    public void initObject(boolean value) {
        this.trajet = value;
        this.passenger = value;
        this.driver = value;
        this.carteBancaire = value;
        this.conversation = value;
    }
	
    public List<Reservation> toItem(List<ReservationDto> dtos) {
        List<Reservation> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (ReservationDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<ReservationDto> toDto(List<Reservation> items) {
        List<ReservationDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Reservation item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(ReservationDto dto, Reservation t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getTrajet() == null  && dto.getTrajet() != null){
            t.setTrajet(new Trajet());
        }else if (t.getTrajet() != null  && dto.getTrajet() != null){
            t.setTrajet(null);
            t.setTrajet(new Trajet());
        }
        if(t.getPassenger() == null  && dto.getPassenger() != null){
            t.setPassenger(new Passenger());
        }else if (t.getPassenger() != null  && dto.getPassenger() != null){
            t.setPassenger(null);
            t.setPassenger(new Passenger());
        }
        if(t.getDriver() == null  && dto.getDriver() != null){
            t.setDriver(new Driver());
        }else if (t.getDriver() != null  && dto.getDriver() != null){
            t.setDriver(null);
            t.setDriver(new Driver());
        }
        if(t.getCarteBancaire() == null  && dto.getCarteBancaire() != null){
            t.setCarteBancaire(new CarteBancaire());
        }else if (t.getCarteBancaire() != null  && dto.getCarteBancaire() != null){
            t.setCarteBancaire(null);
            t.setCarteBancaire(new CarteBancaire());
        }
        if(t.getConversation() == null  && dto.getConversation() != null){
            t.setConversation(new Conversation());
        }else if (t.getConversation() != null  && dto.getConversation() != null){
            t.setConversation(null);
            t.setConversation(new Conversation());
        }
        if (dto.getTrajet() != null)
        trajetConverter.copy(dto.getTrajet(), t.getTrajet());
        if (dto.getPassenger() != null)
        passengerConverter.copy(dto.getPassenger(), t.getPassenger());
        if (dto.getDriver() != null)
        driverConverter.copy(dto.getDriver(), t.getDriver());
        if (dto.getCarteBancaire() != null)
        carteBancaireConverter.copy(dto.getCarteBancaire(), t.getCarteBancaire());
        if (dto.getConversation() != null)
        conversationConverter.copy(dto.getConversation(), t.getConversation());
    }

    public List<Reservation> copy(List<ReservationDto> dtos) {
        List<Reservation> result = new ArrayList<>();
        if (dtos != null) {
            for (ReservationDto dto : dtos) {
                Reservation instance = new Reservation();
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
    public CarteBancaireConverter getCarteBancaireConverter(){
        return this.carteBancaireConverter;
    }
    public void setCarteBancaireConverter(CarteBancaireConverter carteBancaireConverter ){
        this.carteBancaireConverter = carteBancaireConverter;
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
    public boolean  isPassenger(){
        return this.passenger;
    }
    public void  setPassenger(boolean passenger){
        this.passenger = passenger;
    }
    public boolean  isDriver(){
        return this.driver;
    }
    public void  setDriver(boolean driver){
        this.driver = driver;
    }
    public boolean  isCarteBancaire(){
        return this.carteBancaire;
    }
    public void  setCarteBancaire(boolean carteBancaire){
        this.carteBancaire = carteBancaire;
    }
    public boolean  isConversation(){
        return this.conversation;
    }
    public void  setConversation(boolean conversation){
        this.conversation = conversation;
    }
}
