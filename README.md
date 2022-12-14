## Бизнес-процесс. Сервис по работе с клиентами


### Краткое описание процесса
- Процесс иллюстрирует работу обработки заявок, поступающих от клиентов
- Заявка имеет текст, категорию и контактные данные клиента для связи
- В процессе обработки заявки клиенту отправляется сообщение о регистрации заявки с присвоением идентификатора
- Заявка, в процессе исполнения, исходя из сложности, может переназначаться
- Чтобы завершить процесс необходимо получить от клиента положительный ответ по решению заявки


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
   - _способ 1_ Отправить запрос методом ```POST``` по endpoint ```http://localhost:8080/api/v1/support/claim/start``` с request body в формате json 
       - пример request body 
         {
           "customerContactInfo":"+77777",
           "claimType":"OFFER",
           "description":"appUpgrade"
         }
   - _способ 2_ Отправить сообщение в topic ```newClaim```
       - пример value сообщения
         {
           "customerContactInfo":"+00000",
           "claimType":"ENQUIRY",
           "description":"place gates"
         }
    
2. ```createdState``` -> ```acceptedState```
   - отправить запрос методом ```PATCH``` по endpoint ```http://localhost:8080/api/v1/support/claim/register/{id}``` с указанием идентификатора и request body в формате json
     - пример request body
       {
          "isAssigned":false // исполнение заявки общего типа
       }

3. ```createdState``` -> ```assignedState```
   - отправить запрос методом ```PATCH``` по endpoint ```http://localhost:8080/api/v1/support/claim/register/{id}``` с указанием идентификатора и request body в формате json
     - пример request body
       {
          "isAssigned":true // заявка переведена на специалиста по работе со специфичными запросами
       }
   
4. ```acceptedState``` -> ```processedState```
   - отправить запрос методом ```PATCH``` по endpoint ```http://localhost:8080/api/v1/support/claim/execute/basic/{id}``` с указанием идентификатора и request body в формате json
     - пример request body
       {
         "claimAnswer":"will be resolved in 10 days" // ответ специалиста по заявке
       }
     
5. ```assignedState``` -> ```processedState```
   - отправить запрос методом ```PATCH``` по endpoint  ```http://localhost:8080/api/v1/support/claim/execute/assigned/{id}``` с указанием идентификатора и request body в формате json
     - пример request body
       {
         "claimAnswer":"will be resolved in 10 days" // ответ специалиста по заявке
       }
    
6. ```processedState``` -> ```notResolvedState```
   - отправить сообщение в topic ```claimClientResolution```
     - пример value сообщения
       {
         "id":"1",
         "queryIsResolved":false // ответ клиента по решению заявки 
       }

7. ```processedState``` -> ```assignedState```
   - отправить сообщение в topic ```claimClientResolution```
     - пример value сообщения
        {
          "id":"2",
          "queryIsResolved":false
        }
8. ```processedState``` -> ```finishedState```
   - отправить сообщение в topic ```claimClientResolution```
     - пример value сообщения
      {
        "id":"2",
        "queryIsResolved":true // 
      }


### Используемые в проекте технологии
- ```Spring Data Jpa```
- ```Flyway```
- ```ModelMapper```
- ```Camunda BPM```
- ```Apache Kafka```
- ```Swagger```
- ```Docker```