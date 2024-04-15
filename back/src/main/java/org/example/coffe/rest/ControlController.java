package org.example.coffe.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.coffe.model.CoffeeType;
import org.example.coffe.model.ResultModel;
import org.example.coffe.model.Status;
import org.example.coffe.service.InfoService;
import org.example.coffe.service.impl.MachineServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(
        name = "CoffeeMachine Controller"
)
@RequiredArgsConstructor
@RequestMapping("/api/v1/coffee")
public class ControlController {

    private final MachineServiceImpl machineService;

    private final InfoService infoService;

    @Operation(method = "POST",
            description = "Make coffee",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Coffee made",
                            content = {
                                    @Content(schema = @Schema(implementation = ResultModel.class))
                            }
                    )
            }
    )
    @PostMapping(value = "/make", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultModel<Status>> makeCoffee(@RequestParam CoffeeType coffeeType) {
        machineService.make(coffeeType);
        return ResponseEntity.ok(new ResultModel<>(Status.READY));
    }

    @Operation(method = "POST",
            description = "Clean coffee machine",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Coffee machine cleaned",
                            content = {
                                    @Content(schema = @Schema(implementation = ResultModel.class))
                            }
                    )
            }
    )
    @PostMapping(value = "/clean", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultModel<Status>> clean(@RequestParam String machine) {
        machineService.clean(machine);
        return ResponseEntity.ok(new ResultModel<>(Status.READY));
    }

    @Operation(method = "POST",
            description = "Restart coffee machine",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Coffee machine restarted",
                            content = {
                                    @Content(schema = @Schema(implementation = ResultModel.class))
                            }
                    )
            }
    )
    @PostMapping(value = "/restart", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultModel<Status>> restart(@RequestParam String machine) throws InterruptedException {
        machineService.restart(machine);
        return ResponseEntity.ok(new ResultModel<>(infoService.getStatus(machine)));
    }

    @Operation(method = "POST",
            description = "Stop coffee machine",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Coffee machine stopped",
                            content = {
                                    @Content(schema = @Schema(implementation = ResultModel.class))
                            }
                    )
            }
    )
    @PostMapping(value = "/stop", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResultModel<Status>> stop(@RequestParam String machine) {
        machineService.stop(machine);
        return ResponseEntity.ok(new ResultModel<>(Status.STOP));
    }

}
