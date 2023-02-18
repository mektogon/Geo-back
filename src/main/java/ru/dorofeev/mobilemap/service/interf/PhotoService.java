package ru.dorofeev.mobilemap.service.interf;

import org.springframework.http.ResponseEntity;
import ru.dorofeev.mobilemap.model.base.Photo;
import ru.dorofeev.mobilemap.model.dto.PhotoRotateDto;

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

    /**
     * Метод позволяет повернуть изображение на заданный целочисленный градус.
     *
     * @param photo         поворачиваемое изображение.
     * @param rotationAngle угол поворота.
     * @param isPreview     признак, указывающий, что поворачиваем превью фотографии.
     */
    void rotatePhoto(UUID photo, int rotationAngle, boolean isPreview);

    /**
     * Метод позволяет повернуть изображение на заданный целочисленный градус.
     *
     * @param photoRotateDto сущность с информацией для поворота фотографии.
     */
    void rotatePhoto(PhotoRotateDto photoRotateDto);
}
