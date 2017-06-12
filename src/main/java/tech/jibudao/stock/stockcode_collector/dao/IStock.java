package tech.jibudao.stock.stockcode_collector.dao;

import tech.jibudao.stock.stockcode_collector.tables.Stock_Table;

/**
 * Stock
 * Created by Administrator on 2017/6/12.
 */
public interface IStock {

    int add(Stock_Table stock);
}
