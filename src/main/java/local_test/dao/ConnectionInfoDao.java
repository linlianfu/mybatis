package local_test.dao;

import local_test.model.ConnectionInfo;
import org.springframework.stereotype.Repository;
import priv.llf.mybatis.dao.BaseMybatisDaoImpl;

/**
 * @author: eleven
 * @since: 2018/3/10 19:31
 * @Description:
 */
@Repository("connectionInfo")
public class ConnectionInfoDao extends BaseMybatisDaoImpl<ConnectionInfo,String>{
}
