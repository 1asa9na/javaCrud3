# CRUD Project (Java + Maven + Hibernate + Flyway + MySQL in Docker)

## Описание

- **MySQL** --- база данных\
- **Maven App** --- контейнер для сборки проекта и применения миграций
Flyway

------------------------------------------------------------------------

## Запуск проекта

### Запуск контейнеров

``` bash
docker compose up --build
```

При старте:\
- Контейнер **MySQL** создаст пустую базу `crud_db`.\
- Контейнер **Maven** выполнит: - `mvn clean package` -
`mvn flyway:migrate` → применит все миграции из
`db/migration/V1__init_structure.sql`.

------------------------------------------------------------------------

## 🗄️ Структура базы данных

Схема создаётся **Flyway** автоматически:

-   **writers** (id, first_name, last_name)\
-   **posts** (id, content, created, updated, status, writer_id) → FK →
    writers(id)\
-   **labels** (id, name) \[UNIQUE\]\
-   **post_labels** (post_id, label_id) → FK → posts(id), labels(id)

------------------------------------------------------------------------

## 🛠️ Настройки подключения

База доступна:

-   Внутри Docker-сети: `jdbc:mysql://db:3306/crud_db`\
-   С хоста (например, для IDE): `jdbc:mysql://localhost:3306/crud_db`
    -   Пользователь: `root`\
    -   Пароль: `root`
