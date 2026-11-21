package heimaufgaben;


import java.net.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.URL;


public class Server{
    public static void main(String[] args) throws Throwable {
        //ein Port geben
        System.out.print("Port: ");
        Scanner scanner0 = new Scanner(System.in);
        String str0 = scanner0.next();

        System.out.println();

        //wenn Port korrekt ist, startet die Server
        if (str0.equals("2022")) {

            char c = '"';
            System.out.println("Der Server wurde gestartet und wartet am Port 2022 auf Anfragen vom Client.");
            System.out.print("Wenn Sie den Server beenden wollen, dann geben Sie " + c + "J" + c + " ein: ");

            //while-Schleife: Nachdem letzte Verbindung endet, kann der Server immer "accept"-Zustand bleiben.
            while (true) {
                ServerSocket srv = new ServerSocket(2022);
                //Auf eine Verbindung WARTEN und die ERSTE annehmen.
                Socket socket = srv.accept();
                //Einfacher Stream zum senden
                ArrayList<String> his = new ArrayList<>();
                //Fuer HISTORY bemerken wir, wie viele Command ausgefuehrt.
                int count = 0;

                //while-Schleife: Nachdem letzte Command ausgefuehrt hat, kann der Server immer naechste Command einlesen.
                while (true) {
                    //Wir baruchen IO-Stream Reader und Weiter
                    BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                    //reader Information to String
                    String str1 = fromClient.readLine();

                    //Fuer HISTORY bemerken wir die ausgefuehrte Commands.
                    his.add(str1);
                    his.add(" ");
                    //Fuer HISTORY bemerken wir, wie viele Command ausgefuehrt.
                    count++;

                    //PING Dienst
                    if (str1.equals("PING")) {
                        //PONG zuruekschreiben
                        toClient.write("PONG");
                        toClient.newLine();
                        toClient.flush();

                    }
                    //ECHO Dienst
                    else if (str1.matches("ECHO (.+)?")) {
                        Pattern pattern = Pattern.compile("ECHO (.+)?");
                        Matcher matcher = pattern.matcher(str1);
                        if (matcher.find()) {
                            toClient.write(matcher.group(1));
                            toClient.newLine();
                            toClient.flush();
                        }

                    }
                    //CURRENT TIME Dienst
                    else if (str1.equals("CURRENT TIME")) {
                        LocalTime localTime = LocalTime.now();
                        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm:ss");

                        toClient.write(dtfTime.format(localTime));
                        toClient.newLine();
                        toClient.flush();

                    }
                    //CURRENT DATE Dienst
                    else if (str1.equals("CURRENT DATE")) {
                        LocalDate localDate = LocalDate.now();
                        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("dd.MM.yyyy");

                        toClient.write(dtfTime.format(localDate));
                        toClient.newLine();
                        toClient.flush();

                    }
                    //Wenn Client EXIT, schliesst Server die Verbindung
                    else if (str1.equals("EXIT")) {
                        socket.close();
                        srv.close();

                    }
                    //HISTORY Dienst
                    else if (str1.equals("HISTORY")) {
                        //wenn es kein HISTORY gibt, schreibt es ERROR zurueck
                        if (count - 1 == 0){
                            toClient.write("ERROR 404 NOT FOUND");
                            toClient.newLine();
                            toClient.flush();
                        } else {
                            //Mit PrintStream schreiben wir HISTORY zu Dokumentation.txt file.
                            //dokumentation.txt only works when running from ide. from .jar it throws file not found exception
                            //PrintStream printStream = new PrintStream(new FileOutputStream("Dokumentation.txt"));

                            for (int i = 0; i < his.size() - 2; i++) {
                                //senden jede HISTORY zu Client
                                toClient.write(his.get(i).toString());
                                //senden jede HISTORY zu File
                                //printStream.println(his.get(i).toString());

                            }
                            toClient.newLine();
                            toClient.flush();
                        }


                    }
                    //HISTORY (with count) Dienst
                    else if (str1.matches("HISTORY (.+)?")) {
                        //wenn es kein HISTORY gibt, schreibt es ERROR zurueck
                        if (count - 1 == 0){
                            toClient.write("ERROR 404 NOT FOUND");
                            toClient.newLine();
                            toClient.flush();
                        } else {
                            Pattern pattern = Pattern.compile("HISTORY (.+)?");
                            Matcher matcher = pattern.matcher(str1);
                            if (matcher.find()) {
                                //HISTORY anzahl to String
                                String historyVal = matcher.group(1);
                                //check if the value from input is a number
                                if (historyVal.matches("-?\\d+")) {
                                    //Mit PrintStream schreiben wir HISTORY zu Dokumentation.txt file.

                                    //dokumentation.txt only works when running from ide. from .jar it throws file not found exception
                                    //PrintStream printStream = new PrintStream(new FileOutputStream("Dokumentation.txt"));

                                    // change the string to int
                                    int hi = Integer.parseInt(historyVal);
                                    if (hi > 0){
                                        //if the input is more than the history log, output all
                                        if (hi > count - 1) {
                                            for (int i = 0; i < count - 1; i++) {
                                                //senden jede HISTORY zu Client
                                                toClient.write(his.get(2 * i).toString() + " ");
                                                //senden jede HISTORY zu File
                                                //printStream.println(his.get(2 * i).toString() + " ");
                                            }
                                            toClient.newLine();
                                            toClient.flush();
                                        } else { //else print the log as per user input number
                                            for (int i = count - hi - 1; i < count - 1; i++) {
                                                //senden jede HISTORY zu Client
                                                toClient.write(his.get(2 * i).toString() + " ");
                                                //senden jede HISTORY zu File
                                                //printStream.println(his.get(2 * i).toString() + " ");
                                            }
                                            toClient.newLine();
                                            toClient.flush();
                                        }
                                    }
                                } else {
                                    toClient.write("ERROR 404 NOT FOUND");
                                    toClient.newLine();
                                    toClient.flush();
                                }

                            }
                        }

                    }
                    //Proxy Dienst
                    else if (str1.matches("GET (\\S+)? (\\S+)?")) {
                        Pattern pattern = Pattern.compile("GET (\\S+)? (\\S+)?");
                        Matcher matcher = pattern.matcher(str1);
                        if (matcher.find()) {
                            String ads = matcher.group(1);
                            String res = matcher.group(2);
                            URL url = new URL(ads);
                            URLConnection connection = url.openConnection();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            String info = bufferedReader.readLine();
                            System.out.println(info);
                            toClient.write(info);
                            toClient.newLine();
                            toClient.flush();

                        }

                    }
                    //HOLIDAYS API Dienst
                    else if (str1.matches("HOLIDAYS (.+)?")) {
                        Pattern pattern = Pattern.compile("HOLIDAYS (.+)?");
                        Matcher matcher = pattern.matcher(str1);
                        if (matcher.find()) {
                            //get the year input from the client
                            String year = matcher.group(1);
                            //check if the input year is valid
                            if (year.matches("^[12][0-9]{3}$")) {
                                // set the url to holiday api url and pass year input to the url
                                URL url = new URL("https://feiertage-api.de/api/?jahr=%3C" + year + "%3E&nur_land=NW");
                                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
                                String info = bufferedReader.readLine();

                                toClient.write(info);
                                toClient.newLine();
                                toClient.flush();
                            } else { // else 400
                                toClient.write("ERROR 400 BAD REQUEST");
                                toClient.newLine();
                                toClient.flush();
                            }

                        }

                    }
                    //LATEST NEWS API Dienst
                    else if (str1.matches("LATEST NEWS")) {
                        // set the url to latest news api url
                        URL url = new URL("https://www.tagesschau.de/api2/");
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
                            String info = bufferedReader.readLine();
                            System.out.println(info);
                            toClient.write(info);
                            toClient.newLine();
                            toClient.flush();

                    }

                    else {
                        toClient.write("ERROR 400 BAD REQUEST");
                        toClient.newLine();
                        toClient.flush();

                    }

                }

            }

        } else {
            System.out.println("Kein korrekter Port!");
            System.out.println("Aktuell ist nur Port 2022 moeglich.");
            System.out.println();

        }

    }

}
