package com.andreidodu.oreshare.service.impl;

import com.andreidodu.oreshare.constants.FileGroupMetadataConst;
import com.andreidodu.oreshare.dto.FileGroupMetadataDTO;
import com.andreidodu.oreshare.dto.FileMetadataDTO;
import com.andreidodu.oreshare.dto.FileUploadRequestDTO;
import com.andreidodu.oreshare.exception.ApplicationException;
import com.andreidodu.oreshare.mapper.FileGroupMetadataMapper;
import com.andreidodu.oreshare.model.FileGroupMetadata;
import com.andreidodu.oreshare.model.FileMetadata;
import com.andreidodu.oreshare.repository.FileGroupMetadataRepository;
import com.andreidodu.oreshare.repository.FileMetadataRepository;
import com.andreidodu.oreshare.repository.FileRepository;
import com.andreidodu.oreshare.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload2.core.DiskFileItem;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileItemInput;
import org.apache.commons.fileupload2.core.FileItemInputIterator;
import org.apache.commons.fileupload2.jakarta.JakartaServletFileUpload;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    final private FileGroupMetadataRepository fileGroupMetadataRepository;
    final private FileMetadataRepository fileMetadataRepository;
    final private FileRepository fileRepository;

    final private FileGroupMetadataMapper fileGroupMetadataMapper;

    @Override
    public FileGroupMetadataDTO retrieveFileGroupMetadata(Long fileGroupMetadataId) {
        FileGroupMetadata fileGroupMetadata = checkFileGroupMetadataExistence(fileGroupMetadataId);
        return this.fileGroupMetadataMapper.toDTO(fileGroupMetadata);
    }

    @Override
    public FileGroupMetadataDTO save(HttpServletRequest httpServletRequest) {
        FileUploadRequestDTO fileUploadRequestDTO = saveFilesAndExtractMetadata(httpServletRequest);
        return saveMetadata(fileUploadRequestDTO);
    }

    private FileGroupMetadataDTO saveMetadata(FileUploadRequestDTO fileUploadRequestDTO) {
        FileGroupMetadata model = this.fileGroupMetadataMapper.toModel(fileUploadRequestDTO);
        model.getFileMetadataList().forEach(item -> item.setFileGroupMetadata(model));
        FileGroupMetadata newModel = this.fileGroupMetadataRepository.save(model);
        return this.fileGroupMetadataMapper.toDTO(newModel);
    }

    @Override
    public ByteArrayResource download(Long fileId) throws IOException {
        FileMetadata fileMetadata = fileMetadataRepository.findById(fileId)
                .orElseThrow(() -> new ApplicationException("File not found"));
        byte[] fileContent = this.fileRepository.retrieveFileContent(fileMetadata.getFilename());
        return new ByteArrayResource(fileContent);
    }

    private FileUploadRequestDTO saveFilesAndExtractMetadata(HttpServletRequest httpServletRequest) {
        if (!JakartaServletFileUpload.isMultipartContent(httpServletRequest)) {
            throw new ApplicationException("The request is not a multipart request");
        }
        try {
            FileUploadRequestDTO fileUploadRequestDTO = new FileUploadRequestDTO();
            DiskFileItemFactory factory = buildDiskItemFactory();
            JakartaServletFileUpload<DiskFileItem, DiskFileItemFactory> upload = new JakartaServletFileUpload<>(factory);
            FileItemInputIterator fileItemInputIterator = upload.getItemIterator(httpServletRequest);
            while (fileItemInputIterator.hasNext()) {
                processFormField(fileItemInputIterator, fileUploadRequestDTO);
            }
            return fileUploadRequestDTO;
        } catch (IOException e) {
            throw new ApplicationException(String.format("Something went wrong: %s", e.getMessage()));
        }

    }

    private void processFormField(FileItemInputIterator fileItemInputIterator, FileUploadRequestDTO fileUploadRequestDTO) throws IOException {
        FileItemInput item = fileItemInputIterator.next();
        String name = item.getFieldName();
        if (item.isFormField()) {
            final String value = new String(item.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            if (FileGroupMetadataConst.FIELD_NAME_DESCRIPTION.equals(name)) {
                fileUploadRequestDTO.setDescription(value);
                log.debug(String.format("Form field [%s] has as value [%s]", name, value));
                return;
            }
            throw new ApplicationException(String.format("bad request: was sent a non recognized field [%s]", name));
        }

        String filename = item.getName();
        FileMetadataDTO fileMetadataDTO = buildFileMetadataDTO(item);
        fileUploadRequestDTO.getFileList().add(fileMetadataDTO);
        log.info("Saving filename {}...", name);
        saveFile(filename, item.getInputStream());
    }

    private static FileMetadataDTO buildFileMetadataDTO(FileItemInput item) {
        FileMetadataDTO fileMetadataDTO = new FileMetadataDTO();
        fileMetadataDTO.setFilename(item.getFieldName());
        fileMetadataDTO.setDescription(item.getFieldName());
        fileMetadataDTO.setType(item.getContentType());
        return fileMetadataDTO;
    }


    private void saveFile(String filename, InputStream inputStream) {
        try {
            fileRepository.writeDocumentToFile(filename, inputStream);
        } catch (IOException e) {
            throw new ApplicationException(String.format("Exception: %s %s", filename, e.getMessage()));
        }
    }

    private static DiskFileItemFactory buildDiskItemFactory() {
        return DiskFileItemFactory
                .builder()
                .get();
    }

    private FileGroupMetadata checkFileGroupMetadataExistence(Long fileGroupMetadataId) {
        return this.fileGroupMetadataRepository.findById(fileGroupMetadataId)
                .orElseThrow(() -> new ApplicationException("file not found"));
    }

}
