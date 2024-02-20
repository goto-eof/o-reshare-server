package com.andreidodu.oreshare.service;

import com.andreidodu.oreshare.dto.FileGroupMetadataDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;

public interface FileService {
    FileGroupMetadataDTO retrieveFileGroupMetadata(Long fileGroupMetadataId);

    FileGroupMetadataDTO save(HttpServletRequest httpServletRequest);

    ByteArrayResource download(Long fileId) throws IOException;
}
