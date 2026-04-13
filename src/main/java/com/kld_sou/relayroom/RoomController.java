package com.kld_sou.relayroom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room/{roomId}")
public class RoomController {
  private final SimpMessagingTemplate broker;
  private final ConcurrentHashMap<String, String> rooms =
      new ConcurrentHashMap<>();

  @Autowired
  public RoomController(SimpMessagingTemplate broker) {
    this.broker = broker;
  }

  @GetMapping
  public ResponseEntity<Map<String, String>>
  getRoom(@PathVariable String roomId) {
    return ResponseEntity.ok(Map.of("data", rooms.getOrDefault(roomId, "")));
  }

  @PutMapping
  public ResponseEntity<Void>
  updateRoom(@PathVariable String roomId,
             @RequestBody Map<String, String> body) {
    String data = body.get("data");
    rooms.put(roomId, data);
    broker.convertAndSend(
        "/topic/room/" + roomId,
        (Object)Map.of("data", // explicit casting to make compiler calma
                       data));
    return ResponseEntity.ok().build();
  }
}
