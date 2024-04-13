package org.example.coffe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(name="Information response", description="Simple information response")
public class ResultModel<T> {

    @JsonProperty(value = "data")
    private T data;

    @JsonProperty(value = "errors")
    private List<String> errors = new ArrayList<>();

    public ResultModel(T data) {
        this.data = data;
    }
}
