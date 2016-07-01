package com.aminelaadhari.squidb.datetime;

import com.yahoo.aptutils.model.DeclaredTypeName;
import com.yahoo.squidb.processor.data.ModelSpec;
import com.yahoo.squidb.processor.plugins.Plugin;
import com.yahoo.squidb.processor.plugins.PluginEnvironment;

import javax.lang.model.element.VariableElement;

public class DateTimePlugin extends Plugin {

    private static final DeclaredTypeName JODA_DATETIME = new DeclaredTypeName("org.joda.time.DateTime");

    public DateTimePlugin(ModelSpec<?> modelSpec, PluginEnvironment pluginEnv) {
        super(modelSpec, pluginEnv);
    }

    @Override
    public boolean processVariableElement(VariableElement field, DeclaredTypeName fieldType) {
        if (!JODA_DATETIME.equals(fieldType)) {
            return false;
        }
        modelSpec.addPropertyGenerator(new DateTimePropertyGenerator(modelSpec, field, fieldType, utils));
        return true;
    }
}
