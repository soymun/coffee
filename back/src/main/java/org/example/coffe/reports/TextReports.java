package org.example.coffe.reports;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateException;
import net.sf.jooreports.templates.DocumentTemplateFactory;
import org.example.coffe.model.CommandReport;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TextReports {

    private final MinioClient minioClient;

    public File generate(List<CommandReport> value) throws IOException, DocumentTemplateException, InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory();
        DocumentTemplate template = documentTemplateFactory.getTemplate(minioClient.getObject(GetObjectArgs.builder().bucket("reports").object("SimpleTemplate.odt").build()));
        Map<String, Object> dataModel = Map.of("items", value);
        File output = File.createTempFile("report", ".odt");
        template.createDocument(dataModel, new FileOutputStream(output));
        Thread.sleep(10000);
        return output;
    }

}
