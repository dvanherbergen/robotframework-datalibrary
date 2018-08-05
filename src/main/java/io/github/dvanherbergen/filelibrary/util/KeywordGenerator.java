package io.github.dvanherbergen.filelibrary.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;

import org.robotframework.javalib.library.AnnotationLibrary;

import io.github.dvanherbergen.filelibrary.FileLibrary;

/**
 * KeywordGenerator based on SwingLibraryKeywords from wojtek. The
 * KeywordGenerator creates a static Python file containing all the
 * documentation for the FileLibrary keywords. When LibDoc is used to generate
 * documentation from FileLibrary.py, FileLibrary.py uses the generated file
 * as input.
 */
public class KeywordGenerator {

	private final AnnotationLibrary annotationLibrary = new AnnotationLibrary(
			"io/github/dvanherbergen/filelibrary/keyword/**/*.class");

	public static void main(String[] args) {
		KeywordGenerator generator = new KeywordGenerator();
		String target = "keywords.py";
		if (args.length == 1) {
			target = args[0];
		}
		generator.generate(target);
	}

	public void generate(String target) {
		try {
			System.out.println("Generating keywords list...");

			String[] keywords = annotationLibrary.getKeywordNames();
			Arrays.sort(keywords);
			File outFile = new File(target);
			System.out.println("target: " + outFile.getCanonicalPath());

			BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
			writer.write("'''\nThis file is generated automatically and should not be edited.\n'''\n");

			writer.write("keywords = [");
			for (int i = 0; i < keywords.length; i++)
				writer.write("'" + keywords[i] + "', ");
			writer.write("]\n");

			writer.write("keyword_arguments = {");
			for (int i = 0; i < keywords.length; i++) {
				writer.write("'" + keywords[i] + "': ");
				String[] args = annotationLibrary.getKeywordArguments(keywords[i]);
				writer.write("[");
				for (int j = 0; j < args.length; j++)
					writer.write("'" + args[j] + "', ");
				writer.write("],\n");
			}
			writer.write("}\n");

			writer.write("keyword_documentation = {");
			for (int i = 0; i < keywords.length; i++) {
				writer.write("'" + keywords[i] + "': ");
				String docs = annotationLibrary.getKeywordDocumentation(keywords[i]);
				docs = docs.replace("\n", "\\n");
				docs = docs.replace("'", "\\n");
				writer.write("'" + docs + "',\n");
			}

			writer.write("'__intro__': ");
			String intro = FileLibrary.LIBRARY_DOCUMENTATION.replace("\n", "\\n").replace("'", "\\n");
			writer.write("'" + intro + "',\n");

			writer.write("}\n");
			writer.close();

			System.out.println("Keyword list written successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
