package com.kld_sou.relayroom;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void putThenGet() throws Exception {
    mockMvc
        .perform(put("/api/room/test_room")
                     .contentType(MediaType.APPLICATION_JSON)
                     .content("{\"data\":\"somthing_something\"}"))
        .andExpect(status().isOk());

    mockMvc.perform(get("/api/room/test_room"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").value("somthing_something"));
  }

  @Test
  void getNotfound() throws Exception {
    mockMvc.perform(get("/api/room/i_do_not_exist"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").value(""));
  }
}
