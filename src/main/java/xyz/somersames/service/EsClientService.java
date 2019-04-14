package xyz.somersames.service;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.query.QuerySearchRequest;
import org.springframework.stereotype.Service;
import xyz.somersames.model.EsTestDto;
import xyz.somersames.util.EsUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author szh
 * @create 2019-04-10 23:20
 **/
@Service
public class EsClientService {

    public void save() throws IOException {
        RestHighLevelClient client = EsUtil.getInstance();
        Map<String,Object> map =new HashMap<>();
        map.put("a","a");
        map.put("b","b");
        IndexRequest request =new IndexRequest("test_a","test_a","1").source(map);
        client.index(request, RequestOptions.DEFAULT);
    }

    public void saveDto() throws IOException {
        RestHighLevelClient client = EsUtil.getInstance();
        EsTestDto esTestDto =new EsTestDto();
        esTestDto.setA("a");
        esTestDto.setB("b");
        esTestDto.setC(1);
        esTestDto.setDate(new Date());
        IndexRequest request =new IndexRequest("testc_dto","testb_dto","1").source(JSON.toJSONString(esTestDto), XContentType.JSON);
        client.index(request, RequestOptions.DEFAULT);
    }

    public void testQuery() throws ParseException, IOException {
        RestHighLevelClient client = EsUtil.getInstance();
//        QuerySearchRequest request =new QuerySearchRequest();
        SearchRequest searchRequest = new SearchRequest("testc_dto");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.rangeQuery("date").gte(convertDateToLong("2019-4-14 22:15:58")));
        sourceBuilder.query(queryBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        for(SearchHit hit : searchHits){
            System.out.println(hit.getSourceAsString());
        }
        System.out.println(response);
    }

    private Long convertDateToLong(String date) throws ParseException {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return  Long.parseLong(String.valueOf(sdf.parse(date).getTime()));
    }
}
