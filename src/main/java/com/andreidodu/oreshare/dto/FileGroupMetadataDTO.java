package com.andreidodu.oreshare.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class FileGroupMetadataDTO {
    private Long id;
    private String description;
    private LocalDateTime createdDate;
    private List<FileMetadataDTO> fileMetadataDTOList;
}
