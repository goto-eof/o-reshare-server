package com.andreidodu.oreshare.model;

import com.andreidodu.oreshare.model.common.ModelCommon;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@Table(name = "or_file_metadata")
@EntityListeners(AuditingEntityListener.class)
public class FileMetadata extends ModelCommon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "filename", nullable = false)
    private String filename;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_group_metadata_id", nullable = false)
    private FileGroupMetadata fileGroupMetadata;

}
