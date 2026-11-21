# Java TCP Client-Server Service

A small Java TCP client/server application that provides a set of text-based commands over port `2022`.

## Overview

The server accepts one client connection at a time and executes commands sent by the client. The client is a console application that connects to the local server and forwards commands entered by the user.

The project contains separate IntelliJ IDEA modules:

- `vorgabe/ServerAnwendung` - TCP server
- `vorgabe/ClientAnwendung` - TCP client

Prebuilt JAR files are available in each module's `out/artifacts` directory.

## Requirements

- Java 8 or newer
- Two terminals or command prompts
- Internet access for the `HOLIDAYS` and `LATEST NEWS` commands

## Running the Prebuilt JAR Files

Start the server first:

```bash
java -jar vorgabe/ServerAnwendung/out/artifacts/ServerAnwendung_jar/ServerAnwendung.jar
```

Enter `2022` when prompted for the port.

Then start the client in another terminal:

```bash
java -jar vorgabe/ClientAnwendung/out/artifacts/ClientAnwendung_jar/ClientAnwendung.jar
```

Enter `127.0.0.1` or `localhost` as the IP address, then enter `2022` as the port.

## Building from Source

From the repository root, compile the server and client into separate directories:

```bash
mkdir -p build/server build/client
javac -d build/server vorgabe/ServerAnwendung/src/heimaufgaben/*.java
javac -d build/client vorgabe/ClientAnwendung/src/heimaufgaben/*.java
```

Run the server:

```bash
java -cp build/server heimaufgaben.Main
```

Run the client:

```bash
java -cp build/client heimaufgaben.Main
```

On Windows Command Prompt, use the following build commands instead:

```bat
mkdir build\server build\client
javac -d build\server vorgabe\ServerAnwendung\src\heimaufgaben\*.java
javac -d build\client vorgabe\ClientAnwendung\src\heimaufgaben\*.java
```

## Available Commands

| Command | Description |
|---|---|
| `PING` | Returns `PONG`. |
| `ECHO <text>` | Returns the supplied text. |
| `CURRENT TIME` | Returns the server's local time in `HH:mm:ss` format. |
| `CURRENT DATE` | Returns the server's local date in `dd.MM.yyyy` format. |
| `HISTORY` | Returns all commands recorded during the current client connection. |
| `HISTORY <count>` | Returns the most recent `<count>` commands. |
| `GET <URL> <resource>` | Requests a remote URL. |
| `HOLIDAYS <year>` | Returns public-holiday data for North Rhine-Westphalia for the supplied year. |
| `LATEST NEWS` | Returns the latest news data from the Tagesschau API. |
| `EXIT` | Requests disconnect/exit behavior. |

## Example Session

```text
PING
PONG

ECHO Hello from Java
Hello from Java

CURRENT TIME
15:01:05

HISTORY 2
ECHO Hello from Java CURRENT TIME
```

## Error Responses

- `ERROR 400 BAD REQUEST` - The command or argument is invalid.
- `ERROR 404 NOT FOUND` - No history is available or the history argument is invalid.

## Notes

- The server listens only on local port `2022`.
- The client currently accepts only `localhost` or `127.0.0.1`.
- External API responses and availability depend on the corresponding online services.
