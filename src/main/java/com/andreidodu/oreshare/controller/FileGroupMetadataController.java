package com.andreidodu.oreshare.controller;

import com.andreidodu.oreshare.dto.FileGroupMetadataDTO;
import com.andreidodu.oreshare.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
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
    public @ResponseBody ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long fileId) throws IOException {
        ByteArrayResource resource = this.fileService.download(fileId);
        log.info("Downloading fileId = {}", fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("o-ReShare")
                                .build()
                                .toString())
                .body(resource);
    }

}
