package priv.llf.mybatis.support;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: eleven
 * @since: 2018/3/10 16:42
 * @Description:
 */
@Data
@NoArgsConstructor
public class Page<T> implements Serializable {
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


    public Page(int pageNo,int pageSize){
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    /**
     * 获取总页数
     * @return
     */
    public long getTotalPageSize(){

        if (totalSize == 0)
        {
            return 0;
        }

        long TotalPageSize = this.totalSize/this.pageSize;

        return this.totalSize % pageSize == 0 ? TotalPageSize : TotalPageSize + 1;
    }
}
