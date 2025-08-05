package com.backend.backend.services;

import com.backend.backend.models.DriverDTO;
import com.backend.backend.repositories.DriverRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;


@ExtendWith(MockitoExtension.class)
public class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverService driverService;

    @Test
    public void testCreateDriver001() {
        DriverDTO driver1 = new DriverDTO();
        driver1.setName("Driver 1");
        driver1.setLicenseNumber("123456789");
        driver1.setActive(true);
        Mockito.when(driverRepository.save(Mockito.any())).thenReturn(driver1);
        DriverDTO createdDriver = driverService.createDriver(driver1);
        assert createdDriver != null;
        assert createdDriver.getName().equals("Driver 1");
    }
    @Test
    public void testCreateDriver002() {
        DriverDTO driver1 = new DriverDTO();
        driver1.setName("Driver 1");
        driver1.setActive(true);
        DriverDTO createdDriver = driverService.createDriver(driver1);
        assert createdDriver == null;
    }

    @Test
    public void testListDrivers001() {
        DriverDTO driver1 = new DriverDTO();
        driver1.setName("Driver 1");
        driver1.setLicenseNumber("123456789");
        driver1.setActive(true);
        Mockito.when(driverRepository.findByActiveTrue()).thenReturn(List.of(driver1));
        List<DriverDTO> drivers = driverService.listDrivers();
        assert drivers.size() == 1;
        assert drivers.get(0).getName().equals("Driver 1");
    }
}
