# Simple Web Server
[![CircleCI](https://circleci.com/gh/teru00/web-server.svg?style=svg&circle-token=ec26b5296ac0f527a50f678d6deb510cd1f6cfb4)](https://circleci.com/gh/teru00/web-server)

## Overview
Simple Web Server is a software for serving the files via HTTP.

## Description
This application can respond to the browser application.
and send static files of following the file type.

text file
 - HTML
 - CSS
 - JavaScript

binary file
 - PNG
 - JPEG
 - GIF

## Requirement
java version 1.8.0

## Usage
### Server Run
First of all, In your terminal, you can type following command.
```
git clone <this repo>
gradlew run
```

Next, open the browser application and type URL! (Chrome or FireFox)
```
http://localhost:8080/<resources name which your want to access>
```
your browser render Web page!

### Add Resources
you can add web resources to this server.
Note this server is the gradle project. you have to add resources to `src/main/resources`.
Check [Gradle docs](https://docs.gradle.org/current/userguide/userguide)

### Testing
Type the command `gradlew test` application root directory.

## Author

[terufumi shimoji](https://github.com/teru00)
