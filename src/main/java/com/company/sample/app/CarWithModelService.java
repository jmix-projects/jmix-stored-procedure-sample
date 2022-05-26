package com.company.sample.app;

import com.company.sample.entity.CarWithModel;
import io.jmix.core.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarWithModelService {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private Metadata metadata;

    /**
     * Method executes the car_with_model_by_year database function and maps the result to {@link CarWithModel} DTO
     * entity list
     *
     * @param year car production year
     * @return a list of CarWithModel DTO entities that describes car produced in the given year
     */
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