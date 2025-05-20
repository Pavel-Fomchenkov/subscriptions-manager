# Первый этап: сборка приложения
FROM maven:3-openjdk-17 AS builder

# Рабочий каталог для сборки
RUN mkdir /app
WORKDIR /app

# Копируем файлы проекта
COPY pom.xml .
COPY src ./src

# Собираем проект
RUN mvn clean package -DskipTests

# Второй этап: создание минимального рабочего образа
FROM eclipse-temurin:21-jre

# Копируем готовый jar-файл из первого этапа
COPY --from=builder /app/target/*.jar /opt/subscriptions-manager.jar

# Определяем точку входа для запуска приложения
ENTRYPOINT ["java","-jar","/opt/subscriptions-manager.jar"]