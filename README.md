# Java CRUD Project

## Структура проекта

```
crud/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           ├── model/
│   │   │           ├── repository/
│   │   │           ├── view/
│   │   │           └── controller/
│   │   └── resources/
│   └── test/
│       └── java/
│           └── com/
│               └── example/
├── pom.xml
└── README.md
```

- **model/** — классы сущностей (POJO)
- **repository/** — интерфейсы для доступа к данным
- **view/** — взаимодействие с консолью
- **controller/** — взаимодействие с пользователем

## Тесты

- **WriterTest** — unit-тесты класса Writer и принадлежащих ему сервисов
- **PostTest** — unit-тесты класса Post и принадлежащих ему сервисов
- **LabelTest** — unit-тесты класса Label и принадлежащих ему сервисов

Тесты реализованы с использованием JUnit и Mockito.
