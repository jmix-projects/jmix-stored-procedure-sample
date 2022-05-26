# Jmix Database Stored Procedures Sample

The sample projects demonstrates how you can read data using database functions with parameters and then display the results on Jmix screens.

## Display the Function Result Using DTO Entity

The project contains the following entities:

* Car
* Model

There is the following function in PostreSQL database (the function is automatically created in database migration scrips):

```sql
create or replace function car_with_model_by_year(year_ int)
returns table(id UUID, vin text, year_ int, model text)
as $$ select c.id, c.vin, c.year_, m.name
      from CAR c left join MODEL m on c.model_id = m.id 
      where c.year_ = $1 $$
language SQL;
```

There is a DTO entity [CarWithModel](src/main/java/com/company/sample/entity/CarWithModel.java).

The [CarWithModelService](src/main/java/com/company/sample/app/CarWithModelService.java) is responsible for reading the data from the database using Spring `JdbcTemplate` and for mapping the result to `CarWithModel` instances:

```java
@Component
public class CarWithModelService {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private Metadata metadata;
    
    public List<CarWithModel> loadCarWithModelByYear(int year) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("year", year);

        RowMapper<CarWithModel> rowMapper = (rs, rowNum) -> {
            CarWithModel carWithModel = metadata.create(CarWithModel.class);
            carWithModel.setVin(rs.getString("VIN"));
            carWithModel.setYear(rs.getInt("YEAR_"));
            carWithModel.setModel(rs.getString("MODEL"));
            return carWithModel;
        };
        List<CarWithModel> carWithModels = jdbcTemplate.query("select * from car_with_model_by_year(:year)",
                parameterSource,
                rowMapper);

        return carWithModels;
    }
}
```

The [CarWithModelBrowse](src/main/java/com/company/sample/screen/carwithmodel/CarWithModelBrowse.java) displays the result of the service invocation:

```java
@UiController("CarWithModel.browse")
@UiDescriptor("car-with-model-browse.xml")
@LookupComponent("carWithModelsTable")
public class CarWithModelBrowse extends StandardLookup<CarWithModel> {

    @Autowired
    private CarWithModelService carWithModelService;

    @Install(to = "carWithModelsDl", target = Target.DATA_LOADER)
    private List<CarWithModel> carWithModelsDlLoadDelegate(LoadContext<CarWithModel> loadContext) {
        return carWithModelService.loadCarWithModelByYear(2021);
    }
}
```

## Call Database Function Using Java Persistence API

You may also call database functions and convert the result into JPA entities using JPA.

In the database there is a function that returns car information filtering it by year:

```sql
create or replace function car_by_year(year_ int)
returns table(id UUID, vin text, year_ int)
as $$ select c.id, c.vin, c.year_
    from CAR c where c.year_ = $1 $$
language SQL;
```

The [CarService](src/main/java/com/company/sample/app/CarService.java) calls the database function and maps the result to JPA entities:

```java
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
```

You may check how the `CarService` works using the [CarServiceTest](src/test/java/com/company/sample/CarServiceTest.java) 