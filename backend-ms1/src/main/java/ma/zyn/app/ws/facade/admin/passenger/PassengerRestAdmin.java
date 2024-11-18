package  ma.zyn.app.ws.facade.admin.passenger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import java.util.Arrays;
import java.util.ArrayList;

import ma.zyn.app.bean.core.passenger.Passenger;
import ma.zyn.app.dao.criteria.core.passenger.PassengerCriteria;
import ma.zyn.app.service.facade.admin.passenger.PassengerAdminService;
import ma.zyn.app.ws.converter.passenger.PassengerConverter;
import ma.zyn.app.ws.dto.passenger.PassengerDto;
import ma.zyn.app.zynerator.controller.AbstractController;
import ma.zyn.app.zynerator.dto.AuditEntityDto;
import ma.zyn.app.zynerator.util.PaginatedList;


import ma.zyn.app.zynerator.security.bean.User;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import ma.zyn.app.zynerator.process.Result;


import org.springframework.web.multipart.MultipartFile;
import ma.zyn.app.zynerator.dto.FileTempDto;

@RestController
@RequestMapping("/api/admin/passenger/")
public class PassengerRestAdmin {




    @Operation(summary = "Finds a list of all passengers")
    @GetMapping("")
    public ResponseEntity<List<PassengerDto>> findAll() throws Exception {
        ResponseEntity<List<PassengerDto>> res = null;
        List<Passenger> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
            converter.initObject(true);
        List<PassengerDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds an optimized list of all passengers")
    @GetMapping("optimized")
    public ResponseEntity<List<PassengerDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<PassengerDto>> res = null;
        List<Passenger> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<PassengerDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a passenger by id")
    @GetMapping("id/{id}")
    public ResponseEntity<PassengerDto> findById(@PathVariable Long id) {
        Passenger t = service.findById(id);
        if (t != null) {
            converter.init(true);
            PassengerDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a passenger by email")
    @GetMapping("email/{email}")
    public ResponseEntity<PassengerDto> findByEmail(@PathVariable String email) {
	    Passenger t = service.findByReferenceEntity(new Passenger(email));
        if (t != null) {
            converter.init(true);
            PassengerDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  passenger")
    @PostMapping("")
    public ResponseEntity<PassengerDto> save(@RequestBody PassengerDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            Passenger myT = converter.toItem(dto);
            Passenger t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                PassengerDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  passenger")
    @PutMapping("")
    public ResponseEntity<PassengerDto> update(@RequestBody PassengerDto dto) throws Exception {
        ResponseEntity<PassengerDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Passenger t = service.findById(dto.getId());
            converter.copy(dto,t);
            Passenger updated = service.update(t);
            PassengerDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of passenger")
    @PostMapping("multiple")
    public ResponseEntity<List<PassengerDto>> delete(@RequestBody List<PassengerDto> dtos) throws Exception {
        ResponseEntity<List<PassengerDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<Passenger> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified passenger")
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


    @Operation(summary = "Finds a passenger and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<PassengerDto> findWithAssociatedLists(@PathVariable Long id) {
        Passenger loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        PassengerDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds passengers by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<PassengerDto>> findByCriteria(@RequestBody PassengerCriteria criteria) throws Exception {
        ResponseEntity<List<PassengerDto>> res = null;
        List<Passenger> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<PassengerDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated passengers by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody PassengerCriteria criteria) throws Exception {
        List<Passenger> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initObject(true);
        List<PassengerDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets passenger data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody PassengerCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<PassengerDto> findDtos(List<Passenger> list){
        converter.initObject(true);
        List<PassengerDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<PassengerDto> getDtoResponseEntity(PassengerDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }



    @Operation(summary = "Change password to the specified  utilisateur")
    @PutMapping("changePassword")
    public boolean changePassword(@RequestBody User dto) throws Exception {
        return service.changePassword(dto.getUsername(),dto.getPassword());
    }

   public PassengerRestAdmin(PassengerAdminService service, PassengerConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final PassengerAdminService service;
    private final PassengerConverter converter;





}
