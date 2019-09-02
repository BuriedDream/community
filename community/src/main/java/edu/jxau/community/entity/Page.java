package edu.jxau.community.entity;

/**
 * @title: community
 * @ClassName Page.java
 * @Description: 封装分页信息
 * @Author: liam
 * @Version:
 **/
public class Page {

    /**
     * 当前页
     */
    private int current = 1;
    /**
     * 每页显示的个数
     */
    private int limit = 10;
    /**
     * 总记录数
     */
    private int rows;
    /**
     * 所在资源路径
     */
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current >= 1){
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit > 0 && limit < 50) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows > 0){
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String url) {
        this.path = url;
    }

    /**
     * 得到当前页
     * @return
     */
    public int getOffset(){
        return (current - 1)*limit;
    }

    /**
     * 得到总页数
     * @return
     */
    public int getTotal(){
        if(rows/limit == 0){
            return rows/limit;
        }else {
            return rows/limit + 1;
        }
    }

    /**
     * 起始页码
     * @return
     */
    public int getFrom(){
        return (current - 2) > 0 ? (current - 2) : 1;
    }

    /**
     * 结束页码
     * @return
     */
    public int getTo(){
        return (current + 2) > getTotal() ? getTotal() : (current + 2);
    }
}
