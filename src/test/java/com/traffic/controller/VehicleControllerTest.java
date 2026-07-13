package com.traffic.controller;

import com.traffic.dto.VehicleDTO;
import com.traffic.entity.Vehicle;
import com.traffic.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for VehicleController
 */
public class VehicleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController vehicleController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();
    }

    @Test
    public void testGetAllVehicles() throws Exception {
        mockMvc.perform(get("/v1/vehicles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetVehicleById() throws Exception {
        VehicleDTO vehicleDTO = VehicleDTO.builder()
                .vehicleId(1L)
                .licensePlate("ABC123")
                .vehicleType("Car")
                .status("ACTIVE")
                .build();

        when(vehicleService.getVehicleById(1L)).thenReturn(vehicleDTO);

        mockMvc.perform(get("/v1/vehicles/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
