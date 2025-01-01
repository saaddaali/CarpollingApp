package ma.zyn.app.ws.converter.trajet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.BeanUtils;
import ma.zyn.app.utils.converter.AbstractConverterHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import ma.zyn.app.ws.converter.driver.DriverConverter;
import ma.zyn.app.bean.core.driver.Driver;
import ma.zyn.app.bean.core.trajet.Ville;


import ma.zyn.app.utils.util.StringUtil;
import ma.zyn.app.utils.util.DateUtil;
import ma.zyn.app.bean.core.trajet.Trajet;
import ma.zyn.app.ws.dto.trajet.TrajetDto;

@Component
public class TrajetConverter {

    @Autowired
    private DriverConverter driverConverter;
    @Autowired
    private VilleConverter villeConverter;
    private boolean villeDepart;
    private boolean villeDestination;
    private boolean driver;
    private boolean localisationSource;
    private boolean localisationDestination;

    public TrajetConverter() {
        initObject(true);
    }

    public Trajet toItem(TrajetDto dto) {
        if (dto == null) {
            return null;
        } else {
            Trajet item = new Trajet();
            if (StringUtil.isNotEmpty(dto.getId()))
                item.setId(dto.getId());
            if (StringUtil.isNotEmpty(dto.getHoraireDepart()))
                item.setHoraireDepart(DateUtil.stringEnToDate(dto.getHoraireDepart()));
            if (StringUtil.isNotEmpty(dto.getHoraireArrive()))
                item.setHoraireArrive(DateUtil.stringEnToDate(dto.getHoraireArrive()));
            if (StringUtil.isNotEmpty(dto.getPlacesDisponibles()))
                item.setPlacesDisponibles(dto.getPlacesDisponibles());
            if (StringUtil.isNotEmpty(dto.getPlacesMax()))
                item.setPlacesMax(dto.getPlacesMax());
            
            if (StringUtil.isNotEmpty(dto.getDateCreation()))
                item.setDateCreation(DateUtil.stringEnToDate(dto.getDateCreation()));
            if (this.villeDepart && dto.getVilleDepart() != null)
                item.setVilleDepart(villeConverter.toItem(dto.getVilleDepart()));
            if (this.villeDestination && dto.getVilleDestination() != null)
                item.setVilleDestination(villeConverter.toItem(dto.getVilleDestination()));
            if (this.driver && dto.getDriver() != null)
                item.setDriver(driverConverter.toItem(dto.getDriver()));
            if (this.localisationSource && dto.getLocalisationSource() != null)
                item.setLocalisationSource(villeConverter.toItem(dto.getLocalisationSource()));
            if (this.localisationDestination && dto.getLocalisationDestination() != null)
                item.setLocalisationDestination(villeConverter.toItem(dto.getLocalisationDestination()));
            if (dto.getPrix() != null)
                item.setPrix(new BigDecimal(String.valueOf(dto.getPrix())));


            return item;
        }
    }


    public TrajetDto toDto(Trajet item) {
        if (item == null) {
            return null;
        } else {
            TrajetDto dto = new TrajetDto();
            if (StringUtil.isNotEmpty(item.getId()))
                dto.setId(item.getId());
            if (item.getHoraireDepart() != null)
                dto.setHoraireDepart(DateUtil.dateTimeToString(item.getHoraireDepart()));
            if (item.getHoraireArrive() != null)
                dto.setHoraireArrive(DateUtil.dateTimeToString(item.getHoraireArrive()));
            if (StringUtil.isNotEmpty(item.getPlacesDisponibles()))
                dto.setPlacesDisponibles(item.getPlacesDisponibles());
            if (StringUtil.isNotEmpty(item.getPlacesMax()))
                dto.setPlacesMax(item.getPlacesMax());
            
            if (StringUtil.isNotEmpty(item.getPlacesMax()))
                dto.setPlacesMax(item.getPlacesMax());

            if (item.getDateCreation() != null)
                dto.setDateCreation(DateUtil.dateTimeToString(item.getDateCreation()));
            if (this.villeDepart && item.getVilleDepart() != null) {
                dto.setVilleDepart(villeConverter.toDto(item.getVilleDepart()));

            }
            if (this.villeDestination && item.getVilleDestination() != null) {
                dto.setVilleDestination(villeConverter.toDto(item.getVilleDestination()));

            }
            if (this.driver && item.getDriver() != null) {
                dto.setDriver(driverConverter.toDto(item.getDriver()));
            }

            if (this.localisationSource && item.getLocalisationSource() != null) {
                dto.setLocalisationSource(villeConverter.toDto(item.getLocalisationSource()));

            }
            if (this.localisationDestination && item.getLocalisationDestination() != null) {
                dto.setLocalisationDestination(villeConverter.toDto(item.getLocalisationDestination()));

            }

            if (item.getPrix() != null)
                dto.setPrix(item.getPrix());


            return dto;
        }
    }

    public void init(boolean value) {
        initObject(value);
    }

    public void initObject(boolean value) {
        this.villeDepart = value;
        this.villeDestination = value;
        this.driver = value;
        this.localisationSource = value;
        this.localisationDestination = value;
    }

    public List<Trajet> toItem(List<TrajetDto> dtos) {
        List<Trajet> items = new ArrayList<>();
        if (dtos != null && !dtos.isEmpty()) {
            for (TrajetDto dto : dtos) {
                items.add(toItem(dto));
            }
        }
        return items;
    }


    public List<TrajetDto> toDto(List<Trajet> items) {
        List<TrajetDto> dtos = new ArrayList<>();
        if (items != null && !items.isEmpty()) {
            for (Trajet item : items) {
                dtos.add(toDto(item));
            }
        }
        return dtos;
    }


    public void copy(TrajetDto dto, Trajet t) {
        BeanUtils.copyProperties(dto, t, AbstractConverterHelper.getNullPropertyNames(dto));
        if (t.getVilleDepart() == null && dto.getVilleDepart() != null) {
            t.setVilleDepart(new Ville());
        } else if (t.getVilleDepart() != null && dto.getVilleDepart() != null) {
            t.setVilleDepart(null);
            t.setVilleDepart(new Ville());
        }
        if (t.getVilleDestination() == null && dto.getVilleDestination() != null) {
            t.setVilleDestination(new Ville());
        } else if (t.getVilleDestination() != null && dto.getVilleDestination() != null) {
            t.setVilleDestination(null);
            t.setVilleDestination(new Ville());
        }
        if (t.getDriver() == null && dto.getDriver() != null) {
            t.setDriver(new Driver());
        } else if (t.getDriver() != null && dto.getDriver() != null) {
            t.setDriver(null);
            t.setDriver(new Driver());
        }
        if (t.getLocalisationSource() == null && dto.getLocalisationSource() != null) {
            t.setLocalisationSource(new Ville());
        } else if (t.getLocalisationSource() != null && dto.getLocalisationSource() != null) {
            t.setLocalisationSource(null);
            t.setLocalisationSource(new Ville());
        }
        if (t.getLocalisationDestination() == null && dto.getLocalisationDestination() != null) {
            t.setLocalisationDestination(new Ville());
        } else if (t.getLocalisationDestination() != null && dto.getLocalisationDestination() != null) {
            t.setLocalisationDestination(null);
            t.setLocalisationDestination(new Ville());
        }
        if (dto.getVilleDepart() != null)
            villeConverter.copy(dto.getVilleDepart(), t.getVilleDepart());
        if (dto.getVilleDestination() != null)
            villeConverter.copy(dto.getVilleDestination(), t.getVilleDestination());
        if (dto.getDriver() != null)
            driverConverter.copy(dto.getDriver(), t.getDriver());
        if (dto.getLocalisationSource() != null)
            villeConverter.copy(dto.getLocalisationSource(), t.getLocalisationSource());
        if (dto.getLocalisationDestination() != null)
            villeConverter.copy(dto.getLocalisationDestination(), t.getLocalisationDestination());
    }

    public List<Trajet> copy(List<TrajetDto> dtos) {
        List<Trajet> result = new ArrayList<>();
        if (dtos != null) {
            for (TrajetDto dto : dtos) {
                Trajet instance = new Trajet();
                copy(dto, instance);
                result.add(instance);
            }
        }
        return result.isEmpty() ? null : result;
    }


    public DriverConverter getDriverConverter() {
        return this.driverConverter;
    }

    public void setDriverConverter(DriverConverter driverConverter) {
        this.driverConverter = driverConverter;
    }

    public VilleConverter getVilleConverter() {
        return this.villeConverter;
    }

    public void setVilleConverter(VilleConverter villeConverter) {
        this.villeConverter = villeConverter;
    }

    public boolean isVilleDepart() {
        return this.villeDepart;
    }

    public void setVilleDepart(boolean villeDepart) {
        this.villeDepart = villeDepart;
    }

    public boolean isVilleDestination() {
        return this.villeDestination;
    }

    public void setVilleDestination(boolean villeDestination) {
        this.villeDestination = villeDestination;
    }

    public boolean isDriver() {
        return this.driver;
    }

    public void setDriver(boolean driver) {
        this.driver = driver;
    }

    public boolean isLocalisationSource() {
        return this.localisationSource;
    }

    public void setLocalisationSource(boolean localisationSource) {
        this.localisationSource = localisationSource;
    }

    public boolean isLocalisationDestination() {
        return this.localisationDestination;
    }

    public void setLocalisationDestination(boolean localisationDestination) {
        this.localisationDestination = localisationDestination;
    }
}
