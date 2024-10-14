# Test project for the position of backend developer UniBell Software Company

The task description is presented in the `task.txt` file in the project root.

## Local Development Setup

1. **Clone the repository:**

    ```bash
    git clone https://github.com/sidorovmsk/test_unibell.git
    cd test_unibell
    ```

2. **Run the application:**

    ```bash
    mvn spring-boot:run
    ```

3. **Swagger UI:**

   After starting, Swagger UI will be accessible
   at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

   You can use Swagger UI to interact with your API and view documentation.

## Additional Configurations

- **H2 Database:**

  The project uses an H2 database. Adjust database settings in `application.yaml` if needed.