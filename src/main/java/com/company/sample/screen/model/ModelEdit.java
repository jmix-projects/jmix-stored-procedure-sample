package com.company.sample.screen.model;

import io.jmix.ui.screen.*;
import com.company.sample.entity.Model;

@UiController("Model.edit")
@UiDescriptor("model-edit.xml")
@EditedEntityContainer("modelDc")
public class ModelEdit extends StandardEditor<Model> {
}