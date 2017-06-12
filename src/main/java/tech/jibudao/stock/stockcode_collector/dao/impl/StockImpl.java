package tech.jibudao.stock.stockcode_collector.dao.impl;

import org.apache.ibatis.session.SqlSession;
import tech.jibudao.stock.stockcode_collector.dao.BaseDAO;
import tech.jibudao.stock.stockcode_collector.dao.IStock;
import tech.jibudao.stock.stockcode_collector.tables.Stock_Table;

/**
 * Created by Administrator on 2017/6/12.
 */
public class StockImpl implements IStock {

    @Override
    public int add(Stock_Table stock) {

        int count = 0;
        try (SqlSession session = BaseDAO.getSession()) {

            IStock stockDAO = session.getMapper(IStock.class);
            count = stockDAO.add(stock);
            session.commit();
        }

        return count;
    }

}