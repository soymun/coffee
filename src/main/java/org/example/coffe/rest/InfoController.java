package org.example.coffe.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.coffe.coffeeMachine.info.Info;
import org.example.coffe.coffeeMachine.info.InfoCoffee;
import org.example.coffe.coffeeMachine.status.Status;
import org.example.coffe.coffeeMachine.status.StatusMachine;
import org.example.coffe.model.ResultModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(
        name = "Info Controller"
)
@RequestMapping("/api/v1/info")
@RequiredArgsConstructor
public class InfoController {

    private final StatusMachine statusMachine;

    private final Info info;

    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(method = "GET",
            description = "Get info",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Info get",
                            content = {
                                    @Content(schema = @Schema(implementation = InfoCoffee.class))
                            }
                    )
            }
    )
    public ResponseEntity<InfoCoffee> getInfo(){
        return ResponseEntity.ok(info.getInfo());
    }

    @GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(method = "GET",
            description = "Get status",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Status get",
                            content = {
                                    @Content(schema = @Schema(implementation = Status.class))
                            }
                    )
            }
    )
    public ResponseEntity<Status> getStatus(){
        return ResponseEntity.ok(statusMachine.getStatus());
    }
}
