package tech.jibudao.stock.stockcode_collector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) throws IOException {

        logger.info("开始更新数据……");

        String regex_sh = "<div class=\"sltit\"><a name=\"sh\"/>上海股票</div>\\s*<ul>(.*?)\\s*</ul>";

        String regex_sz = "<div class=\"sltit\"><a name=\"sz\"/>深圳股票</div>\\s*<ul>(.*?)\\s*</ul>";

        String regex_li = "<li>.*?>(.*?)\\((\\d+)\\)</a></li>";

        String urlString = "http://quote.eastmoney.com/stocklist.html";

        logger.info("开始获取页面源代码……");

        String html = HTMLSourceCollectUtil.get(urlString, "GBK");

        logger.info("页面源代码获取完毕！");

        // 上证 代码列表字符串
        String sh_CodeListString = null;

        // 深证 代码列表字符串
        String sz_CodeListString = null;

        logger.info("开始解析 Stock 代码列表……");

        // 匹配 上证代码列表
        Pattern pattern = Pattern.compile(regex_sh);
        Matcher matcher = pattern.matcher(html);
        if (matcher.find()) {
            sh_CodeListString = matcher.group(1);
        }

        // 匹配 深证代码列表
        pattern = Pattern.compile(regex_sz);
        matcher = pattern.matcher(html);
        if (matcher.find()) {
            sz_CodeListString = matcher.group(1);
        }

        logger.info("解析 Stock 代码列表完毕！");

        logger.info("开始解析 Stock 代码记录……");

        // 初始化 stock数据库操作
        StockImpl stockDao = new StockImpl();

        // 解析 上证 代码列表
        List<Stock_Table> stockList = new ArrayList<>();
        pattern = Pattern.compile(regex_li);
        matcher = pattern.matcher(sh_CodeListString);

        while (matcher.find()) {

            Stock_Table stock = new Stock_Table();
            stock.setStock_code(Integer.valueOf(matcher.group(2).trim()));
            stock.setStock_name(matcher.group(1).trim());
            stock.setStock_exchange("上海");

            try {
                stockDao.upsert(stock);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            stockList.add(stock);
        }

        logger.info("上证 Stock 代码记录写入完毕！");


        // 解析 深证 代码列表
        stockList = new ArrayList<>();
        pattern = Pattern.compile(regex_li);
        matcher = pattern.matcher(sz_CodeListString);

        while (matcher.find()) {
            Stock_Table stock = new Stock_Table();
            stock.setStock_code(Integer.valueOf(matcher.group(2).trim()));
            stock.setStock_name(matcher.group(1).trim());
            stock.setStock_exchange("深圳");

            try {
                stockDao.upsert(stock);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            stockList.add(stock);
        }

        logger.info("深证 Stock 代码记录写入完毕！");

        logger.info("操作完毕！");

    }
}