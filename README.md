# GitHub Repository Proxy


This application exposes a simple HTTP endpoint (/github/{username}) that allows fetching all public GitHub repositories of a user that are not forks, along with their branches and the latest commit SHA.
It acts as a proxy to the GitHub API and returns data in the following format:

 ```json
[
  {
    "repositoryName": "repo1",
    "ownerLogin": "username",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "abc123"
      }
    ]
  }
]
``` 

For a non-existent user, the endpoint returns HTTP 404:

 ```json
{
  "status": 404,
  "message": "User unknown not found"
}
``` 




## How It's Made

- Stack: Java 25, Spring Boot 4.0.1 (WebMVC), Gradle (Kotlin DSL)

- Architecture: Controller → Service → Client

- External API: GitHub REST API v3

- Testing:

	- Integration tests only, using WireMock to simulate GitHub API

	- RestTestClient for HTTP-level testing of /github/{username} endpoint



## Integration Tests

- Integration tests use WireMock to simulate the GitHub API.

- Tests cover:

	- fetching repositories with branches (excluding forks)

	- the HTTP /github/{username} endpoint

	- proper handling of 404 for non-existent users
	


## Running the Application

1. Clone the repository:

```bash
git clone <repo-url>
```


2. Build the project:

```bash
./gradlew build
```

3. Run the application:

```bash
./gradlew bootRun
```

4. Call the endpoint using a browser or curl:

```bash
curl http://localhost:8080/github/<username>
```