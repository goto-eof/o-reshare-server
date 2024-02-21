package com.andreidodu.oreshare.service;

import com.andreidodu.oreshare.dto.FileGroupMetadataDTO;
import com.andreidodu.oreshare.dto.StreamInfoDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.io.FileNotFoundException;

public interface FileService {
    FileGroupMetadataDTO retrieveFileGroupMetadata(Long fileGroupMetadataId);

    FileGroupMetadataDTO save(HttpServletRequest httpServletRequest);

    StreamInfoDTO retrieveFileStreamInfo(Long fileId) throws FileNotFoundException;
}
