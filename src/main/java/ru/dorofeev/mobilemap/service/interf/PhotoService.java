package ru.dorofeev.mobilemap.service.interf;

import org.springframework.http.ResponseEntity;
import ru.dorofeev.mobilemap.model.base.Photo;

import java.util.UUID;

public interface PhotoService extends AbstractFileService<Photo> {
    /**
     * Позволяет получить первое фото в отсортированном списке фотографий, доступных гео-объекту.
     * Первое фото считается главной фотографией, отображаемой на странице.
     *
     * @param id идентификатор гео-объекта.
     * @return фотография.
     */
    Photo getMainPhoto(UUID id);

    /**
     * Позволяет получить превью фотографии, принадлежащей гео-объекту по ID.
     *
     * @param id идентификатор гео-объекта.
     * @return 'представление' превью фотографии.
     */
    ResponseEntity<byte[]> getPreviewPhotoById(UUID id);

    /**
     * Инициализация нового превью для главной фотографии гео-объекта.
     *
     * @param id идентификатор гео-объекта.
     */
    void initializePreviewPhoto(UUID id);
}
