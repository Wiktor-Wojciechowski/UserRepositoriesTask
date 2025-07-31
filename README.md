# User Repositories Task
This API retrieves a user's  public non-fork GitHub repositories using GitHub's API.

The project uses JDK 21 and Spring Boot 3.5.4
## How to run

Clone the repository with
```
git clone https://github.com/Wiktor-Wojciechowski/UserRepositoriesTask/
```
or download the .zip file and extract it in the desired location.

To run the project, open the folder and run the command: 
```
mvnw spring-boot:run
```

## Usage

The API exposes a single GET endpoint that returns repositories for a given user.
```
localhost:8080/users/{user}/repositories
```

Replace {user} with the desired user's username to get the repositories.

Exemplary response of the endpoint:
```
[
  {
  name: "Hello-World",
  ownerLogin: "octocat",
  branches: {
    name: "master"
    sha: "7e068727fdb347b685b658d2981f8c85f7bf0585"
    }
  }
]
```
## Testing
You can run the tests by using
```
mvnw test
```
