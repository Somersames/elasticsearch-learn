package xyz.somersames.update.multi_thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import xyz.somersames.util.EsConstant;
import xyz.somersames.util.EsUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MultiThreadUpdate {
    public static void main(String[] args) throws IOException {
        MultiThreadUpdate multiThreadUpdate = new MultiThreadUpdate();
        ExecutorService executorService =new ThreadPoolExecutor(6,10,0L, TimeUnit.MICROSECONDS,new LinkedBlockingQueue<>());
        for(int i =0 ;i< 1000;i++){
            int finalI = i;
            executorService.execute(() -> {
                try {
                    multiThreadUpdate.update(String.valueOf(finalI));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    private void update(String str) throws IOException {
        RestHighLevelClient client = EsUtil.getInstance();
        UpdateRequest updateRequest =new UpdateRequest(EsConstant.ES_INSERT_INDEX,EsConstant.ES_TEST_TYPE,"1");
        updateRequest.versionType(VersionType.EXTERNAL).version(Integer.MAX_VALUE);
        Map<String,String> map = new HashMap<>();
        map.put("message",str);
        updateRequest.doc(JSON.toJSONString(map, SerializerFeature.DisableCircularReferenceDetect),XContentType.JSON);
        client.update(updateRequest, RequestOptions.DEFAULT);
    }
}
