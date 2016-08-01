package com.hand.Exam2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket sfd = new Socket("127.0.0.1", 32000);
        File file = new File("target.pdf");
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        BufferedInputStream in = new BufferedInputStream(sfd.getInputStream());
        byte[] bf = new byte[1024];
        int count = 0;
        while ((count = in.read(bf)) != -1) {
            out.write(bf, 0, count);
        }
        out.close();
        in.close();
        sfd.close();
    }
}
