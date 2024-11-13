package com.project.sharedCardServer.restController.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileRequest {
    private UUID id;
    private String uri;
    private byte[] pic;
}
