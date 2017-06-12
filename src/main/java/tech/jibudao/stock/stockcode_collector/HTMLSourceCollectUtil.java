package tech.jibudao.stock.stockcode_collector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Html Source Collector
 * Created by Administrator on 2017/6/12.
 */
public class HTMLSourceCollectUtil {

    public static String get(String urlString, String charsetName) throws IOException {

        URL url = new URL(urlString);

        Charset charset = Charset.forName("UTF-8");
        if (null != charsetName) {
            charset = Charset.forName(charsetName);
        }

        StringBuilder html;
        try (InputStream inputStream = url.openStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            html = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                html.append(line);
            }
        }

        if (null == html) {
            return null;
        }

        return html.toString();
    }
}