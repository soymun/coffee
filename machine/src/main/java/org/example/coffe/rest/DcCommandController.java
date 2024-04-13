package org.example.coffe.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.example.coffe.coffeeMachine.info.InfoCoffee;
import org.example.coffe.entities.DcCommand;
import org.example.coffe.model.DcCommandDto;
import org.example.coffe.model.ResultModel;
import org.example.coffe.service.DcCommandService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dc-command")
@RequiredArgsConstructor
public class DcCommandController {

    private final DcCommandService dcCommandService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(method = "GET",
            description = "Get dictionary command",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Dictionary command get",
                            content = {
                                    @Content(schema = @Schema(implementation = InfoCoffee.class))
                            }
                    )
            }
    )


    public ResponseEntity<ResultModel<List<DcCommandDto>>> getInfo(){
        return ResponseEntity.ok(new ResultModel<>(dcCommandService.getAll()));
    }
}

