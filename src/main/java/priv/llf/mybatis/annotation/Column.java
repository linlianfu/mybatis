package priv.llf.mybatis.annotation;

import java.lang.annotation.*;

/**
 * @author: eleven
 * @since: 0.1.0
 * @date :2018/4/12 16:18
 * @description: 描述model和数据库表的映射关系
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {

    String name() default "";
}
