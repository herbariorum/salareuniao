package com.digitalinnovation.one.saladereuniao.controller;

import com.digitalinnovation.one.saladereuniao.exception.ResourceNotFoundException;
import com.digitalinnovation.one.saladereuniao.model.Room;
import com.digitalinnovation.one.saladereuniao.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/rooms")
    public List<Room> getAllRooms(){
        return roomRepository.findAll();
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable(value = "id") Long roomId) throws ResourceNotFoundException {
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(()-> new ResourceNotFoundException("Room not found "+ roomId));
            return ResponseEntity.ok().body(room);

    }

    @PostMapping("/rooms")
    public Room createRoom(@Valid @RequestBody Room rom){
        return roomRepository.save(rom);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable(value = "id") Long roomId, @Valid @RequestBody Room roomDetail) throws ResourceNotFoundException{
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found this id: "+ roomId));
        room.setName(roomDetail.getName());
        room.setDate(roomDetail.getDate());
        room.setStartHour(roomDetail.getStartHour());
        room.setEndHour(roomDetail.getEndHour());

        final Room updateRoom = roomRepository.save(room);

        return ResponseEntity.ok(updateRoom);
    }


    @DeleteMapping("/rooms/{id}")
    public Map<String , Boolean> deleteRoom(@PathVariable(value = "id") Long roomId) throws ResourceNotFoundException{
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("Rom not found for this id "+ roomId));

        roomRepository.delete(room);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
