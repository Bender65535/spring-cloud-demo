package com.demo.elasticsearch.test;

import com.demo.elasticsearch.entity.Item;
import com.demo.elasticsearch.repository.ItemRepository;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchTest {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testIndex(){
        elasticsearchTemplate.createIndex(Item.class);
        elasticsearchTemplate.putMapping(Item.class);
    }

    @Test
    public void testCreate(){
        Item item = new Item(1L,"小米手机","手机","小米",2999.9,"123.jpg");
        itemRepository.save(item);
    }

    @Test
    public void testFind(){
//        Optional<Item> item = itemRepository.findById(1L);
//        System.out.println(item.get());  //必须要有无参构造


        Iterable<Item> all = itemRepository.findAll(Sort.by("price").descending());
        all.forEach(System.out::println);
    }

    @Test
    public void testCustomized(){
        List<Item> items = itemRepository.findByTitle("小米");
        items.forEach(System.out::println);

    }

    @Test
    public void testSearch(){
        //通过查询构建工具构建查询条件
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", "手机");
        //执行查询,获取结果集
        Iterable<Item> items = itemRepository.search(queryBuilder);
        items.forEach(System.out::println);
    }

    @Test
    public void testNative(){
        //构建自定义查询构建器
        NativeSearchQueryBuilder nativeSearchQueryBuilder=new NativeSearchQueryBuilder();
        //添加基本的查询条件
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("title","手机"));
        //执行查询获取分页结果
        Page<Item> itemPage = itemRepository.search(nativeSearchQueryBuilder.build());
        System.out.println(itemPage.getTotalPages());
        System.out.println(itemPage.getTotalElements());
        itemPage.forEach(System.out::println);
    }

    @Test
    public void testAggs(){
        //初始化自定义查询构建器
        NativeSearchQueryBuilder queryBuilder=new NativeSearchQueryBuilder();
        //添加聚合
        queryBuilder.addAggregation(AggregationBuilders.terms("bandAgg").field("brand"));
        //添加结果集过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{},null));
        //执行聚合查询
        AggregatedPage<Item> itemPage =(AggregatedPage) itemRepository.search(queryBuilder.build());
        //解析聚合结果集，根据聚合的类型以及字段类型要进行强转,brand-是字符串类型,字条类型-词条聚合
        StringTerms bandAgg = (StringTerms)itemPage.getAggregation("bandAgg");
        //获取桶的集合
        List<StringTerms.Bucket> buckets = bandAgg.getBuckets();
        buckets.forEach(bucket -> {
            System.out.println(bucket.getKeyAsString());
            System.out.println(bucket.getDocCount());
            //获取子聚合的map集合,key-聚合名称,value-对应的子聚合对象
            Map<String, Aggregation> stringAggregationMap=bucket.getAggregations().asMap();
            InternalAvg price_avg=(InternalAvg) stringAggregationMap.get("price_avg");
            System.out.println(price_avg.getValue());

        });


    }
}
