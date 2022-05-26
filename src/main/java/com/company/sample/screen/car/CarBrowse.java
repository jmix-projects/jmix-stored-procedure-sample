package com.company.sample.screen.car;

import io.jmix.ui.screen.*;
import com.company.sample.entity.Car;

@UiController("Car.browse")
@UiDescriptor("car-browse.xml")
@LookupComponent("carsTable")
public class CarBrowse extends StandardLookup<Car> {
}