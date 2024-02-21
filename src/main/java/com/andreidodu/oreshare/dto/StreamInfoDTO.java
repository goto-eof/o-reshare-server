package com.andreidodu.oreshare.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.FileInputStream;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class StreamInfoDTO {
    private FileInputStream fileInputStream;
    private long fileSize;
    private String filename;
    private long lastModified;
}
