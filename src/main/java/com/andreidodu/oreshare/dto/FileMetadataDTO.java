package com.andreidodu.oreshare.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FileMetadataDTO {
    private Long id;
    private String filename;
    private String type;
    private LocalDateTime createdDate;
    private String description;
}
