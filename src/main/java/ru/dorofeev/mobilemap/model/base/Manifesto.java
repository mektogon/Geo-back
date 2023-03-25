package ru.dorofeev.mobilemap.model.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Version;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Манифест тайлов
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Manifesto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Builder
    public Manifesto(UUID id, String name, String mapNameArchiveLink) {
        this.id = id;
        this.name = name;
        this.mapNameArchiveLink = mapNameArchiveLink;
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(unique = true)
    private String name;

    @Setter(AccessLevel.NONE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate = new Date();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastUpdate = new Date();

    /**
     * Версия для проверки совместимости
     */
    @Version
    private int version;

    /**
     * JSON-строка состоящая из мапы: наименование архива - ссылка
     */
    @Column(columnDefinition = "TEXT")
    private String mapNameArchiveLink;
}