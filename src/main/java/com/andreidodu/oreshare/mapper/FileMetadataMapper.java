package com.andreidodu.oreshare.mapper;

import com.andreidodu.oreshare.dto.FileMetadataDTO;
import com.andreidodu.oreshare.model.FileMetadata;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class FileMetadataMapper {

    final static String TO_FILE_METADATA_LIST = "toFileMetadataList";
    final static String TO_FILE_METADATA_DTO_LIST = "toFileMetadataDTOList";

    public abstract FileMetadataDTO toDTO(FileMetadata model);

    @Named(TO_FILE_METADATA_LIST)
    public abstract List<FileMetadata> toDTOList(List<FileMetadataDTO> dtoList);

    @Named(TO_FILE_METADATA_DTO_LIST)
    public abstract List<FileMetadataDTO> toDTOListNew(List<FileMetadata> dtoList);

    @Mapping(target = "fileGroupMetadata", ignore = true)
    public abstract FileMetadata toModel(FileMetadataDTO dto);


}
