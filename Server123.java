package heimaufgaben;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Server{
    public static void main(String[] args) throws Throwable {
        System.out.println("ssssssss");
        /*ServerSocket srv = new ServerSocket(2022);
        //Auf eine Verbindung WARTEN und die ERSTE annehmen.
        Socket socket = srv.accept();
        //Einfacher Stream zum senden
        PrintStream toClient = new PrintStream(socket.getOutputStream(), true, "utf-8");
        toClient.println("Port: "); // Port: senden

        BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
        while(!fromClient.ready()) Thread.sleep(5);
        String line = fromClient.readLine(); //Eine Zeile einlesen

        PrintStream toClient1 = new PrintStream(socket.getOutputStream(), true, "utf-8");
        if (line.equals("2022")) {
            char c = '"';
            toClient1.println("Der Server wurde gestartet und wartet am Port 2022 auf Anfragen vom Client.");
            toClient1.println("Wenn Sie den Server beenden wollen, dann geben Sie " + c + "J" + c + " ein: ");
        } else {
            toClient1.println("Kein korrekter Port!");
            toClient1.println("Aktuell ist nur Port 2022 moeglich.");
        }
        //Warten bis mindenstens eine ganze Zeile gelesen werden kann



        toClient.close();
        toClient1.close();
        srv.close();
        socket.close();*/
        ServerSocket srv = new ServerSocket(20222);
        //Auf eine Verbindung WARTEN und die ERSTE annehmen.
        Socket socket = srv.accept();
        //Einfacher Stream zum senden

        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("Port: ".getBytes(StandardCharsets.UTF_8));

        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len = inputStream.read(bytes);
        String str = new String(bytes, 0, len);

        if (str.equals("2022")) {
            char c = '"';
            String str1 = "Wenn Sie den Server beenden wollen, dann geben Sie " + c + "J" + c + " ein: ";
            OutputStream outputStream1 = socket.getOutputStream();
            outputStream1.write("Der Server wurde gestartet und wartet am Port 2022 auf Anfragen vom Client." .getBytes(StandardCharsets.UTF_8));
            outputStream1.write(str1.getBytes(StandardCharsets.UTF_8));

            InputStream inputStream1 = socket.getInputStream();
            byte[] bytes1 = new byte[1024];
            int len1 = inputStream.read(bytes1);
            String str2 = new String(bytes1, 0, len1);

            if (str2.equals("J")) {
                OutputStream outputStream2 = socket.getOutputStream();
                outputStream2.write("Der Server wurde beendet.123" .getBytes(StandardCharsets.UTF_8));
            }


        } else {
            OutputStream outputStream1 = socket.getOutputStream();
            outputStream1.write("Kein korrekter Port!".getBytes(StandardCharsets.UTF_8));
            outputStream1.write("Aktuell ist nur Port 2022 moeglich.".getBytes(StandardCharsets.UTF_8));
        }


        socket.close();
        srv.close();
    }

}

