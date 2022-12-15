## Бизнес-процесс. Сервис по работе с клиентами


### Краткое описание процесса
- Процесс иллюстрирует работу обработки заявок, поступающих от клиентов
- Заявка имеет текст, категорию и контактные данные клиента для связи
- В процессе обработки заявки клиенту отправляется сообщение о регистрации заявки с присвоением идентификатора
- Заявка, в процессе исполнения, исходя из сложности, может переназначаться
- Чтобы завершить процесс необходимо получить от клиента положительный ответ по решению заявки

### Запуск проекта 
выполнить
```
mvn package 
```
далее в терминале
```
docker compose up
```

### Конфигурация
- Переменные окружения для настройки проекта
    - ```DATABASE_URL``` - url для подключения к БД
    - ```DATABASE_USERNAME``` - username БД
    - ```DATABASE_PASSWORD``` - password для входа в БД
    - ```KAFKA_GROUP_ID``` - идентификатор группы для подключения consumers
    - ```KAFKA_SERVER``` - имя host
- Приложение запускается под портом ```8080```
- Использование Swagger для тестирования endpoints ```http://localhost:8080/swagger-ui.html#/```


### Перечень состояний процесса eventID по Camunda BPM
- message start event
  - ```newState``` - обращение в службу поддержки
- intermediate throw event
  - ```createdState``` - заяка сформирована
  - ```acceptedState``` - заяка принята
  - ```assignedState``` - заявка назначена
  - ```processedState``` - заявка обработана
  - ```notResolvedState``` - вопрос по заявке не решен
- terminate end event
  - ```finishedState``` - заявка выполнена
    

### Переходы состояний процесса
1. ```newState``` -> ```createdState```
   - _вариант 1_ Отправить запрос методом ```POST``` по endpoint ```http://localhost:8080/api/v1/support/claim/start``` с request body в формате json 
       - пример request body 
         {
           "customerContactInfo":"+77777",
           "claimType":"OFFER",
           "description":"appUpgrade"
         }
   - _вариант 2_ Отправить сообщение в topic ```newClaim```
       - в терминале выполнить ```docker exec --interactive --tty broker_support kafka-console-producer --bootstrap-server broker_support:9092 --topic newClaim```, далее записать сообщение
         - пример сообщения
           {"customerContactInfo":"+00000", "claimType":"ENQUIRY", "description":"place gates"}
    
2. ```createdState``` -> ```acceptedState```
   - отправить запрос методом ```PATCH``` по endpoint ```http://localhost:8080/api/v1/support/claim/register/{id}``` с указанием идентификатора и request body в формате json
     - пример request body
       {
          "isAssigned":false
       }

3. ```createdState``` -> ```assignedState```
   - отправить запрос методом ```PATCH``` по endpoint ```http://localhost:8080/api/v1/support/claim/register/{id}``` с указанием идентификатора и request body в формате json
     - пример request body
       {
          "isAssigned":true
       }
   
4. ```acceptedState``` -> ```processedState```
   - отправить запрос методом ```PATCH``` по endpoint ```http://localhost:8080/api/v1/support/claim/execute/basic/{id}``` с указанием идентификатора и request body в формате json
     - пример request body
       {
         "claimAnswer":"will be resolved in 10 days"
       }
     
5. ```assignedState``` -> ```processedState```
   - отправить запрос методом ```PATCH``` по endpoint  ```http://localhost:8080/api/v1/support/claim/execute/assigned/{id}``` с указанием идентификатора и request body в формате json
     - пример request body
       {
         "claimAnswer":"will be resolved in 10 days"
       }
    
6. ```processedState``` -> ```notResolvedState```
   - отправить сообщение в topic ```claimClientResolution```
     - в терминале выполнить ```docker exec --interactive --tty broker_support kafka-console-producer --bootstrap-server broker_support:9092 --topic claimClientResolution```, далее записать сообщение
       - пример сообщения
         {"id":1, "queryIsSolved":false, "clientResponseOnClaimAnswer":"hasn't fixed yet"}

7. ```processedState``` -> ```assignedState```
   - отправить сообщение в topic ```claimClientResolution```
     - в терминале выполнить ```docker exec --interactive --tty broker_support kafka-console-producer --bootstrap-server broker_support:9092 --topic claimClientResolution```, далее записать сообщение
       - пример сообщения
          {"id":1, "queryIsSolved":false, "clientResponseOnClaimAnswer":"hasn't fixed yet"}

8. ```processedState``` -> ```finishedState```
   - отправить сообщение в topic ```claimClientResolution```
     - в терминале выполнить ```docker exec --interactive --tty broker_support kafka-console-producer --bootstrap-server broker_support:9092 --topic claimClientResolution```, далее записать сообщение
       - пример сообщения
        {"id":1, "queryIsSolved":true, "clientResponseOnClaimAnswer":"fixed"}
    
### Дополнительно
- чтение ответов 
  - заявка принята
    - ```docker exec --interactive --tty broker_support kafka-console-consumer --bootstrap-server broker_support:9092 --topic claimAcceptance --from-beginning```
  - ответ по заявке от исполнителя
     - ```docker exec --interactive --tty broker_support kafka-console-consumer --bootstrap-server broker_support:9092 --topic claimExecutorAnswer --from-beginning```
- внесение примечания по заявке исполнителем в любой момент по процессу 
   - отправить запрос методом ```PATCH``` по endpoint  ```http://localhost:8080/api/v1/support/claim/update-by-id/{id}``` с указанием идентификатора и request body в формате json
      - пример request body 
        {
           "notes":"check documentation" 
        }
### Используемые в проекте технологии
- ```Spring Data Jpa```
- ```Flyway```
- ```ModelMapper```
- ```Camunda BPM```
- ```Apache Kafka```
- ```Swagger```
- ```Docker```