package local_test.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import javax.persistence.Entity;

/**
 * @Author: eleven
 * @since: 2018/3/10 19:24
 * @Description:
 */
@Data
@Entity
@Alias("connectionInfo")
public class ConnectionInfo{

    private String id;

    private String ip;

    private String title;
}
