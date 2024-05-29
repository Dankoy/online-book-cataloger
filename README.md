### jasypt

Обязательно надо указать явно все параметры при шифровании

Шифрование файла с голыми
паролями - `mvn jasypt:encrypt -Djasypt.plugin.path="file:./src/main/resources/application.yml" -Djasypt.encryptor.password=masterpassword`

Шифрование строки с помощью плагина - `mvn jasypt:encrypt-value -Djasypt.encry
ptor.password=masterpassword -Djasypt.encryptor.algorithm=PBEWithMD5AndDES -Djasypt.encryptor.ivGeneratorClassName=org.jasypt.iv.NoIvGenerator -Djasypt.plugin.value=mongopass`

### Билд бэкенд контейнера

`docker build -t evawe/library-backend:1 --build-arg maven_profile=no-frontend --build-arg JASYPT_PASSWORD=masterpassword .
`

### Запуск бэкенд контейнера

`docker run --rm --name library-backend -e JASYPT_PASSWORD=masterpassword -e MONGODB_DATABASE=library -e MONGODB_PORT=27017 -e MONGODB_HOST=localhost evawe/library-backend:1`

### Зависимости для docker-compose

Для сокрытия паролей и прочих интересных переменных используется файл .env из корня проекта.

Пример содержимого файла:

`JASYPT_MASTER_PASSWORD=пароль
MONGO_CONTAINER_PASSWORD=пароль`

### zuul

Zuul не работает с версиями спринг бута выше 2.2.Х    
Необходимо использовать spring cloud gateway

## Схема микросервисов

## Авторизация и аутентификация

Реализован флоу Token Relay с помощью Spring Cloud Gateway и Spring Authorization Server. Все
запросы проходят через gateway, который выступает как oauth2 клиент для сервера авторизации.
Сервер авторизации генерирует jwt токены, регистрирует и логинит пользователей. При этом клиенты
публичные в виде SPA и других токены не видят и не хранят.

Сервер авторизации отвечает за аутентификацию, а gateway на основе полученных данных просто
ретранслирует полученный токен к ресурс серверу. Этот самый ресурс сервер обязан проверить
авторизацию и пустить на ресурс юзера или нет. В конфигурации с клиентом oauth на стороне security
gateway невозможно настроить авторизацию НЕ на ресурсных микросервисах. Для того, что бы так
сделать, надо перенастроить security gateway как resource server, таким образом он может уже
проверять токен на авторизацию по ролям, но клиент должен к нему прийти с токеном, то есть запросить
уже login flow пройти и с полученным токеном прийти к security gateway.

[Более подробно](https://www.baeldung.com/spring-cloud-gateway-oauth2)

