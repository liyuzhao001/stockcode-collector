package tech.jibudao.stock.stockcode_collector.dao.impl;

import org.apache.ibatis.session.SqlSession;
import tech.jibudao.stock.stockcode_collector.dao.BaseDAO;
import tech.jibudao.stock.stockcode_collector.dao.IStock;
import tech.jibudao.stock.stockcode_collector.tables.Stock_Table;

/**
 * Stock Implement
 * Created by Administrator on 2017/6/12.
 */
public class StockImpl implements IStock {

    @Override
    public int add(Stock_Table stock) {

        try (SqlSession session = BaseDAO.getSession()) {

            IStock stockDAO = session.getMapper(IStock.class);
            int count = stockDAO.add(stock);
            session.commit();

            return count;
        }
    }

    @Override
    public int upsert(Stock_Table stock) {

        try (SqlSession session = BaseDAO.getSession()) {

            IStock stockDAO = session.getMapper(IStock.class);
            Stock_Table oldStock = stockDAO.selectStockByStockCode(stock.getStock_code());

            int count = 0;

            // 新数据
            if (null == oldStock) {
                count = stockDAO.add(stock);
                session.commit();
                return count;
            }

            // 判断有字段数据更新，执行更新操作
            if (!compareStockIsSame(stock, oldStock)) {
                stock.setId(oldStock.getId());
                count = stockDAO.update(stock);
                session.commit();
                return count;
            }

            // 既不是新数据，又没有字段数据变更，不执行任何操作
            return count;
        }
    }

    @Override
    public int update(Stock_Table stock) {

        try (SqlSession session = BaseDAO.getSession()) {

            IStock stockDAO = session.getMapper(IStock.class);
            int count = stockDAO.update(stock);
            session.commit();

            return count;
        }
    }

    @Override
    public Stock_Table selectStockByStockCode(int stockCode) {

        try (SqlSession session = BaseDAO.getSession()) {

            IStock stockDAO = session.getMapper(IStock.class);
            Stock_Table stock = stockDAO.selectStockByStockCode(stockCode);

            return stock;
        }
    }


    /**
     * 逐个比较两个 Stock 的字段是否相同
     *
     * @param stock1
     * @param stock2
     * @return 有差异返回 FALSE，无差异返回 TRUE
     */
    private boolean compareStockIsSame(Stock_Table stock1, Stock_Table stock2) {

        if (stock1.getStock_code() != stock2.getStock_code()) {
            return false;
        }

        if (!stock1.getStock_name().equals(stock2.getStock_name())) {
            return false;
        }

        if (!stock1.getStock_exchange().equals(stock2.getStock_exchange())) {
            return false;
        }

        if (stock1.getStock_status() != stock2.getStock_status()) {
            return false;
        }

        return true;
    }
}