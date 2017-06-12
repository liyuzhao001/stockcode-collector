package tech.jibudao.stock.stockcode_collector.tables;

import java.util.Date;

/**
 * Stock
 * Created by Administrator on 2017/6/12.
 */
public class Stock_Table {
    private int id;
    private int stock_code;
    private String stock_name;
    private short stock_status;
    private String stock_exchange;
    private Date create_time;
    private Date update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock_code() {
        return stock_code;
    }

    public void setStock_code(int stock_code) {
        this.stock_code = stock_code;
    }

    public String getStock_name() {
        return stock_name;
    }

    public void setStock_name(String stock_name) {
        this.stock_name = stock_name;
    }

    public short getStock_status() {
        return stock_status;
    }

    public void setStock_status(short stock_status) {
        this.stock_status = stock_status;
    }

    public String getStock_exchange() {
        return stock_exchange;
    }

    public void setStock_exchange(String stock_exchange) {
        this.stock_exchange = stock_exchange;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

}