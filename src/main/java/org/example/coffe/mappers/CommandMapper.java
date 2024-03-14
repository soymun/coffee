package org.example.coffe.mappers;

import org.example.coffe.entities.CoffeeLog;
import org.example.coffe.entities.Command;
import org.example.coffe.model.CoffeeLogDto;
import org.example.coffe.model.CommandDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommandMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "message", target = "message")
    CommandDto commandToCommandDto(Command command);

    @Mapping(source = "coffeeType", target = "name")
    CoffeeLogDto getCoffeeLogDto(CoffeeLog coffeeLog);
}
