package  ma.zyn.app.ws.converter.paiement;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zyn.app.utils.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;




import ma.zyn.app.utils.util.StringUtil;
import ma.zyn.app.utils.util.DateUtil;
import ma.zyn.app.bean.core.paiement.CarteBancaire;
import ma.zyn.app.ws.dto.paiement.CarteBancaireDto;

@Component
public class CarteBancaireConverter {



    public CarteBancaire toItem(CarteBancaireDto dto) {
        if (dto == null) {
            return null;
        } else {
        CarteBancaire item = new CarteBancaire();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getTitulaire()))
                item.setTitulaire(dto.getTitulaire());
            if(StringUtil.isNotEmpty(dto.getNumero()))
                item.setNumero(dto.getNumero());
            if(StringUtil.isNotEmpty(dto.getDateExpiration()))
                item.setDateExpiration(DateUtil.stringEnToDate(dto.getDateExpiration()));
            if(StringUtil.isNotEmpty(dto.getCodeSecret()))
                item.setCodeSecret(dto.getCodeSecret());



        return item;
        }
    }


    public CarteBancaireDto toDto(CarteBancaire item) {
        if (item == null) {
            return null;
        } else {
            CarteBancaireDto dto = new CarteBancaireDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getTitulaire()))
                dto.setTitulaire(item.getTitulaire());
            if(StringUtil.isNotEmpty(item.getNumero()))
                dto.setNumero(item.getNumero());
            if(item.getDateExpiration()!=null)
                dto.setDateExpiration(DateUtil.dateTimeToString(item.getDateExpiration()));
            if(StringUtil.isNotEmpty(item.getCodeSecret()))
                dto.setCodeSecret(item.getCodeSecret());


        return dto;
        }
    }


	
    public List<CarteBancaire> toItem(List<CarteBancaireDto> dtos) {
        List<CarteBancaire> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (CarteBancaireDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<CarteBancaireDto> toDto(List<CarteBancaire> items) {
        List<CarteBancaireDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (CarteBancaire item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(CarteBancaireDto dto, CarteBancaire t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<CarteBancaire> copy(List<CarteBancaireDto> dtos) {
        List<CarteBancaire> result = new ArrayList<>();
        if (dtos != null) {
            for (CarteBancaireDto dto : dtos) {
                CarteBancaire instance = new CarteBancaire();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
