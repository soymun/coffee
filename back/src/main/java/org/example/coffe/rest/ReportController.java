package org.example.coffe.rest;

import lombok.RequiredArgsConstructor;
import net.sf.jooreports.templates.DocumentTemplateException;
import org.example.coffe.service.ReportService;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/txt")
    public Resource getReport() throws DocumentTemplateException, IOException, InterruptedException {
        return reportService.historyReports();
    }
}
