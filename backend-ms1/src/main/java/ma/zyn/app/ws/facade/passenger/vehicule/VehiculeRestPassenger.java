package  ma.zyn.app.ws.facade.passenger.vehicule;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;

import ma.zyn.app.bean.core.vehicule.Vehicule;
import ma.zyn.app.dao.criteria.core.vehicule.VehiculeCriteria;
import ma.zyn.app.service.facade.passenger.vehicule.VehiculePassengerService;
import ma.zyn.app.ws.converter.vehicule.VehiculeConverter;
import ma.zyn.app.ws.dto.vehicule.VehiculeDto;
import ma.zyn.app.utils.util.PaginatedList;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/passenger/vehicule/")
public class VehiculeRestPassenger {




    @Operation(summary = "Finds a list of all vehicules")
    @GetMapping("")
    public ResponseEntity<List<VehiculeDto>> findAll() throws Exception {
        ResponseEntity<List<VehiculeDto>> res = null;
        List<Vehicule> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
            converter.initObject(true);
        List<VehiculeDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all vehicules")
    @GetMapping("optimized")
    public ResponseEntity<List<VehiculeDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<VehiculeDto>> res = null;
        List<Vehicule> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<VehiculeDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a vehicule by id")
    @GetMapping("id/{id}")
    public ResponseEntity<VehiculeDto> findById(@PathVariable Long id) {
        Vehicule t = service.findById(id);
        if (t != null) {
            converter.init(true);
            VehiculeDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a vehicule by plaqueImmatriculation")
    @GetMapping("plaqueImmatriculation/{plaqueImmatriculation}")
    public ResponseEntity<VehiculeDto> findByPlaqueImmatriculation(@PathVariable String plaqueImmatriculation) {
	    Vehicule t = service.findByReferenceEntity(new Vehicule(plaqueImmatriculation));
        if (t != null) {
            converter.init(true);
            VehiculeDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  vehicule")
    @PostMapping("")
    public ResponseEntity<VehiculeDto> save(@RequestBody VehiculeDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            Vehicule myT = converter.toItem(dto);
            Vehicule t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                VehiculeDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  vehicule")
    @PutMapping("")
    public ResponseEntity<VehiculeDto> update(@RequestBody VehiculeDto dto) throws Exception {
        ResponseEntity<VehiculeDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Vehicule t = service.findById(dto.getId());
            converter.copy(dto,t);
            Vehicule updated = service.update(t);
            VehiculeDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of vehicule")
    @PostMapping("multiple")
    public ResponseEntity<List<VehiculeDto>> delete(@RequestBody List<VehiculeDto> dtos) throws Exception {
        ResponseEntity<List<VehiculeDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<Vehicule> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified vehicule")
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

    @Operation(summary = "find by driver id")
    @GetMapping("driver/id/{id}")
    public List<VehiculeDto> findByDriverId(@PathVariable Long id){
        return findDtos(service.findByDriverId(id));
    }
    @Operation(summary = "delete by driver id")
    @DeleteMapping("driver/id/{id}")
    public int deleteByDriverId(@PathVariable Long id){
        return service.deleteByDriverId(id);
    }

    @Operation(summary = "Finds a vehicule and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<VehiculeDto> findWithAssociatedLists(@PathVariable Long id) {
        Vehicule loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        VehiculeDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds vehicules by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<VehiculeDto>> findByCriteria(@RequestBody VehiculeCriteria criteria) throws Exception {
        ResponseEntity<List<VehiculeDto>> res = null;
        List<Vehicule> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<VehiculeDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated vehicules by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody VehiculeCriteria criteria) throws Exception {
        List<Vehicule> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initObject(true);
        List<VehiculeDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets vehicule data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody VehiculeCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<VehiculeDto> findDtos(List<Vehicule> list){
        converter.initObject(true);
        List<VehiculeDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<VehiculeDto> getDtoResponseEntity(VehiculeDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public VehiculeRestPassenger(VehiculePassengerService service, VehiculeConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final VehiculePassengerService service;
    private final VehiculeConverter converter;





}
