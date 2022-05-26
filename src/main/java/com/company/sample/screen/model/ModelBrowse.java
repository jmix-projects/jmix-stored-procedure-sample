package com.company.sample.screen.model;

import io.jmix.ui.screen.*;
import com.company.sample.entity.Model;

@UiController("Model.browse")
@UiDescriptor("model-browse.xml")
@LookupComponent("modelsTable")
public class ModelBrowse extends StandardLookup<Model> {
}