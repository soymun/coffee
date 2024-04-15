package org.example.coffe.service;

import org.example.coffe.model.DcCommandDto;

import java.util.List;

public interface DcCommandService {

    /**
     * Получить словарь команд
     * @return List<DcCommandDto>
     */
    List<DcCommandDto> getAll();
}
