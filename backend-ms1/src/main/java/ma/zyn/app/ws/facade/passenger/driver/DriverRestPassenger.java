package ma.zyn.app.ws.facade.passenger.driver;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;

import ma.zyn.app.bean.core.driver.Driver;
import ma.zyn.app.dao.criteria.core.driver.DriverCriteria;
import ma.zyn.app.service.facade.passenger.driver.DriverPassengerService;
import ma.zyn.app.ws.converter.driver.DriverConverter;
import ma.zyn.app.ws.dto.driver.DriverDto;
import ma.zyn.app.utils.util.PaginatedList;


import ma.zyn.app.utils.security.bean.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/passenger/driver/")
public class DriverRestPassenger {


    @Operation(summary = "Finds a list of all drivers")
    @GetMapping("")
    public ResponseEntity<List<DriverDto>> findAll() throws Exception {
        ResponseEntity<List<DriverDto>> res = null;
        List<Driver> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<DriverDto> dtos = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Verify the Driver driver")
    @PostMapping("verify")
    public ResponseEntity<DriverDto> verify(String cin, String fullName) {
       boolean t =service.verifyDriver(cin, fullName);
        if (t) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Finds an optimized list of all drivers")
    @GetMapping("optimized")
    public ResponseEntity<List<DriverDto>> findAllOptimized() throws Exception {
        ResponseEntity<List<DriverDto>> res = null;
        List<Driver> list = service.findAllOptimized();
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<DriverDto> dtos = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds a driver by id")
    @GetMapping("id/{id}")
    public ResponseEntity<DriverDto> findById(@PathVariable Long id) {
        Driver t = service.findById(id);
        if (t != null) {
            DriverDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Finds a driver by email")
    @GetMapping("email/{email}")
    public ResponseEntity<DriverDto> findByEmail(@PathVariable String email) {
        Driver t = service.findByReferenceEntity(new Driver(email));
        if (t != null) {
            DriverDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Saves the specified  driver")
    @PostMapping("")
    public ResponseEntity<DriverDto> save(@RequestBody DriverDto dto) throws Exception {
        if (dto != null) {
            Driver myT = converter.toItem(dto);
            Driver t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            } else {
                DriverDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        } else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  driver")
    @PutMapping("")
    public ResponseEntity<DriverDto> update(@RequestBody DriverDto dto) throws Exception {
        ResponseEntity<DriverDto> res;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Driver t = service.findById(dto.getId());
            converter.copy(dto, t);
            Driver updated = service.update(t);
            DriverDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of driver")
    @PostMapping("multiple")
    public ResponseEntity<List<DriverDto>> delete(@RequestBody List<DriverDto> dtos) throws Exception {
        ResponseEntity<List<DriverDto>> res;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            List<Driver> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified driver")
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


    @Operation(summary = "Finds a driver and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<DriverDto> findWithAssociatedLists(@PathVariable Long id) {
        Driver loaded = service.findWithAssociatedLists(id);
        DriverDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds drivers by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<DriverDto>> findByCriteria(@RequestBody DriverCriteria criteria) throws Exception {
        ResponseEntity<List<DriverDto>> res = null;
        List<Driver> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        List<DriverDto> dtos = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated drivers by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody DriverCriteria criteria) throws Exception {
        List<Driver> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        List<DriverDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets driver data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody DriverCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }

    public List<DriverDto> findDtos(List<Driver> list) {
        List<DriverDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<DriverDto> getDtoResponseEntity(DriverDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @Operation(summary = "Change password to the specified  utilisateur")
    @PutMapping("changePassword")
    public boolean changePassword(@RequestBody User dto) throws Exception {
        return service.changePassword(dto.getUsername(), dto.getPassword());
    }

    public DriverRestPassenger(DriverPassengerService service, DriverConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    private final DriverPassengerService service;
    private final DriverConverter converter;


}
