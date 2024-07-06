# Setup
- Java: 17
- Spring Boot: 3.3.1
- Maven: 3.9.7

# Unit Test Coverage
<br><img src="/ScreenCapture/book_controller_coverage.png" width="500">
<br><img src="/ScreenCapture/book_service_coverage.png" width="500">
<br><img src="/ScreenCapture/book_repository_coverage.png" width="500">

# API Documentation
```
POST /createBook
```
```json
{
  "title" : String, // alphanumeric only
  "author" : String, // alphanumeric only
  "published" : boolean // boolean value
}
```

|  Status Code  | Description |
|:-------------:|:-----------:|
|      201      |   CREATED   |
|      400      | BAD REQUEST |

Sample Request & Response
<br><img src="/ScreenCapture/create_success.png" width="500">
<br><img src="/ScreenCapture/create_invalid_author.png" width="500">
<br><img src="/ScreenCapture/create_invalid_title.png" width="500">

```
GET /readBook
```

| Parameter |  Type   | Description |
|:---------:|:-------:|:-----------:|
|  author   | String  |  Optional   |
| published | boolean |  Optional   |


| Status Code | Description |
|:-----------:|:-----------:|
|     200     |     OK      |
|     404     |  NOT FOUND  |

Sample Request & Response
<br><img src="/ScreenCapture/read_all.png" width="500">
<br><img src="/ScreenCapture/read_by_author.png" width="500">
<br><img src="/ScreenCapture/read_by_published.png" width="500">
<br><img src="/ScreenCapture/read_by_author_published.png" width="500">
<br><img src="/ScreenCapture/read_not_found.png" width="500">

```
DELETE /readBook
```

| Parameter |  Type   | Description |
|:---------:|:-------:|:-----------:|
|    id     |  long   |  Required   |

| Status Code | Description |
|:-----------:|:-----------:|
|     200     |     OK      |
|     404     |  NOT FOUND  |

Sample Request & Response
<br><img src="/ScreenCapture/delete_success.png" width="500">
<br><img src="/ScreenCapture/delete_not_found.png" width="500">
