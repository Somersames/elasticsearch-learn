package xyz.somersames;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;

/**
 * @author szh
 * @create 2019-04-10 23:09
 **/
@SpringBootApplication
@EnableAutoConfiguration(exclude = ElasticsearchAutoConfiguration.class)
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class);
    }
}
