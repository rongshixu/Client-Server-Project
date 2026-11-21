package heimaufgaben;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Throwable {
        /*//Verbindung zum Server aufbauen
        Socket soc = new Socket("localhost", 2022);
        //Daten durch einen BufferedReader lesen lassen
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(soc.getInputStream(), "utf-8"));
        //Warten bis mindenstens eine ganze Zeile gelesen werden kann
        while(!fromServer.ready()) Thread.sleep(5);

        String line = fromServer.readLine(); //Eine Zeile einlesen
        System.out.print(line);

        final Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        BufferedWriter toServer = new BufferedWriter(new OutputStreamWriter(soc.getOutputStream(), "utf-8"));
        toServer.write(str);



        BufferedReader fromServer1 = new BufferedReader(new InputStreamReader(soc.getInputStream(), "utf-8"));
        //Warten bis mindenstens eine ganze Zeile gelesen werden kann
        while(!fromServer1.ready()) Thread.sleep(5);
        String line1 = fromServer.readLine(); //Eine Zeile einlesen
        System.out.print(line1);

        //Warten bis mindenstens eine ganze Zeile gelesen werden kann
        fromServer.close();
        fromServer1.close();
        toServer.close();
        soc.close();*/
        //Verbindung zum Server aufbauen
        Socket soc = new Socket("localhost", 20222);
        //Daten durch einen BufferedReader lesen lassen

        InputStream inputStream = soc.getInputStream();
        byte[] bytes = new byte[1024];
        int len = inputStream.read(bytes);
        System.out.println(new String(bytes, 0, len));

        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        OutputStream outputStream = soc.getOutputStream();
        outputStream.write(str.getBytes(StandardCharsets.UTF_8));

        InputStream inputStream1 = soc.getInputStream();
        byte[] bytes1 = new byte[1024];
        int len1 = inputStream.read(bytes1);
        System.out.println(new String(bytes1, 0, len1));

        InputStream inputStream2 = soc.getInputStream();
        byte[] bytes2 = new byte[1024];
        int len2 = inputStream.read(bytes2);
        System.out.println(new String(bytes2, 0, len2));

        Scanner scanner1 = new Scanner(System.in);
        String str1 = scanner.next();

        OutputStream outputStream1 = soc.getOutputStream();
        outputStream1.write(str1.getBytes(StandardCharsets.UTF_8));

        InputStream inputStream3 = soc.getInputStream();
        byte[] bytes3 = new byte[1024];
        int len3 = inputStream.read(bytes3);
        System.out.println(new String(bytes3, 0, len3));



        soc.close();
    }
}
