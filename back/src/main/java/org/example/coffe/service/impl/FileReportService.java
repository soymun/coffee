package org.example.coffe.service.impl;

import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import net.sf.jooreports.templates.DocumentTemplateException;
import org.example.coffe.mappers.CommandMapper;
import org.example.coffe.model.CoffeeLogDto;
import org.example.coffe.reports.TextReports;
import org.example.coffe.repositories.CommandRepository;
import org.example.coffe.repositories.DcCommandDcRepository;
import org.example.coffe.service.ReportService;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
public class FileReportService implements ReportService {

    private final TextReports textReports;

    private final CommandRepository commandRepository;

    private final CommandMapper commandMapper;

    @Override
    public Resource historyReports() throws DocumentTemplateException, IOException, InterruptedException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return new FileSystemResource(textReports.generate(commandRepository.getCommandReport()));
    }
}
