package com.company.sample.screen.carwithmodel;

import io.jmix.ui.screen.*;
import com.company.sample.entity.CarWithModel;

@UiController("CarWithModel.edit")
@UiDescriptor("car-with-model-edit.xml")
@EditedEntityContainer("carWithModelDc")
public class CarWithModelEdit extends StandardEditor<CarWithModel> {
}