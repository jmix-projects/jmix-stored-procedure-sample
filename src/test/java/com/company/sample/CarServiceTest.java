package com.company.sample;

import com.company.sample.entity.Car;
import com.company.sample.app.CarService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class CarServiceTest {

    @Autowired
    private CarService carService;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    private static final Logger log = LoggerFactory.getLogger(CarServiceTest.class);

    @Test
    public void loadCarByYear() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
        transactionTemplate.executeWithoutResult(status -> {
            List<Car> cars = carService.loadCarByYear(2021);
            cars.forEach(car ->
                    log.info("Car: vin={}, year={}", car.getVin(), car.getYear()));
            Assert.isTrue(cars.size() > 0, "Cars collection should not be empty");
        });
    }
}
