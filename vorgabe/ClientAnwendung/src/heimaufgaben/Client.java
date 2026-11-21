package heimaufgaben;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    public static void main(String[] args) throws Throwable {
        //Verbindung zum Server aufbauen
        System.out.print("IP-Adresse: ");
        Scanner scanner0 = new Scanner(System.in);
        String str0 = scanner0.next();
        //check the localhost
        if (str0.equals("127.0.0.1") || str0.equals("localhost")) {
            System.out.print("Port: ");
            Scanner scanner1 = new Scanner(System.in);
            String str1 = scanner1.next();
            if (str1.equals("2022")) {
                //if the provided port is 2022, enter the server
                try {
                    Socket soc = new Socket("localhost", 2022);
                    System.out.println();
                    System.out.println("Eine TCP-Verbindung zum Server mit IP-Adresse localhost (Port: 2022) wurde hergestellt. Sie koennen nun Ihre Anfragen an den Server stellen.");
                    System.out.println();

                    //Jetzt koennen Sie Dienste benutzen
                    BufferedReader fromClient = new BufferedReader(new InputStreamReader(soc.getInputStream()));
                    BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream()));

                    //while-Schleife: Nachdem letzte Operation endet, kann der Client immer neue Input eingeben.
                    while (true) {
                        Scanner scanner2 = new Scanner(System.in);
                        String str2 = scanner2.nextLine();

                        // exit the process when client inputs EXIT
                        if (str2.equals("EXIT")){
                            System.out.println("Die Verbindung zum Server wurde beendet.");
                            System.out.println();
                            break;
                        }

                        toServer.write(str2);
                        toServer.newLine();
                        toServer.flush();

                        //receive the response from server
                        String fromC = fromClient.readLine();
                        // when server responds with ERROR, cut the "ERROR"
                        if (fromC.matches("ERROR (.+)?")) {
                            Pattern pattern = Pattern.compile("ERROR (.+)?");
                            Matcher matcher = pattern.matcher(fromC);
                            if (matcher.find()) {
                                System.out.println(matcher.group(1));
                                System.out.println();
                            }
                        } else { // else display output from server
                            System.out.println(fromC);
                            System.out.println();
                        }

                    }

                    fromClient.close();
                    toServer.close();
                    soc.close();

                } catch (ConnectException connectException) { // if any error occurs inside the try, catch the exception
                    System.out.println();
                    System.out.println("Fehler beim Verbindungsaufbau! Es konnte keine TCP-Verbindung zum Server mit der IP-Adresse localhost (Port: 2022) hergestellt werden.");
                    System.out.println();
                }


            } else { // if port != 2022
                System.out.println();
                System.out.println("Kein korrekter Port!");
                System.out.println("Aktuell ist nur Port 2022 moeglich.");
                System.out.println();
            }

        } else { // if address is incorrect
            System.out.println();
            System.out.println("Falsche IP-Adresse!");
            System.out.println("Aktuell ist nur die IPv4-Adresse 127.0.0.1 und die Eingabe localhost moeglich.");
            System.out.println();
        }
    }
}