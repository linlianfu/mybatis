package priv.llf.mybatis.sql;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.Assert;
import priv.llf.mybatis.constant.MapKeyConstant;
import priv.llf.mybatis.support.Page;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Author：calvin
 * Date:  2017/8/18 0018
 *  * 描述：
 *        1.必须提供无惨构造函数
 *        2提供的sql语句方法必须是public的，返回值必须为String，可以为static。.
 */
@Slf4j
@NoArgsConstructor
public class BaseSqlProvider {

    /**
     * 插入实体的SQL
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public String save(Object obj) throws Exception {
        SQL sql = new SQL();
        Class<?> clazz = obj.getClass();
        Table table = clazz.getAnnotation(Table.class);
        sql.INSERT_INTO(table.name());
        List<Field> list = getAllField(obj);
        for (Field field : list) {
            field.setAccessible(true);
            Object colValue = field.get(obj);
            Column column = field.getAnnotation(Column.class);
            Id id = field.getAnnotation(Id.class);
            if (column == null)
                continue;
            if (id != null) {
                colValue = UUID.randomUUID().toString().replace("-", "");
                field.set(obj, colValue);
            }
            String colName = "";
            if (column != null)
                colName = StringUtils.isBlank(column.name()) ? field.getName() : column.name();
                 sql.VALUES(String.format("`%s`",colName), String.format("#{%s}", field.getName()));
        }
        return sql.toString();
    }


    /**
     * 根据ID删除的sql
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public String deleteById(Object obj) throws Exception {
        SQL sql = new SQL();
        Table table = obj.getClass().getAnnotation(Table.class);
        sql.DELETE_FROM(table.name());
        Field idField = getIdField(obj);
        idField.setAccessible(true);
        Column idColum = idField.getAnnotation(Column.class);
        String idColName = StringUtils.isBlank(idColum.name()) ? idField.getName() : idColum.name();
        sql.WHERE(String.format("`%s` = #{%s}", idColName, idField.getName()));
        return sql.toString();
    }
    /**
     * 更新实体的SQL
     * @param obj
     * @return
     * @throws Exception
     */
    public String update(Object obj) throws Exception {
        SQL sql = new SQL();
        Table table = obj.getClass().getAnnotation(Table.class);
        sql.UPDATE(table.name());
        List<Field> list = getAllField(obj);
        Field idField = null;
        Column idColum = null;
        for (Field field : list) {
            field.setAccessible(true);
            Column column = field.getAnnotation(Column.class);
            Id id = field.getAnnotation(Id.class);
            if (column == null) {
                continue;
            }

            Object colValue = field.get(obj);

            if (colValue == null ||((colValue instanceof String)
                    && StringUtils.isBlank(String.valueOf(colValue))))
                continue;

            if (id != null){
                idColum = column;
                idField = field;
            }
            String colName = StringUtils.isBlank(column.name()) ? field.getName() : column.name();
            sql.SET(String.format("%s = #{%s}", colName, field.getName()));
        }
        Assert.notNull(idField, "主键ID为空,不允许修改全表");
        Assert.hasLength(idColum.name(),"没有指定主键映射关系");
        String idColName = StringUtils.isBlank(idColum.name()) ? idField.getName() : idColum.name();
        sql.WHERE(String.format("`%s` = #{%s}", idColName, idField.getName()));
        return sql.toString();
    }

    /**
     * 精确查询的sql生成
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public String findByCondition(Object obj) throws Exception {
        SQL sql = new SQL();
        Class<?> clazz = obj.getClass();
        Table table = clazz.getAnnotation(Table.class);
        sql.SELECT(getAllFieldString(clazz));
        sql.FROM(table.name());
        List<Field> list = getAllField(obj);
        for (Field field : list) {
            field.setAccessible(true);
            Object colValue = field.get(obj);
            Column column = field.getAnnotation(Column.class);
            if (column != null && colValue != null) {
                if ((colValue instanceof String) && StringUtils.isBlank(String.valueOf(colValue)))
                    continue;
                if ((colValue instanceof Integer) && String.valueOf(colValue).equals("0"))
                    continue;
                String colName = StringUtils.isBlank(column.name()) ? field.getName() : column.name();
                sql.WHERE(String.format("`%s` = #{%s}", colName, field.getName()));
            }
        }
        return sql.toString();
    }
    /**
     * 精确查询的sql生成
     *
     * @param param
     * @return
     * @throws Exception
     */
    public String findPage(Map<String,Object> param ) throws Exception {
        Page page     = (Page)param.get(Page.PAGE_KEY);
        Object entity = param.get(MapKeyConstant.ENTITY);
        SQL sql = new SQL();
        Class<?> clazz = entity.getClass();
        Table table = clazz.getAnnotation(Table.class);
        sql.SELECT(getAllFieldString(clazz));
        sql.FROM(table.name());
        List<Field> list = getAllField(entity);
        for (Field field : list) {
            field.setAccessible(true);
            Object colValue = field.get(entity);
            Column column = field.getAnnotation(Column.class);
            if (column != null && colValue != null) {
                if ((colValue instanceof String) && StringUtils.isBlank(String.valueOf(colValue)))
                    continue;
                if ((colValue instanceof Integer) && String.valueOf(colValue).equals("0"))
                    continue;
                String colName = StringUtils.isBlank(column.name()) ? field.getName() : column.name();
                sql.WHERE(String.format("`%s` = #{%s}", colName, field.getName()));
            }
        }
        int limit = page.getPageSize();
        int offset = (page.getPageNo()-1)*limit;
        return sql.toString()+" limit "+String.format("%s , %s", offset, limit);
    }

    /**
     * 查询所有的sql
     *
     * @param
     * @return
     * @throws Exception
     */
    public  String findAll(Object obj) throws Exception {
        SQL sql = new SQL();
        Class<?> clazz = obj.getClass();
        Table table = clazz.getAnnotation(Table.class);
        sql.SELECT(getAllFieldString(clazz));
        sql.FROM(table.name());
        return sql.toString();
    }

    /**
     * 统计总数
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public String count(Object obj) throws Exception {
        SQL sql = new SQL();
        Table table = obj.getClass().getAnnotation(Table.class);
        Field idField = getIdField(obj);
        idField.setAccessible(true);
        Column idColum = idField.getAnnotation(Column.class);
        String idColName = StringUtils.isBlank(idColum.name()) ? idField.getName() : idColum.name();
        sql.SELECT("count(`" + idColName + "`)");
        sql.FROM(table.name());
        List<Field> list = getAllField(obj);
        for (Field field : list) {
            field.setAccessible(true);
            Object colValue = field.get(obj);
            Column column = field.getAnnotation(Column.class);
            if (column != null && colValue != null) {
                if ((colValue instanceof String) && StringUtils.isBlank(String.valueOf(colValue)))
                    continue;
                if ((colValue instanceof Integer) && String.valueOf(colValue).equals("0"))
                    continue;
                String colName = StringUtils.isBlank(column.name()) ? field.getName() : column.name();
                sql.WHERE(String.format("`%s` = #{%s}", colName, field.getName()));
            }
        }
        return sql.toString();
    }

    /**
     * 获取所有的带有@Id和@Column的列的，inser，select字符串
     *
     * @param t
     * @return返回带有实体各个属性别名的查询sql
     */
    public <T>String getAllFieldString(Class<T> t) {
        StringBuilder sb = new StringBuilder(300);
        while (true) {
            Field[] fields = t.getDeclaredFields();
            for (Field field : fields) {
                Column column = field.getAnnotation(Column.class);
                if (column != null)
                    sb.append("`").append(StringUtils.isBlank(column.name()) ? field.getName() : column.name()).append("`");
                if (column != null)
                    sb.append(" as ").append(field.getName()).append(",");
            }
            if (t.getSuperclass() == Object.class)
                break;
            t = (Class<T>)t.getSuperclass();
        }
        sb.setCharAt(sb.length() - 1, ' ');
        return sb.toString();
    }

    /**
     * 获取所有的带有@Id和@Column的列
     *
     * @param obj
     * @return
     */
    private List<Field> getAllField(Object obj) {
        Class<?> clazz = obj.getClass();
        List<Field> list = new ArrayList<>();
        while (true) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Column column = field.getAnnotation(Column.class);
                if (column != null)
                    list.add(field);
            }
            if (clazz.getSuperclass() == Object.class)
                break;
            clazz = clazz.getSuperclass();
        }
        return list;
    }
    /**
     * 获取带有@Id的field
     *
     * @param obj
     * @return
     */
    private Field getIdField(Object obj) {
        Class<?> clazz = obj.getClass();
        Field idField = null;
        while (true) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                Id id = field.getAnnotation(Id.class);
                if (id != null) {
                    idField = field;
                    break;
                }
            }
            if (idField != null || clazz.getSuperclass() == Object.class)
                break;
            clazz = clazz.getSuperclass();
        }
        return idField;
    }



}
