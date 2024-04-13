package org.example.coffe.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.coffe.mappers.CommandMapper;
import org.example.coffe.model.CommandDto;
import org.example.coffe.repositories.CommandRepository;
import org.example.coffe.service.CommandService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommandServiceImpl implements CommandService {

    private final CommandRepository commandRepository;
    private final CommandMapper commandMapper;
    @Override
    @Transactional
    public List<CommandDto> getAllByPage() {
        log.info("Get all commands by size");
        return commandRepository.findAll().stream().map(commandMapper::commandToCommandDto).toList();
    }
}
