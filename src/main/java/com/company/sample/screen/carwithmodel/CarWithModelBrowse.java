package com.company.sample.screen.carwithmodel;

import com.company.sample.app.CarWithModelService;
import io.jmix.core.LoadContext;
import io.jmix.ui.screen.*;
import com.company.sample.entity.CarWithModel;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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