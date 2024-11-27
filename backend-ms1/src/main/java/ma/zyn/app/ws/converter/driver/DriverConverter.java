package  ma.zyn.app.ws.converter.driver;

import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zyn.app.utils.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;




import ma.zyn.app.utils.util.StringUtil;
import ma.zyn.app.utils.util.DateUtil;
import ma.zyn.app.bean.core.driver.Driver;
import ma.zyn.app.ws.dto.driver.DriverDto;

@Component
public class DriverConverter {



    public Driver toItem(DriverDto dto) {
        if (dto == null) {
            return null;
        } else {
        Driver item = new Driver();
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


            //item.setRoles(dto.getRoles());

        return item;
        }
    }


    public DriverDto toDto(Driver item) {
        if (item == null) {
            return null;
        } else {
            DriverDto dto = new DriverDto();
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


        return dto;
        }
    }


	
    public List<Driver> toItem(List<DriverDto> dtos) {
        List<Driver> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (DriverDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<DriverDto> toDto(List<Driver> items) {
        List<DriverDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Driver item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(DriverDto dto, Driver t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
    }

    public List<Driver> copy(List<DriverDto> dtos) {
        List<Driver> result = new ArrayList<>();
        if (dtos != null) {
            for (DriverDto dto : dtos) {
                Driver instance = new Driver();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


}
