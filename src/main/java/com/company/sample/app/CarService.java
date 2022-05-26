package com.company.sample.app;

import com.company.sample.entity.Car;
import io.jmix.core.Stores;
import io.jmix.data.StoreAwareLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.List;

/**
 * The service demonstrates how the database function may be called and how the result may be mapped to the JPA entity
 * using the Java Persistence API (JPA)
 */
@Component
public class CarService {

    @Autowired
    private StoreAwareLocator storeAwareLocator;

    public List<Car> loadCarByYear(int year) {
        EntityManager entityManager = storeAwareLocator.getEntityManager(Stores.MAIN);
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("CAR_BY_YEAR", Car.class)
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .setParameter(1, year);

        List<Car> cars = storedProcedureQuery.getResultList();
        return cars;
    }
}