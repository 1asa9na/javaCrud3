FROM maven:3.9.6-eclipse-temurin-21

# рабочая директория
WORKDIR /usr/src/app

# копируем всё (src, pom.xml и т.д.)
COPY . .

# по умолчанию будем собирать
CMD ["mvn", "clean", "package"]