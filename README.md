# Use SQLite with Spring Data JDBC

This repository provides a spring-data-relational dialect for SQLite, so it can
be used with spring-data-jdbc.

## Dependencies

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.data</groupId>
        <artifactId>spring-data-jdbc</artifactId>
        <version>${spring-data.version}</version>
    </dependency>
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <scope>runtime</scope>
        <version>${sqlite.version}</version>
    </dependency>
    <dependency>
        <groupId>org.nuvito.spring.data</groupId>
        <artifactId>sqlite-dialect</artifactId>
        <version>${sqlite-dialect.version}</version>
    </dependency>
</dependencies>
```

The dialect will be instantiated as Bean within the 
`org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration`.
If you are using Spring Boot since 2.1.0, it will be auto-configured. 
