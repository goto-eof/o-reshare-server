package com.andreidodu.oreshare.controller;

import com.andreidodu.oreshare.dto.FileGroupMetadataDTO;
import com.andreidodu.oreshare.dto.StreamInfoDTO;
import com.andreidodu.oreshare.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/fileGroup")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class FileGroupMetadataController {

    private final FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<FileGroupMetadataDTO> upload(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(this.fileService.save(httpServletRequest));
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<FileGroupMetadataDTO> retrieveFileGroupMetadata(@PathVariable Long id) throws IOException {
        return ResponseEntity.ok(this.fileService.retrieveFileGroupMetadata(id));
    }

    @GetMapping(value = "/download/fileId/{fileId}")
    public @ResponseBody ResponseEntity<InputStreamResource> downloadFile(@PathVariable Long fileId) throws IOException {
        StreamInfoDTO streamInfoDTO = this.fileService.retrieveFileStreamInfo(fileId);
        InputStreamResource inputStreamResource = new InputStreamResource(streamInfoDTO.getFileInputStream());
        log.info("Downloading fileId = {}", fileId);
        final HttpHeaders httpHeaders = prepareHeadersForDownload(streamInfoDTO);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(streamInfoDTO.getFileSize())
                .headers(httpHeaders)
                .body(inputStreamResource);
    }

    private static HttpHeaders prepareHeadersForDownload(StreamInfoDTO streamInfoDTO) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.LAST_MODIFIED, String.valueOf(streamInfoDTO.getLastModified()));
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                .filename(streamInfoDTO.getFilename())
                .build()
                .toString());
        httpHeaders.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(streamInfoDTO.getFileSize()));
        return httpHeaders;
    }

}
