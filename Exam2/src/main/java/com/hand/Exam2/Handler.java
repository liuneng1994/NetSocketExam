package com.hand.Exam2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;

public class Handler implements Runnable {
    private Socket afd;
    private String filePath;

    public Handler(Socket afd, String filePath) {
        this.afd = afd;
        this.filePath = filePath;
    }

    public void run() {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(filePath));
            out = new BufferedOutputStream(afd.getOutputStream());
            byte[] bf = new byte[1024];
            int count = 0;
            while ((count = in.read(bf)) != -1) {
                out.write(bf, 0, count);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                }
                catch (IOException e) {
                }
            }
        }
    }
}
