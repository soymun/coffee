package org.example.coffe.mappers;

import org.example.coffe.entities.DcCommand;
import org.example.coffe.model.DcCommandDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DcCommandMapper {

    DcCommandDto getDcCommandDto(DcCommand dcCommand);
}
