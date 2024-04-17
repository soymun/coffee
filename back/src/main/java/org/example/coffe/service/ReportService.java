package org.example.coffe.service;

import io.minio.errors.*;
import net.sf.jooreports.templates.DocumentTemplateException;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public interface ReportService {

    Resource historyReports() throws DocumentTemplateException, IOException, InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;
}
