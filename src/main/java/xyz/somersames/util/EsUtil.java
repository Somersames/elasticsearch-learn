package xyz.somersames.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author szh
 * @create 2019-04-10 23:16
 **/
public class EsUtil {
    private static RestHighLevelClient client  =new RestHighLevelClient(RestClient.builder(
            new HttpHost("localhost",9200,"http")
    ));

    public static RestHighLevelClient getInstance(){
        return client;
    }
}
