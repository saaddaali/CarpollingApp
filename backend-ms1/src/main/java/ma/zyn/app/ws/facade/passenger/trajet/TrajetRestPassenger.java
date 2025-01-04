package  ma.zyn.app.ws.facade.passenger.trajet;

import io.swagger.v3.oas.annotations.Operation;

import ma.zyn.app.bean.core.driver.Driver;
import ma.zyn.app.bean.core.passenger.Passenger;
import ma.zyn.app.service.facade.passenger.driver.DriverPassengerService;
import ma.zyn.app.utils.security.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import ma.zyn.app.bean.core.trajet.Trajet;
import ma.zyn.app.dao.criteria.core.trajet.TrajetCriteria;
import ma.zyn.app.service.facade.passenger.trajet.TrajetPassengerService;
import ma.zyn.app.ws.converter.trajet.TrajetConverter;
import ma.zyn.app.ws.dto.trajet.TrajetDto;
import ma.zyn.app.utils.util.PaginatedList;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static ma.zyn.app.utils.security.common.SecurityUtil.getCurrentUser;

@RestController
@RequestMapping("/api/passenger/trajet/")
public class TrajetRestPassenger {




    @Operation(summary = "Finds a list of all trajets")
    @GetMapping("")
    public ResponseEntity<List<TrajetDto>> findAll() throws Exception {
        ResponseEntity<List<TrajetDto>> res = null;
        List<Trajet> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
            converter.initObject(true);
        List<TrajetDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }


    @Operation(summary = "Finds a trajet by id")
    @GetMapping("id/{id}")
    public ResponseEntity<TrajetDto> findById(@PathVariable Long id) {
        Trajet t = service.findById(id);
        if (t != null) {
            converter.init(true);
            TrajetDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Saves the specified  trajet")
    @PostMapping("")
    public ResponseEntity<TrajetDto> save(@RequestBody TrajetDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            Trajet myT = converter.toItem(dto);
            String username = getCurrentUser().getUsername();
            Driver driver = driverService.findByUsername(username);
            myT.setDriver(driver);
            Trajet t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                TrajetDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  trajet")
    @PutMapping("")
    public ResponseEntity<TrajetDto> update(@RequestBody TrajetDto dto) throws Exception {
        ResponseEntity<TrajetDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Trajet t = service.findById(dto.getId());
            converter.copy(dto,t);
            Trajet updated = service.update(t);
            TrajetDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of trajet")
    @PostMapping("multiple")
    public ResponseEntity<List<TrajetDto>> delete(@RequestBody List<TrajetDto> dtos) throws Exception {
        ResponseEntity<List<TrajetDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<Trajet> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified trajet")
    @DeleteMapping("id/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable Long id) throws Exception {
        ResponseEntity<Long> res;
        HttpStatus status = HttpStatus.PRECONDITION_FAILED;
        if (id != null) {
            boolean resultDelete = service.deleteById(id);
            if (resultDelete) {
                status = HttpStatus.OK;
            }
        }
        res = new ResponseEntity<>(id, status);
        return res;
    }

    @Operation(summary = "find by villeDepart id")
    @GetMapping("villeDepart/id/{id}")
    public List<TrajetDto> findByVilleDepartId(@PathVariable Long id){
        return findDtos(service.findByVilleDepartId(id));
    }
    @Operation(summary = "delete by villeDepart id")
    @DeleteMapping("villeDepart/id/{id}")
    public int deleteByVilleDepartId(@PathVariable Long id){
        return service.deleteByVilleDepartId(id);
    }
    @Operation(summary = "find by villeDestination id")
    @GetMapping("villeDestination/id/{id}")
    public List<TrajetDto> findByVilleDestinationId(@PathVariable Long id){
        return findDtos(service.findByVilleDestinationId(id));
    }
    @Operation(summary = "delete by villeDestination id")
    @DeleteMapping("villeDestination/id/{id}")
    public int deleteByVilleDestinationId(@PathVariable Long id){
        return service.deleteByVilleDestinationId(id);
    }
    @Operation(summary = "find by localisationSource id")
    @GetMapping("localisationSource/id/{id}")
    public List<TrajetDto> findByLocalisationSourceId(@PathVariable Long id){
        return findDtos(service.findByLocalisationSourceId(id));
    }
    @Operation(summary = "delete by localisationSource id")
    @DeleteMapping("localisationSource/id/{id}")
    public int deleteByLocalisationSourceId(@PathVariable Long id){
        return service.deleteByLocalisationSourceId(id);
    }
    @Operation(summary = "find by localisationDestination id")
    @GetMapping("localisationDestination/id/{id}")
    public List<TrajetDto> findByLocalisationDestinationId(@PathVariable Long id){
        return findDtos(service.findByLocalisationDestinationId(id));
    }
    @Operation(summary = "delete by localisationDestination id")
    @DeleteMapping("localisationDestination/id/{id}")
    public int deleteByLocalisationDestinationId(@PathVariable Long id){
        return service.deleteByLocalisationDestinationId(id);
    }

    @Operation(summary = "Finds a trajet and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<TrajetDto> findWithAssociatedLists(@PathVariable Long id) {
        Trajet loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        TrajetDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds trajets by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<TrajetDto>> findByCriteria(@RequestBody TrajetCriteria criteria) throws Exception {
        ResponseEntity<List<TrajetDto>> res = null;
        List<Trajet> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<TrajetDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds trajets current user")
    @GetMapping("find-by-current-user")
    public ResponseEntity<List<TrajetDto>> findByCurrentUse() throws Exception {
        ResponseEntity<List<TrajetDto>> res = null;
        List<Trajet> list = service.findAllOptimizedDriver();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<TrajetDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated trajets by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody TrajetCriteria criteria) throws Exception {
        List<Trajet> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initObject(true);
        List<TrajetDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets trajet data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody TrajetCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<TrajetDto> findDtos(List<Trajet> list){
        converter.initObject(true);
        List<TrajetDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<TrajetDto> getDtoResponseEntity(TrajetDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }






   public TrajetRestPassenger(TrajetPassengerService service, TrajetConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final TrajetPassengerService service;

    @Autowired
    private  DriverPassengerService driverService;
    private final TrajetConverter converter;





}
