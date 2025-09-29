package org.uploader.fileuploadtest.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , unique = true)
    private String uploadId;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private int totalChunks;

    @Column(nullable = false)
    private String contentType;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    @Column(nullable = false , name = "created_at" , updatable = false)
    private Instant createdAt;


    public enum Status{
        IN_PROGRESS , COMPLETED , CANCELED
    }
}
