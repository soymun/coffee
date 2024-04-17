package org.example.coffe.service;

import net.sf.jooreports.templates.DocumentTemplateException;
import org.springframework.core.io.Resource;

import java.io.IOException;


public interface ReportService {

    Resource historyReports() throws DocumentTemplateException, IOException;
}
