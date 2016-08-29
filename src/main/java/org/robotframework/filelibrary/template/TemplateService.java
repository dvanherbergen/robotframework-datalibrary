package org.robotframework.filelibrary.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.robotframework.filelibrary.FileLibraryException;
import org.robotframework.filelibrary.context.TemplateContext;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class TemplateService {

	private Configuration config;

	public TemplateService() {

		String templatePath = new File("").getAbsolutePath();
		System.out.println("Using template path '" + templatePath + "'.");
		
		try {
			config = new Configuration(Configuration.VERSION_2_3_25);
			config.setDirectoryForTemplateLoading(new File(templatePath));
			config.setDefaultEncoding("UTF-8");
			config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			config.setLogTemplateExceptions(false);
		} catch (IOException e) {
			throw new FileLibraryException(e);
		}
		
	}

	public void generateFile(String templateFile, String outputFile) {
		
		Writer writer = null;
		
		try {
			Template template = config.getTemplate(templateFile);
			File outFile = new File(outputFile);
			writer = new FileWriter(outFile);
			template.process(TemplateContext.getCurrentContext().getValues(), writer);
			System.out.println("Created '" + outFile.getAbsolutePath() + "' from template '" + template.getName() + "'");
		} catch (IOException | TemplateException e) {
			throw new FileLibraryException(e);
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					throw new FileLibraryException(e);
				}
			}
		}
		
	}
}
