package local_test.dao;

import local_test.model.ConnectionInfo;
import org.springframework.stereotype.Repository;
import priv.llf.mybatis.dao.BaseMybatisDaoImpl;

/**
 * @Author: calvin
 * @Since: 2018/3/10 19:31
 * @Description:
 */
@Repository("connectionInfo")
public class ConnectionInfoDao extends BaseMybatisDaoImpl<ConnectionInfo,String>{
}
