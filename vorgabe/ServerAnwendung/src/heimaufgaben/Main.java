package heimaufgaben;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Throwable {
        Server server = new Server();
        server.main(args);
        System.out.println("Der Server wurde beendet.");
    }
}
