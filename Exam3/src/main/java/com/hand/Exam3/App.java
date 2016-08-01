package com.hand.Exam3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.gson.Gson;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        String information = Get("http://hq.sinajs.cn/list=sz300170");
        String s = information.substring(information.indexOf("\"") + 1, information.lastIndexOf("\""));
        String[] infos = s.split(",");
        Stock stock = new Stock(infos[0], infos[1], infos[2], infos[3], infos[4], infos[5]);
        toJsonFile(stock, "stock.json");
        toXMLFile(stock, "stock.xml");
    }

    public static String Get(String url) {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = null;
        try {
            URL requestUrl = new URL(url);
            URLConnection urlConnection = requestUrl.openConnection();
            urlConnection.connect();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "gbk"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
    }

    public static void toJsonFile(Stock stock, String path) {
        File file = new File(path);
        Gson gson = new Gson();
        PrintWriter writer = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new PrintWriter(new FileOutputStream(file));
            writer.print(gson.toJson(stock));
            writer.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public static void toXMLFile(Stock stock, String path) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e2) {
            e2.printStackTrace();
        }
        Document document = builder.newDocument();
        Element root = document.createElement("xml");
        Element st = document.createElement("stock");
        root.appendChild(st);
        Field[] fields = Stock.class.getDeclaredFields();
        for (Field fd : fields) {
            Element e = document.createElement(fd.getName());
            try {
                e.setTextContent((String) fd.get(stock));
            }
            catch (Exception e1) {
                e1.printStackTrace();
            }
            st.appendChild(e);
        }
        FileOutputStream file = null;
        document.appendChild(root);
        try {
            OutputFormat of = new OutputFormat(document);
            of.setIndenting(true);
            of.setIndent(4);
            file = new FileOutputStream(path);
            XMLSerializer xmlSerializer = new XMLSerializer(file, of);
            xmlSerializer.serialize(document);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (file != null) {
                try {
                    file.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
