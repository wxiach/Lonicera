package cn.wxiach.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author wxiach 2023/11/3
 */
public class FreeMarkerTemplateEngine extends TemplateEngine {

    private final Configuration configuration;

    public FreeMarkerTemplateEngine() {
        this.configuration = createFreemarkerConfiguration();
    }

    @Override
    public String render(ModelAndView modelAndView) {
        try {
            StringWriter stringWriter = new StringWriter();

            Template template = this.configuration.getTemplate(modelAndView.getView());
            template.process(modelAndView.getModel(), stringWriter);

            return stringWriter.toString();
        } catch (IOException | TemplateException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private Configuration createFreemarkerConfiguration() {
        Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        configuration.setClassForTemplateLoading(FreeMarkerTemplateEngine.class, "/templates/");
        return configuration;
    }

}
