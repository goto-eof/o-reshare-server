package com.andreidodu.oreshare.mapper;

import com.andreidodu.oreshare.dto.FileGroupMetadataDTO;
import com.andreidodu.oreshare.dto.FileUploadRequestDTO;
import com.andreidodu.oreshare.model.FileGroupMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {FileMetadataMapper.class})
public abstract class FileGroupMetadataMapper {

    @Mapping(source = "fileMetadataList", target = "fileMetadataDTOList", qualifiedByName = FileMetadataMapper.TO_FILE_METADATA_DTO_LIST)
    public abstract FileGroupMetadataDTO toDTO(FileGroupMetadata model);

    @Mapping(target = "fileMetadataList", ignore = true)
    public abstract FileGroupMetadata toModel(FileGroupMetadataDTO fileGroupMetadataDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "fileList", target = "fileMetadataList", qualifiedByName = FileMetadataMapper.TO_FILE_METADATA_LIST)
    public abstract FileGroupMetadata toModel(FileUploadRequestDTO fileUploadRequestDTO);

}
