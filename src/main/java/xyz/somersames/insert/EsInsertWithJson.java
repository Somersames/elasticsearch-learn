package xyz.somersames.insert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import xyz.somersames.model.EsTestDto;
import xyz.somersames.util.EsConstant;
import xyz.somersames.util.EsUtil;

import java.io.IOException;
import java.util.UUID;

/**
 * @author szh
 * @create 2019-05-06 22:21
 **/
@Slf4j
public class EsInsertWithJson {
    public static void main(String[] args) throws IOException {
        EsTestDto esTestDto =new EsTestDto();
        esTestDto.setC(123);
        esTestDto.setB("B");
        esTestDto.setA("A");
        esTestDto.setId("2");
        EsInsertWithJson esInsertWithJson =new EsInsertWithJson();
//        esInsertWithJson.insertWithObject(esTestDto);
        esInsertWithJson.asyncInsertWithObject(esTestDto);
    }


    public Boolean insertWithObject(EsTestDto obj) throws IOException {
        if(obj == null){
            return false;
        }
        RestHighLevelClient client = EsUtil.getInstance();
        String id =null;
        if(StringUtils.isBlank(obj.getId())){
            id= UUID.randomUUID().toString().replace("-", "").toLowerCase();
        }else{
            id = obj.getId();
        }
        IndexRequest request = new IndexRequest(EsConstant.ES_INSERT_INDEX,EsConstant.ES_INSERT_TYPE,id);
        request.source(JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect),XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        if(response.status() != null && response.status().getStatus() == 201){
            return true;
        }
        return false;
    }


    public void asyncInsertWithObject(EsTestDto obj) throws IOException {
        if(obj == null){
            return ;
        }
        RestHighLevelClient client = EsUtil.getInstance();
        String id =null;
        if(StringUtils.isBlank(obj.getId())){
            id= UUID.randomUUID().toString().replace("-", "").toLowerCase();
        }else{
            id = obj.getId();
        }
        IndexRequest request = new IndexRequest(EsConstant.ES_INSERT_INDEX,EsConstant.ES_INSERT_TYPE,id);
        request.source(JSON.toJSONString(obj,SerializerFeature.DisableCircularReferenceDetect),XContentType.JSON);
        client.indexAsync(request,RequestOptions.DEFAULT,getListener());
    }

    private ActionListener getListener() {
        ActionListener listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                if (indexResponse.status() != null && indexResponse.status().getStatus() == 201) {
                    log.info("新增成功");
                }
            }

            @Override
            public void onFailure(Exception e) {
                log.info("新增失败,{},{}", e.getMessage(), e);
            }
        };
        return listener;
    }
}
