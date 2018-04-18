package priv.llf.mybatis.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author: eleven
 * @since: 2018/3/10 16:42
 * @Description:
 * @param <T> 数据模型，对应数据库表的列，其中模型当中的字段必须使用{@link javax.persistence.Column}映射在数据库表中的列
 * @param <PK> 主键类型
 */
public interface BaseMybatisDao<T,PK extends Serializable>{
    /**
     *暂时只支持xml的格式查询
     * @param statement xml文件当中sql查询语句的id，
     * @param primaryKey 表主键，必须保证查询的结果该值不会重复
     * @param query 查询入参
     * @return
     */
    List<T> selectList(String statement, String primaryKey, Object query);
}
