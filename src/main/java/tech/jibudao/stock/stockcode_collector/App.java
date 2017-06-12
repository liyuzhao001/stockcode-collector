package tech.jibudao.stock.stockcode_collector;

import tech.jibudao.stock.stockcode_collector.dao.impl.StockImpl;
import tech.jibudao.stock.stockcode_collector.tables.Stock_Table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {

        String regex_sh = "<div class=\"sltit\"><a name=\"sh\"/>上海股票</div>\\s*<ul>(.*?)\\s*</ul>";

        String regex_sz = "<div class=\"sltit\"><a name=\"sz\"/>深圳股票</div>\\s*<ul>(.*?)\\s*</ul>";

        String regex_li = "<li>.*?>(.*?)\\((\\d+)\\)</a></li>";

        String urlString = "http://quote.eastmoney.com/stocklist.html";

        String html = HTMLSourceCollectUtil.get(urlString, "GBK");

        String sh_CodeListString = null;
        String sz_CodeListString = null;

        Pattern pattern = Pattern.compile(regex_sh);
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            sh_CodeListString = matcher.group(1);
        }

        pattern = Pattern.compile(regex_sz);
        matcher = pattern.matcher(html);
        if (matcher.find()) {
            sz_CodeListString = matcher.group(1);
        }

        StockImpl stockDao = new StockImpl();


        List<Stock_Table> stockList = new ArrayList<>();
        pattern = Pattern.compile(regex_li);
        matcher = pattern.matcher(sh_CodeListString);

        while (matcher.find()) {

            Stock_Table stock = new Stock_Table();
            stock.setStock_code(Integer.valueOf(matcher.group(2).trim()));
            stock.setStock_name(matcher.group(1).trim());
            stock.setStock_exchange("上海");

            try {
                stockDao.add(stock);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            stockList.add(stock);
        }

        System.out.println(sh_CodeListString);

        System.out.println("---------- ----------");


        stockList = new ArrayList<>();
        pattern = Pattern.compile(regex_li);
        matcher = pattern.matcher(sz_CodeListString);

        while (matcher.find()) {
            Stock_Table stock = new Stock_Table();
            stock.setStock_code(Integer.valueOf(matcher.group(2).trim()));
            stock.setStock_name(matcher.group(1).trim());
            stock.setStock_exchange("深圳");

            try {
                stockDao.add(stock);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            stockList.add(stock);
        }

        System.out.println(sz_CodeListString);
    }
}