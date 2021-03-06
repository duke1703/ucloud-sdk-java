package cn.ucloud.usql.client;

import cn.ucloud.common.handler.UcloudHandler;
import cn.ucloud.common.pojo.Account;
import cn.ucloud.usql.model.ListCatalogDatabasesParam;
import cn.ucloud.usql.model.ListCatalogDatabasesResult;
import cn.ucloud.usql.pojo.USQLConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNull;


/**
 * @Description : USQL.ListCatalogDatabases 测试
 * @Author : ucloud-sdk-generator
 * @Date : 2019-03-13 07:56
 **/
public class ListCatalogDatabasesTest {

    private USQLClient client;

    private ListCatalogDatabasesParam param;


    @Before
    public void setUp() throws Exception {
        client = new DefaultUSQLClient(new USQLConfig(
                new Account(System.getenv("UCloudPrivateKey"),
                        System.getenv("UCloudPublicKey"))));
        String region = "cn-bj2";
        param = new ListCatalogDatabasesParam(region);
        param.setProjectId("org-izug1m");
    }


    @Test
    public void listCatalogDatabases() {
        try {
            ListCatalogDatabasesResult result = client.listCatalogDatabases(param);
            JSONComparator.jsonComparator(result);
        } catch (Exception e) {
            assertNull(e);
        }
    }
}