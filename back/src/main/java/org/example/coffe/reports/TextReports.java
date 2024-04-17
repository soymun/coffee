package org.example.coffe.reports;

import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateException;
import net.sf.jooreports.templates.DocumentTemplateFactory;
import org.example.coffe.model.CommandReport;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class TextReports {

    public File generate(List<CommandReport> value) throws IOException, DocumentTemplateException, InterruptedException {
        DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory();
        DocumentTemplate template = documentTemplateFactory.getTemplate(TextReports.class.getResourceAsStream("/SimpleTemplate.odt"));
        Map<String, Object> dataModel = Map.of("items", value);
        File output = File.createTempFile("report", ".odt");
        template.createDocument(dataModel, new FileOutputStream(output));
        Thread.sleep(10000);
        return output;
    }

}
