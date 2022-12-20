# Geo-back
REST-API проект, разрабатываемый в рамках дипломной работы *Алтайского Государственного Университета*.
**Целью** проекта является **разработка серверного части** для мобильного приложения навигации по достопримечательностям Чуйского тракта.
## Технологии
Проект разрабатывался с использованием:
- Spring Framework (Core, Boot, Data JPA, Security, MVC)
- Lombok
- PostgreSQL
- Swagger
- SLF4J-reload4j
## Общая информация
В проекте используется *Spring Security*, что позволяет разделить доступность к определенным endpoint\`ам на основании ролей.
Под ролью администратора: *ROLE_ADMIN* осуществляются все манипуляции над объектами: создание, удаление, обновление. 
Есть "открытые" endpoint\`ы, к которым могут обращаться не авторизированные пользователи. Сделано в целях удобства для мобильного
приложения, т.к. ему не требуется авторизация для получения информации о географических объектах и принадлежащих к ним медиа-файлах.
Все endpoint`ы расположены по адресу: **/swagger-ui/index.html**
<br> 
Каждый географический объект имеет ряд обязательных полей, без которых объект нельзя создать:
- **name** - **_наименование_** - ограничение в 256 символов.
- **latitude** - **_широта_** - [-90.0; 90.0]
- **longitude** - **_долгота_** - [-180.0; 180.0]
- **description** - **_описание_** - ограничений на длину описания нет.
- **designation** - **_обозначение_** - передается наименование обозначение. Оно должно быть создано до использования в гео-объекте.
- **photo** - **_список фотографий_** - передается не более 5 фотографий.
- **audio** - **_аудиозапись_** - передается не более 1 аудиозаписи.
- Все остальные поля явлются **необязательными**, но если вы передаете **адрес** (_region, district, typeLocality, locality, street, houseNumber_), 
то должны передавать хотя следующие поля:
- - **region** - **_название региона_**
- - **typeLocality** - **_тип местности (город, деревня и пр.)_** - должны быть уже зашиты в систему.
- - **locality** - **_название местности (Барнаул, Москва и пр.)_**
- - Без этих полей **адрес не сохранится**, следовательно и **гео-объект**.

Каждое поле проходит **валидацию**, после прохождения которой географический объект сохраняется.

## Руководство администратора
Руководство по настройке приложения находится в папке: **admin-guide**