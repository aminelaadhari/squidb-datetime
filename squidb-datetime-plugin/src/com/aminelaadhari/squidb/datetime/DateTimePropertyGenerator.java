package com.aminelaadhari.squidb.datetime;

import com.yahoo.aptutils.model.DeclaredTypeName;
import com.yahoo.aptutils.utils.AptUtils;
import com.yahoo.aptutils.writer.JavaFileWriter;
import com.yahoo.squidb.processor.data.ModelSpec;
import com.yahoo.squidb.processor.plugins.defaults.properties.generators.BasicLongPropertyGenerator;

import java.io.IOException;
import java.util.Set;

import javax.lang.model.element.VariableElement;

public class DateTimePropertyGenerator extends BasicLongPropertyGenerator {
    private static final DeclaredTypeName JODA_DATETIME = new DeclaredTypeName("org.joda.time.DateTime");
    private final DeclaredTypeName fieldType;

    public DateTimePropertyGenerator(ModelSpec<?> modelSpec, VariableElement field, DeclaredTypeName fieldType, AptUtils utils) {
        super(modelSpec, field, utils);
        this.fieldType = fieldType;
    }

    // Add imports required by this property here
    @Override
    protected void registerAdditionalImports(Set<DeclaredTypeName> imports) {
        super.registerAdditionalImports(imports);
        imports.add(JODA_DATETIME);
    }

    // Defines the type passed/returned in get/set
    @Override
    public DeclaredTypeName getTypeForAccessors() {
        return fieldType;
    }

    // Generates getter implementation using the static helper class from earlier
    @Override
    protected void writeGetterBody(JavaFileWriter writer) throws IOException {
        writer.writeStringStatement("Long instant = this.containsNonNullValue(" + propertyName + ") ? this.get(" + propertyName + ") : null");
        writer.writeStringStatement("return instant == null ? null : new DateTime(instant)");
    }

    // Generates setter implementation using the static helper class from earlier
    @Override
    protected void writeSetterBody(JavaFileWriter writer, String argName) throws IOException {
        writer.writeStringStatement("this.set(" + propertyName + ", " + argName + " == null ? null : " + argName + ".getMillis())");
        writer.writeStringStatement("return this");
    }
}
