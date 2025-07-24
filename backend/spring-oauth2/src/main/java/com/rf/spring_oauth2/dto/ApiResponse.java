package com.rf.spring_oauth2.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse <T>{
    private int status;
    private String path;
    private String message;
    private LocalDateTime dateTime;
    private Map<String,String> errors=new HashMap<>();
    private T data;

    public static <T> ApiResponse<T> success(String message){
        return ApiResponse.<T>builder().status(200).message(message).dateTime(LocalDateTime.now()).build();
    }
    public static <T> ApiResponse<T> success(T data,String message){
        return ApiResponse.<T>builder().status(200).message(message).dateTime(LocalDateTime.now()).data(data).build();
    }
}
