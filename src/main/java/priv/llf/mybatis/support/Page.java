package priv.llf.mybatis.support;

import lombok.Data;

/**
 * Author：calvin
 * Date:  2017/8/19 0019
 */
@Data
public class Page {
    public static final String PAGE_KEY = "page";
    /**
     * 缺省每页的记录数
     */
    public static final int DEFAULT_PAGE_SIZE = 15;
    /**
     * 当前页中存放的数据集合
     */
    protected Object currentPageData;

    /**
     * 当前页第一条数据在总记录集的位置,从0开始
     */
    protected int currentPageStartIndex;

    /**
     * 每页的记录数
     */
    protected int pageSize = DEFAULT_PAGE_SIZE;

    /**
     * 整个总记录集拥有的总记录数
     */
    protected long totalSize;
    /**
     * 整个总记录集拥有的总页数
     */
    protected long totalPageSize;

    /**
     * 起始页码
     */
    protected int pageNo = 1;
}