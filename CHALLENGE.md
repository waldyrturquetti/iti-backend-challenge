# Description

A password is considered valid when it meets the following criteria:

- Nine or more characters
- At least 1 digit
- At least 1 lowercase letter
- At least 1 uppercase letter
- At least 1 special character
  - Consider the following characters as special: !@#$%^&*()-+
- No repeated characters within the set

Example:  

```c#
IsValid("") // false  
IsValid("aa") // false  
IsValid("ab") // false  
IsValid("AAAbbbCc") // false  
IsValid("AbTp9!foo") // false  
IsValid("AbTp9!foA") // false
IsValid("AbTp9 fok") // false
IsValid("AbTp9!fok") // true
```

> **_Note:_**  Whitespace characters should not be considered as valid characters.

## Problem

Build an application that exposes a web API that validates whether a password is valid.

Input: A password (string).  
Output: A boolean indicating whether the password is valid.

Although our applications are written in Kotlin and C# (.net core), you don't need to write your solution using them. Use the programming language you feel most confident with.

## Areas of Focus

- Unit / integration tests
- Abstraction, coupling, extensibility, and cohesion
- API design
- Clean Code
- SOLID
- Solution documentation in the *README* 

## Areas We Won't Evaluate

- Dockerfile
- CI/CD scripts
- Postman collections or execution tools

### About Documentation

At this stage of the selection process, we want to understand the decisions behind your code, so it's essential that the *README* contains some information regarding your solution.

Some tips for what we expect to see:

- Basic instructions on how to run the project;
- Details about your solution, we'd like to know your reasoning behind the decisions;
- If anything is unclear and you had to make assumptions, what were they and what motivated you to make these decisions.

## How We Expect to Receive Your Solution

This stage is eliminatory, so we expect the code to reflect this importance.

If you encounter any issues, questions, or problems, please contact us. We're here to help.

Send us the link to a public repository with your solution.
