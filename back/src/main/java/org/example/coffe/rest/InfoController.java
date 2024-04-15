package org.example.coffe.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.coffe.model.InfoCoffee;
import org.example.coffe.model.ResultModel;
import org.example.coffe.model.Status;
import org.example.coffe.service.InfoService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(
        name = "Info Controller"
)
@RequestMapping("/api/v1/info")
@RequiredArgsConstructor
public class InfoController {

    private final InfoService infoService;

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
    public ResponseEntity<InfoCoffee> getInfo(@RequestParam String machine) throws InterruptedException {
        return ResponseEntity.ok(infoService.getInfo(machine));
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
    public ResponseEntity<Status> getStatus(@RequestParam String machine) throws InterruptedException {
        return ResponseEntity.ok(infoService.getStatus(machine));
    }

    @GetMapping(value = "/machines", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(method = "GET",
            description = "Get Machine",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Machine get",
                            content = {
                                    @Content(schema = @Schema(implementation = List.class))
                            }
                    )
            }
    )
    public ResponseEntity<ResultModel<List<String>>> getMachines() throws InterruptedException {
        return ResponseEntity.ok(new ResultModel<>(infoService.machine()));
    }
}
