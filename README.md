# AbacSpringSecurity
Attribute-Based Access Control with Spring Security

## Aknowledgments

This repo was forked from original developed by [mostafa8eltaher](https://github.com/mostafa8eltaher/AbacSpringSecurity) and adapted for a straighforward playground deloyment for experimenting/testing Attribute Based Access Control (ABAC) policies in Spring. For more details, check [The DZone article](https://dzone.com/articles/simple-attribute-based-access-control-with-spring)

## Requirements

* Maven 3.x
* Java SDK 8+
* Docker 3.x

## How to Build
```shell
mvn clean install
```
# Sample Application
## How to deploy and run

Prior deploying the application, you must build the app using the instructions above.
```sbtshell
docker-compose up -d
```

## How to test

All REST requests with sample data are in [AbacSpringSecurity.postman_collection.json](AbacSpringSecurity.postman_collection), you can import it into Postman app or chrome extension.

- Add new project using **POST /sample-issue-tracker/projects/**
- Assign PM to the project using **PUT /sample-issue-tracker/projects/{project_id}/pm/**
- Add users to the project using **POST /sample-issue-tracker/projects/{project_id}/users/**
- Add issues to the project using **POST /sample-issue-tracker/projects/{project_id}/issues/**
- Assign issues to users using **PUT /sample-issue-tracker/projects/{project_id}/issues/{issue_id}/assignee**
- Update issue's status **PUT /sample-issue-tracker/projects/{project_id}/issues/{issue_id}/status**

## User Authentication
The sample application uses HTTP Basic Authentication and blow are the users that can be used:
- admin/password	
- pm1/password
- pm2/password
- dev1/password
- dev2/password
- test1/password
- test2/password

## Policy Rules

Default rules are stored in [default-policy.json](sample-issue-tracker/src/main/resources/edu/mostafa/abac/security/policy/json/default-policy.json). Check check [The DZone article](https://dzone.com/articles/simple-attribute-based-access-control-with-spring) for more details.

All users can be found and modified in **edu.mostafa.abac.config.InMemoryUserDetailsService** class.

**IMPORTANT**: This sample app does not implement a login method, you you must inject the Authentication header credentials for each user when experimenting. See the [InMemoryUserDetailsService.java](sample-issue-tracker/src/main/java/edu/mostafa/abac/config/InMemoryUserDetailsService.java) class for details. 