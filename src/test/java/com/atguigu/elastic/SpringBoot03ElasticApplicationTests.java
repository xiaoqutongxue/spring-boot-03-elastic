package com.atguigu.elastic;


import com.atguigu.elastic.bean.Article;
import com.atguigu.elastic.bean.Book;
import com.atguigu.elastic.repository.BookRepository;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot03ElasticApplicationTests {

    @Autowired
    JestClient jestClient;

    @Autowired
    BookRepository bookRepository;

    @Test
    public void test02() {
//        Book book = new Book();
//        book.setId(2);
//        book.setBookName("西游记02");
//        book.setAuthor("吴承恩02");
//        bookRepository.index(book);
        // https://docs.spring.io/spring-data/elasticsearch/docs/3.0.6.RELEASE/reference/html/
        // 命名规则参照官方文档
       for(Book book : bookRepository.findByBookNameLike("02")){
           System.out.println(book);
       }
    }

    @Test
    public void contextLoads() {
        // 1.给es中保存索引（文档）
        Article article = new Article();
        article.setId(1);
        article.setTitle("好消息");
        article.setAuthor("张三");
        article.setContent("HelloWorld");


        // Builder()表示的是保存什么
        // index()表示索引位置
        // 构建一个索引功能   new Index.Builder(article).index("atguigu").type("news").build();
        // http://192.168.10.212:9200/atguigu/news/1
        Index index = new Index.Builder(article).index("atguigu").type("news").build();

        // 执行
        try {
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 测试搜索
    // content表示用内容来搜索
    @Test
    public void search(){

        // 查询表达式
        String json = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"content\" : \"HelloWorld\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        // 构建搜索功能
        // {"took":20,"timed_out":false,"_shards":{"total":5,"successful":5,"skipped":0,"failed":0},"hits":{"total":1,"max_score":0.2876821,"hits":
        // [{"_index":"atguigu","_type":"news","_id":"1","_score":0.2876821,"_source":
        // {"id":1,"author":"张三","title":"好消息","content":"HelloWorld"}}]}}

        Search search = new Search.Builder(json).addIndex("atguigu").addType("news").build();
        // 执行
        try {
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
