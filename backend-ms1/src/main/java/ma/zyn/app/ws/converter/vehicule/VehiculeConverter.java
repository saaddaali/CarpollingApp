package  ma.zyn.app.ws.converter.vehicule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zyn.app.zynerator.converter.AbstractConverterHelper;

import java.util.ArrayList;
import java.util.List;

import ma.zyn.app.ws.converter.driver.DriverConverter;
import ma.zyn.app.bean.core.driver.Driver;



import ma.zyn.app.zynerator.util.StringUtil;
import ma.zyn.app.zynerator.converter.AbstractConverter;
import ma.zyn.app.zynerator.util.DateUtil;
import ma.zyn.app.bean.core.vehicule.Vehicule;
import ma.zyn.app.ws.dto.vehicule.VehiculeDto;

@Component
public class VehiculeConverter {

    @Autowired
    private DriverConverter driverConverter ;
    private boolean driver;

    public  VehiculeConverter() {
        initObject(true);
    }

    public Vehicule toItem(VehiculeDto dto) {
        if (dto == null) {
            return null;
        } else {
        Vehicule item = new Vehicule();
            if(StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if(StringUtil.isNotEmpty(dto.getMarque()))
                item.setMarque(dto.getMarque());
            if(StringUtil.isNotEmpty(dto.getModele()))
                item.setModele(dto.getModele());
            if(StringUtil.isNotEmpty(dto.getAnnee()))
                item.setAnnee(dto.getAnnee());
            if(StringUtil.isNotEmpty(dto.getCouleur()))
                item.setCouleur(dto.getCouleur());
            if(StringUtil.isNotEmpty(dto.getPlaqueImmatriculation()))
                item.setPlaqueImmatriculation(dto.getPlaqueImmatriculation());
            if(StringUtil.isNotEmpty(dto.getCapacite()))
                item.setCapacite(dto.getCapacite());
            if(StringUtil.isNotEmpty(dto.getImage()))
                item.setImage(dto.getImage());
            if(this.driver && dto.getDriver()!=null)
                item.setDriver(driverConverter.toItem(dto.getDriver())) ;




        return item;
        }
    }


    public VehiculeDto toDto(Vehicule item) {
        if (item == null) {
            return null;
        } else {
            VehiculeDto dto = new VehiculeDto();
            if(StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if(StringUtil.isNotEmpty(item.getMarque()))
                dto.setMarque(item.getMarque());
            if(StringUtil.isNotEmpty(item.getModele()))
                dto.setModele(item.getModele());
            if(StringUtil.isNotEmpty(item.getAnnee()))
                dto.setAnnee(item.getAnnee());
            if(StringUtil.isNotEmpty(item.getCouleur()))
                dto.setCouleur(item.getCouleur());
            if(StringUtil.isNotEmpty(item.getPlaqueImmatriculation()))
                dto.setPlaqueImmatriculation(item.getPlaqueImmatriculation());
            if(StringUtil.isNotEmpty(item.getCapacite()))
                dto.setCapacite(item.getCapacite());
            if(StringUtil.isNotEmpty(item.getImage()))
                dto.setImage(item.getImage());
            if(this.driver && item.getDriver()!=null) {
                dto.setDriver(driverConverter.toDto(item.getDriver())) ;

            }


        return dto;
        }
    }

    public void init(boolean value) {
        initObject(value);
    }

    public void initObject(boolean value) {
        this.driver = value;
    }
	
    public List<Vehicule> toItem(List<VehiculeDto> dtos) {
        List<Vehicule> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (VehiculeDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<VehiculeDto> toDto(List<Vehicule> items) {
        List<VehiculeDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Vehicule item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(VehiculeDto dto, Vehicule t) {
		BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if(t.getDriver() == null  && dto.getDriver() != null){
            t.setDriver(new Driver());
        }else if (t.getDriver() != null  && dto.getDriver() != null){
            t.setDriver(null);
            t.setDriver(new Driver());
        }
        if (dto.getDriver() != null)
        driverConverter.copy(dto.getDriver(), t.getDriver());
    }

    public List<Vehicule> copy(List<VehiculeDto> dtos) {
        List<Vehicule> result = new ArrayList<>();
        if (dtos != null) {
            for (VehiculeDto dto : dtos) {
                Vehicule instance = new Vehicule();
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
    public boolean  isDriver(){
        return this.driver;
    }
    public void  setDriver(boolean driver){
        this.driver = driver;
    }
}
