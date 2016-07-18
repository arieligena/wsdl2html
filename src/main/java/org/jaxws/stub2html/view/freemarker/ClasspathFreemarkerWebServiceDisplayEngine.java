package org.jaxws.stub2html.view.freemarker;

import java.io.IOException;

import org.jaxws.stub2html.view.JavaNameDisplayStrategy;
import org.jaxws.stub2html.view.simple.SimpleJavaNameDisplayStrategy;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * 
 * @author chenjianjx
 * 
 */
public class ClasspathFreemarkerWebServiceDisplayEngine extends FreemarkerWebServiceDisplayEngine {

    private String absoluteFtlClassPath;

    private ClasspathFreemarkerWebServiceDisplayEngine(JavaNameDisplayStrategy nameDisplayingStrategy, String absoluteFtlClassPath) {
        super(nameDisplayingStrategy);

        if (!absoluteFtlClassPath.startsWith("/")) {
            throw new IllegalArgumentException("Template's class-path has to start with '/'");
        }
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        configuration.setClassForTemplateLoading(ClasspathFreemarkerWebServiceDisplayEngine.class, "/");
        configuration.setLogTemplateExceptions(true);
        this.absoluteFtlClassPath = absoluteFtlClassPath;
    }

    public static FreemarkerWebServiceDisplayEngine createEngine(JavaNameDisplayStrategy nameDisplayingStrategy, String absoluteFtlClassPath) {
        return new ClasspathFreemarkerWebServiceDisplayEngine(nameDisplayingStrategy, absoluteFtlClassPath);
    }
    
    public static FreemarkerWebServiceDisplayEngine createEngine(SimpleJavaNameDisplayStrategy nameDisplayingStrategy) {
        String ftlPath = "/falabella.ftl";
        return createEngine(nameDisplayingStrategy, ftlPath);
    }
    
    protected Template getTemplate() {
        try {
            return configuration.getTemplate(absoluteFtlClassPath);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

 

}
