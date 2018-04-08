package priv.llf.mybatis.typehandler;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: Eleven
 * @Since: 2018/4/7 23:44
 * @Description:
 */
public class JsonObjectTypeHandler extends BaseTypeHandler<JSONObject> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JSONObject parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i,JSONObject.toJSONString(parameter));
    }

    @Override
    public JSONObject getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);
        if (StringUtils.isBlank(str)) return null;
        return JSONObject.parseObject(str);
    }

    @Override
    public JSONObject getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String str = rs.getString(columnIndex);
        if (StringUtils.isBlank(str)) return null;
        return JSONObject.parseObject(str);
    }

    @Override
    public JSONObject getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String str = cs.getString(columnIndex);
        if (StringUtils.isBlank(str)) return null;
        return JSONObject.parseObject(str);
    }
}
