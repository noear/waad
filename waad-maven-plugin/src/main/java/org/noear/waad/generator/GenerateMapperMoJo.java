package org.noear.waad.generator;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.noear.waad.generator.mapper.XmlSqlMapperGenerator;
import org.noear.waad.generator.service.XmlSqlServiceGenerator;

import java.io.File;


@Mojo(name="generate-mapper")
public class GenerateMapperMoJo extends AbstractMojo {
    @Parameter(defaultValue = "${basedir}")
    private File baseDir;

    @Parameter(defaultValue = "${project.build.sourceDirectory}", required = true, readonly = true)
    private File sourceDir;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        //getLog().info(baseDir.getAbsolutePath());
        //getLog().info(sourceDir.getAbsolutePath());

        System.out.println("[Waad] Start building mapper files:");
        XmlSqlMapperGenerator.generate(baseDir, sourceDir);
        XmlSqlServiceGenerator.generate(baseDir, sourceDir);
    }
}