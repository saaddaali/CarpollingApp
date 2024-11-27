package  ma.zyn.app.ws.converter.passenger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zyn.app.utils.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;

import ma.zyn.app.ws.converter.paiement.CarteBancaireConverter;
import ma.zyn.app.bean.core.paiement.CarteBancaire;



import ma.zyn.app.utils.util.StringUtil;
import ma.zyn.app.utils.util.DateUtil;
import ma.zyn.app.bean.core.passenger.Passenger;
import ma.zyn.app.ws.dto.passenger.PassengerDto;

@Component
public class PassengerConverter {

    @Autowired
    private CarteBancaireConverter carteBancaireConverter ;
    private boolean carteBancaire;

    public  PassengerConverter() {
        initObject(true);
    }

    public Passenger toItem(PassengerDto dto) {
        if (dto == null) {
            return null;
        } else {
        Passenger item = new Passenger();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getPhoto()))
                item.setPhoto(dto.getPhoto());
            if(StringUtil.isNotEmpty(dto.getAdresse()))
                item.setAdresse(dto.getAdresse());
            if(StringUtil.isNotEmpty(dto.getDateInscription()))
                item.setDateInscription(DateUtil.stringEnToDate(dto.getDateInscription()));
            if(StringUtil.isNotEmpty(dto.getEvaluation()))
                item.setEvaluation(dto.getEvaluation());
            if(StringUtil.isNotEmpty(dto.getPassword()))
                item.setPassword(dto.getPassword());
            item.setAccountNonLocked(dto.getAccountNonLocked());
            item.setPasswordChanged(dto.getPasswordChanged());
            if(StringUtil.isNotEmpty(dto.getUsername()))
                item.setUsername(dto.getUsername());
            item.setAccountNonExpired(dto.getAccountNonExpired());
            item.setCredentialsNonExpired(dto.getCredentialsNonExpired());
            item.setEnabled(dto.getEnabled());
            if(StringUtil.isNotEmpty(dto.getEmail()))
                item.setEmail(dto.getEmail());
            if(this.carteBancaire && dto.getCarteBancaire()!=null)
                item.setCarteBancaire(carteBancaireConverter.toItem(dto.getCarteBancaire())) ;



            //item.setRoles(dto.getRoles());

        return item;
        }
    }


    public PassengerDto toDto(Passenger item) {
        if (item == null) {
            return null;
        } else {
            PassengerDto dto = new PassengerDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getPhoto()))
                dto.setPhoto(item.getPhoto());
            if(StringUtil.isNotEmpty(item.getAdresse()))
                dto.setAdresse(item.getAdresse());
            if(item.getDateInscription()!=null)
                dto.setDateInscription(DateUtil.dateTimeToString(item.getDateInscription()));
            if(StringUtil.isNotEmpty(item.getEvaluation()))
                dto.setEvaluation(item.getEvaluation());
            if(StringUtil.isNotEmpty(item.getAccountNonLocked()))
                dto.setAccountNonLocked(item.getAccountNonLocked());
            if(StringUtil.isNotEmpty(item.getPasswordChanged()))
                dto.setPasswordChanged(item.getPasswordChanged());
            if(StringUtil.isNotEmpty(item.getUsername()))
                dto.setUsername(item.getUsername());
            if(StringUtil.isNotEmpty(item.getAccountNonExpired()))
                dto.setAccountNonExpired(item.getAccountNonExpired());
            if(StringUtil.isNotEmpty(item.getCredentialsNonExpired()))
                dto.setCredentialsNonExpired(item.getCredentialsNonExpired());
            if(StringUtil.isNotEmpty(item.getEnabled()))
                dto.setEnabled(item.getEnabled());
            if(StringUtil.isNotEmpty(item.getEmail()))
                dto.setEmail(item.getEmail());
            if(this.carteBancaire && item.getCarteBancaire()!=null) {
                dto.setCarteBancaire(carteBancaireConverter.toDto(item.getCarteBancaire())) ;

            }


        return dto;
        }
    }

    public void init(boolean value) {
        initObject(value);
    }

    public void initObject(boolean value) {
        this.carteBancaire = value;
    }
	
    public List<Passenger> toItem(List<PassengerDto> dtos) {
        List<Passenger> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (PassengerDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<PassengerDto> toDto(List<Passenger> items) {
        List<PassengerDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Passenger item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(PassengerDto dto, Passenger t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getCarteBancaire() == null  && dto.getCarteBancaire() != null){
            t.setCarteBancaire(new CarteBancaire());
        }else if (t.getCarteBancaire() != null  && dto.getCarteBancaire() != null){
            t.setCarteBancaire(null);
            t.setCarteBancaire(new CarteBancaire());
        }
        if (dto.getCarteBancaire() != null)
        carteBancaireConverter.copy(dto.getCarteBancaire(), t.getCarteBancaire());
    }

    public List<Passenger> copy(List<PassengerDto> dtos) {
        List<Passenger> result = new ArrayList<>();
        if (dtos != null) {
            for (PassengerDto dto : dtos) {
                Passenger instance = new Passenger();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


    public CarteBancaireConverter getCarteBancaireConverter(){
        return this.carteBancaireConverter;
    }
    public void setCarteBancaireConverter(CarteBancaireConverter carteBancaireConverter ){
        this.carteBancaireConverter = carteBancaireConverter;
    }
    public boolean  isCarteBancaire(){
        return this.carteBancaire;
    }
    public void  setCarteBancaire(boolean carteBancaire){
        this.carteBancaire = carteBancaire;
    }
}
