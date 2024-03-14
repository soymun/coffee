package org.example.coffe.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.coffe.mappers.DcCommandMapper;
import org.example.coffe.model.DcCommandDto;
import org.example.coffe.repositories.DcCommandDcRepository;
import org.example.coffe.service.DcCommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DcCommandServiceImpl implements DcCommandService {

    private final HashMap<Long, DcCommandDto> cache = new HashMap<>();


    private final DcCommandDcRepository dcCommandRepository;

    private final DcCommandMapper dcCommandMapper;
    @Override
    public List<DcCommandDto> getAll() {
        log.info("Get dcCommand");
        if(!cache.isEmpty()){
            return cache.values().stream().toList();
        }

        List<DcCommandDto> dcCommands = dcCommandRepository.findAll().stream().map(dcCommandMapper::getDcCommandDto).collect(Collectors.toList());

        dcCommands.forEach(dc -> cache.put(dc.getId(), dc));

        return dcCommands;
    }
}
