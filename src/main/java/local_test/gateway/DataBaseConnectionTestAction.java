package local_test.gateway;

import local_test.dao.ConnectionInfoDao;
import local_test.model.ConnectionInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * @Author: calvin
 * @Since: 2018/3/10 16:42
 * @Description:
 */
@Controller
@RequestMapping("dataBaseConnectionTest")
public class DataBaseConnectionTestAction {
    public static  final Log log = LogFactory.getLog(DataBaseConnectionTestAction.class);
    @Autowired
    ConnectionInfoDao connectionInfoDao;
    @ResponseBody
    @RequestMapping(value = "connectionToDataBase",method = RequestMethod.GET)
    public void connectionToDataBase(){
        log.debug("收到连接数据库请求。。。。");

            List<ConnectionInfo> connectionInfoList = connectionInfoDao.selectList("findConnectionList","id","");
            if (CollectionUtils.isNotEmpty(connectionInfoList)){
                for (ConnectionInfo connectionInfo : connectionInfoList) {
                    log.debug(connectionInfo.toString());
                }
            }

//        List<ConnectionInfo> list = connectionInfoDao.getSqlSession().selectList("listConnection");
//        if (CollectionUtils.isNotEmpty(list)){
//                for (ConnectionInfo connectionInfo : list) {
//                    log.debug(connectionInfo.toString());
//                }
//            }
    }
}
