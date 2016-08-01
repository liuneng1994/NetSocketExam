package com.hand.Exam1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class App {
    public static void main(String[] args) {
        try {
            httpDownload("http://files.saas.hand-china.com/java/target.pdf", "target.pdf");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void httpDownload(String url, String path) throws MalformedURLException {
        URL requestUrl = new URL(url);
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        URLConnection conn;
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            conn = requestUrl.openConnection();
            conn.connect();
            in = new BufferedInputStream(conn.getInputStream());
            out = new BufferedOutputStream(new FileOutputStream(path));
            byte[] bf = new byte[1024];
            int count = 0;
            while ((count = in.read(bf)) != -1) {
                out.write(bf, 0, count);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
                out.close();
            }
            catch (IOException e) {
            }
        }
    }
}
