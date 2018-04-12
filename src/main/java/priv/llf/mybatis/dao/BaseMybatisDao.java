package priv.llf.mybatis.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author: eleven
 * @since: 2018/3/10 16:42
 * @Description:
 */
public interface BaseMybatisDao<T,PK extends Serializable>{
    /**
     *暂时只支持xml的格式查询
     * @param statement xml文件当中sql查询语句的id，
     * @param primaryKey 表主键，必须保证查询的结果该值不会重复
     * @param paramObject 保留字段
     * @return
     */
    List<T> selectList(String statement, String primaryKey, Object paramObject);
}
