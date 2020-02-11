package com.company;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.UnexpectedException;
import java.util.Scanner;

public class Main {

    private static Socket socket;

    public static void main(String[] args) {
        System.out.print("Введите имя: ");
        String name = new Scanner(System.in).nextLine();
        try{
                socket = new Socket("localhost", 3000);
                Thread sendMessage = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            try {
                                String text;
                                System.out.print("Введите сообщение: ");
                                text = new Scanner(System.in).nextLine();
                                OutputStream output = socket.getOutputStream();
                                PrintWriter writer = new PrintWriter(output, true);
                                writer.println(name+": "+text);
                            } catch (IOException e) {
                                System.err.println(e.getMessage());
                                break;
                            }
                        }

                    }
                });

                Thread readMessage = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            try {
                                InputStream input = socket.getInputStream();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                                String response = reader.readLine();
                                System.out.println(response);
                            }
                            catch (IOException e){
                                System.err.println(e.getMessage());
                                break;
                            }
                        }

                    }
                });

                sendMessage.start();
                readMessage.start();
        } catch (IOException ex) {
            System.out.println( ex.getMessage());
        }
    }
}
