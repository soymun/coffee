package org.example.coffe.service;

import org.example.coffe.model.CommandDto;

import java.util.List;

public interface CommandService {

    /**
     * Получить команды
     * @param pageNumber номер страницы
     * @param pageSize размер страницы
     * @return List<CommandDto>
     */
    List<CommandDto> getAllByPage();
}
