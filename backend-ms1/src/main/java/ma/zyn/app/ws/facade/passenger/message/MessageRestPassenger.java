package  ma.zyn.app.ws.facade.passenger.message;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.http.HttpStatus;

import ma.zyn.app.bean.core.message.Message;
import ma.zyn.app.dao.criteria.core.message.MessageCriteria;
import ma.zyn.app.service.facade.passenger.message.MessagePassengerService;
import ma.zyn.app.ws.converter.message.MessageConverter;
import ma.zyn.app.ws.dto.message.MessageDto;
import ma.zyn.app.utils.util.PaginatedList;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/passenger/message/")
public class MessageRestPassenger {




    @Operation(summary = "Finds a list of all messages")
    @GetMapping("")
    public ResponseEntity<List<MessageDto>> findAll() throws Exception {
        ResponseEntity<List<MessageDto>> res = null;
        List<Message> list = service.findAll();
        HttpStatus status = HttpStatus.NO_CONTENT;
            converter.initObject(true);
        List<MessageDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;
        res = new ResponseEntity<>(dtos, status);
        return res;
    }


    @Operation(summary = "Finds a message by id")
    @GetMapping("id/{id}")
    public ResponseEntity<MessageDto> findById(@PathVariable Long id) {
        Message t = service.findById(id);
        if (t != null) {
            converter.init(true);
            MessageDto dto = converter.toDto(t);
            return getDtoResponseEntity(dto);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @Operation(summary = "Saves the specified  message")
    @PostMapping("")
    public ResponseEntity<MessageDto> save(@RequestBody MessageDto dto) throws Exception {
        if(dto!=null){
            converter.init(true);
            Message myT = converter.toItem(dto);
            Message t = service.create(myT);
            if (t == null) {
                return new ResponseEntity<>(null, HttpStatus.IM_USED);
            }else{
                MessageDto myDto = converter.toDto(t);
                return new ResponseEntity<>(myDto, HttpStatus.CREATED);
            }
        }else {
            return new ResponseEntity<>(dto, HttpStatus.NO_CONTENT);
        }
    }

    @Operation(summary = "Updates the specified  message")
    @PutMapping("")
    public ResponseEntity<MessageDto> update(@RequestBody MessageDto dto) throws Exception {
        ResponseEntity<MessageDto> res ;
        if (dto.getId() == null || service.findById(dto.getId()) == null)
            res = new ResponseEntity<>(HttpStatus.CONFLICT);
        else {
            Message t = service.findById(dto.getId());
            converter.copy(dto,t);
            Message updated = service.update(t);
            MessageDto myDto = converter.toDto(updated);
            res = new ResponseEntity<>(myDto, HttpStatus.OK);
        }
        return res;
    }

    @Operation(summary = "Delete list of message")
    @PostMapping("multiple")
    public ResponseEntity<List<MessageDto>> delete(@RequestBody List<MessageDto> dtos) throws Exception {
        ResponseEntity<List<MessageDto>> res ;
        HttpStatus status = HttpStatus.CONFLICT;
        if (dtos != null && !dtos.isEmpty()) {
            converter.init(false);
            List<Message> ts = converter.toItem(dtos);
            service.delete(ts);
            status = HttpStatus.OK;
        }
        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Delete the specified message")
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

    @Operation(summary = "find by trajet id")
    @GetMapping("trajet/id/{id}")
    public List<MessageDto> findByTrajetId(@PathVariable Long id){
        return findDtos(service.findByTrajetId(id));
    }
    @Operation(summary = "delete by trajet id")
    @DeleteMapping("trajet/id/{id}")
    public int deleteByTrajetId(@PathVariable Long id){
        return service.deleteByTrajetId(id);
    }
    @Operation(summary = "find by driver id")
    @GetMapping("driver/id/{id}")
    public List<MessageDto> findByDriverId(@PathVariable Long id){
        return findDtos(service.findByDriverId(id));
    }
    @Operation(summary = "delete by driver id")
    @DeleteMapping("driver/id/{id}")
    public int deleteByDriverId(@PathVariable Long id){
        return service.deleteByDriverId(id);
    }
    @Operation(summary = "find by passenger id")
    @GetMapping("passenger/id/{id}")
    public List<MessageDto> findByPassengerId(@PathVariable Long id){
        return findDtos(service.findByPassengerId(id));
    }
    @Operation(summary = "delete by passenger id")
    @DeleteMapping("passenger/id/{id}")
    public int deleteByPassengerId(@PathVariable Long id){
        return service.deleteByPassengerId(id);
    }
    @Operation(summary = "find by conversation id")
    @GetMapping("conversation/id/{id}")
    public List<MessageDto> findByConversationId(@PathVariable Long id){
        return findDtos(service.findByConversationId(id));
    }
    @Operation(summary = "delete by conversation id")
    @DeleteMapping("conversation/id/{id}")
    public int deleteByConversationId(@PathVariable Long id){
        return service.deleteByConversationId(id);
    }

    @Operation(summary = "Finds a message and associated list by id")
    @GetMapping("detail/id/{id}")
    public ResponseEntity<MessageDto> findWithAssociatedLists(@PathVariable Long id) {
        Message loaded =  service.findWithAssociatedLists(id);
        converter.init(true);
        MessageDto dto = converter.toDto(loaded);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(summary = "Finds messages by criteria")
    @PostMapping("find-by-criteria")
    public ResponseEntity<List<MessageDto>> findByCriteria(@RequestBody MessageCriteria criteria) throws Exception {
        ResponseEntity<List<MessageDto>> res = null;
        List<Message> list = service.findByCriteria(criteria);
        HttpStatus status = HttpStatus.NO_CONTENT;
        converter.initObject(true);
        List<MessageDto> dtos  = converter.toDto(list);
        if (dtos != null && !dtos.isEmpty())
            status = HttpStatus.OK;

        res = new ResponseEntity<>(dtos, status);
        return res;
    }

    @Operation(summary = "Finds paginated messages by criteria")
    @PostMapping("find-paginated-by-criteria")
    public ResponseEntity<PaginatedList> findPaginatedByCriteria(@RequestBody MessageCriteria criteria) throws Exception {
        List<Message> list = service.findPaginatedByCriteria(criteria, criteria.getPage(), criteria.getMaxResults(), criteria.getSortOrder(), criteria.getSortField());
        converter.initObject(true);
        List<MessageDto> dtos = converter.toDto(list);
        PaginatedList paginatedList = new PaginatedList();
        paginatedList.setList(dtos);
        if (dtos != null && !dtos.isEmpty()) {
            int dateSize = service.getDataSize(criteria);
            paginatedList.setDataSize(dateSize);
        }
        return new ResponseEntity<>(paginatedList, HttpStatus.OK);
    }

    @Operation(summary = "Gets message data size by criteria")
    @PostMapping("data-size-by-criteria")
    public ResponseEntity<Integer> getDataSize(@RequestBody MessageCriteria criteria) throws Exception {
        int count = service.getDataSize(criteria);
        return new ResponseEntity<Integer>(count, HttpStatus.OK);
    }
	
	public List<MessageDto> findDtos(List<Message> list){
        converter.initObject(true);
        List<MessageDto> dtos = converter.toDto(list);
        return dtos;
    }

    private ResponseEntity<MessageDto> getDtoResponseEntity(MessageDto dto) {
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }




   public MessageRestPassenger(MessagePassengerService service, MessageConverter converter){
        this.service = service;
        this.converter = converter;
    }

    private final MessagePassengerService service;
    private final MessageConverter converter;





}
