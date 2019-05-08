package xyz.somersames.script.date.search;


import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import xyz.somersames.util.EsUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author szh
 * @create 2019-05-08 22:45
 **/
public class ExpireDateSearch {


    /**
     * 未来三个月所有到期提醒
     */
    public void nextThreeMonth() throws IOException {
        RestHighLevelClient client = EsUtil.getInstance();
        SearchRequest searchRequest = new SearchRequest("date_test");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("script","(doc['c'].getDate().getMonthValue() ==5 && doc['c'].getDate().getDayOfMonth() >=9) ||(doc['c'].getDate().getMonthValue() ==8 && doc['c'].getDate().getDayOfMonth() <=9) || doc['c'].getDate().getMonthValue() ==6 || doc['c'].getDate().getMonthValue() ==7");
        Script inline = new Script(ScriptType.INLINE, "painless",
                "params.script", parameters);
        query.must(QueryBuilders.scriptQuery(inline));
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHit = response.getHits();
        SearchHit[] hits = searchHit == null? null: searchHit.getHits();
        if(hits != null){
            for(SearchHit hit: hits){
                System.out.println(hit.getSourceAsString());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ExpireDateSearch expireDateSearch =new ExpireDateSearch();
        expireDateSearch.nextThreeMonth();
    }
}
