package com.example.demo.context;

import com.example.demo.models.CommandDto;
import com.example.demo.models.InfoCoffee;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Data
public class InfoContext {

    private Map<Long, String> dictionary;

    private List<CommandDto> data;

    private InfoCoffee infoCoffee;

    private String status;
}
