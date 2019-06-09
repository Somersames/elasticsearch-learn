package xyz.somersames.create;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.Build;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import xyz.somersames.util.EsConstant;
import xyz.somersames.util.EsUtil;

import java.io.IOException;

/**
 * @author szh
 * @create 2019-05-06 22:21
 **/

@Slf4j
public class IndexCreate {
    private void indexCreate() throws IOException {
        RestHighLevelClient client = EsUtil.getInstance();
        CreateIndexRequest request = new CreateIndexRequest(EsConstant.ES_INSERT_INDEX);
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("message");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        request.mapping(builder);
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        System.out.printf("", createIndexResponse.isAcknowledged());
    }

    public static void main(String[] args) throws IOException {
        new IndexCreate().indexCreate();
    }
}
