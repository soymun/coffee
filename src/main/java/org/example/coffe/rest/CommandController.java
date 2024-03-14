package org.example.coffe.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.coffe.model.CommandDto;
import org.example.coffe.model.ResultModel;
import org.example.coffe.service.CommandService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(
        name = "Command Controller"
)
@RequestMapping("/api/v1/command")
public class CommandController {

    private final CommandService commandService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(method = "GET",
            description = "Get commands",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Commands get",
                            content = {
                                    @Content(schema = @Schema(implementation = ResultModel.class))
                            }
                    )
            }
    )
    public ResponseEntity<ResultModel<List<CommandDto>>> getInfo(@RequestParam Integer pageSize, @RequestParam Integer pageNumber){
        return ResponseEntity.ok(new ResultModel<>(commandService.getAllByPage(pageNumber, pageSize)));
    }
}
