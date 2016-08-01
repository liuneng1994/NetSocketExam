package com.hand.Exam2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket sfd = new ServerSocket(32000);
            Socket afd = sfd.accept();
            Thread thread = new Thread(new Handler(afd, "../Exam1/target.pdf"));
            thread.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
