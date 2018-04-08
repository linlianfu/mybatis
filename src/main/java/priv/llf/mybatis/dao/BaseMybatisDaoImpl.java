package priv.llf.mybatis.dao;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.persistence.Column;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: calvin
 * @Since: 2018/3/10 16:42
 * @Description:
 */
@Slf4j
@NoArgsConstructor
public class BaseMybatisDaoImpl<T,PK extends Serializable> extends SqlSessionDaoSupport implements
        BaseMybatisDao<T, PK> {

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    public List<T> selectList(String statement, String primaryKey,Object paramObject){
        Assert.notNull(statement,"statement can't null");
        Assert.notNull(primaryKey,"primaryKey can't null");
        Class<T> clazz =  (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        // TODO: 2017/8/17 0017 后期完善查询条件，以及分页
        Map<String ,Map<String,String>> resultMap = getSqlSession().selectMap(statement,primaryKey);
        List<T> resultList  = new ArrayList<T>(resultMap.size());
        log.info("符合条件的数据共有{}条：",resultMap.size());
        Set<String> keySet = resultMap.keySet();
        for (String str : keySet){//获取单条记录
            Map<String,String> row = resultMap.get(str);
            T bean = BeanUtils.instantiate(clazz);

            Field[] fields =  bean.getClass().getDeclaredFields();
            for (int i = 0;i<fields.length; i++)
            {//将记录转换成model
                Column column = fields[i].getAnnotation(Column.class);
                if (column == null) continue;
                Class type = fields[i].getType();
                String fieldName = column.name();
                fields[i].setAccessible(true);
                if (row.containsKey(fieldName))
                {
                    try {
                            if (type == int.class || type == Integer.class){
                                fields[i].set(bean,Integer.parseInt(String.valueOf(row.get(fieldName))));
                            }else if (type == BigDecimal.class){
                                fields[i].set(bean,new BigDecimal(String.valueOf(row.get(fieldName))));
                            }else if (type == Boolean.class || type == boolean.class){
                                fields[i].set(bean,Integer.parseInt(String.valueOf(row.get(fieldName)))!=0);
                            } else {
                                fields[i].set(bean,row.get(fieldName));
                            }
                        } catch (IllegalAccessException e)
                        {
                            log.info(">>>>>字段设值失败。。。");
                            e.printStackTrace();
                        }
                }
            }
            resultList.add(bean);
        }
        return resultList;
    }
}
