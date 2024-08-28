package me.glicz.airflow.api.generator;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import me.glicz.airflow.api.generator.entity.EntityTypesGenerator;
import me.glicz.airflow.api.generator.item.ItemTypesGenerator;
import net.minecraft.SharedConstants;
import net.minecraft.server.Bootstrap;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        OptionParser optionParser = new OptionParser();
        OptionSpec<File> sourceFolderOption = optionParser.accepts("sourceFolder").withRequiredArg().ofType(File.class);

        OptionSet optionSet = optionParser.parse(args);
        File sourceFolder = optionSet.valueOf(sourceFolderOption);

        SharedConstants.tryDetectVersion();
        String version = SharedConstants.getCurrentVersion().getName();

        Bootstrap.bootStrap();
        Bootstrap.validate();

        new EntityTypesGenerator().run(version, sourceFolder);
        new ItemTypesGenerator().run(version, sourceFolder);
    }
}
