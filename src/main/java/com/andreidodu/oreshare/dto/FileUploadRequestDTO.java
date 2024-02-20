package com.andreidodu.oreshare.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FileUploadRequestDTO {

    private String description;
    private List<FileMetadataDTO> fileList = new ArrayList<>();
}
