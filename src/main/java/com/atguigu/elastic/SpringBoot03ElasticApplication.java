package com.atguigu.elastic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * springboot默认使用两种技术来和ES（Elasticsearch）进行交互
 * 1.Jest(默认不生效的)
 *      需要导入工具包：import io.searchbox.client.JestClient;
 * 2.SpringData  Elasticsearch (es版本有可能不合适)、
 *
 *       如果版本不合适
 *       1.升级springboot版本
 *       2.安装对应的ES版本  手动引入2.4.6版本 记得登录的端口号相应发生改变
 *
 *      1）.Client 节点信息clusterNodes；clusterName
 *      2）.ElasticsearchTemplate 操作es
 *      3）.编写一个ElasticsearchRepository的子接口来操作es
 *
 */
@SpringBootApplication
public class SpringBoot03ElasticApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot03ElasticApplication.class, args);
    }

}
