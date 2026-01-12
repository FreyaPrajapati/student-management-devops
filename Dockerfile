FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY src /app/src

RUN javac -d . src/model/*.java src/service/*.java src/ui/*.java src/Main.java

CMD ["java", "Main"]

