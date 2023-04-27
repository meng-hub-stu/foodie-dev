package com.imooc;

import com.imooc.pojo.Student;
import com.imooc.utils.JsonUtils;
import org.assertj.core.util.Lists;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Mengdexin
 * @date 2021 -12 -31 -21:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EsApplication.class)
public class TestElasticSearch {

    @Autowired
    private ElasticsearchTemplate es;

    /**
     * 创建索引
     * 创建索引时候，会把数据插入进去
     */
    @Test
    public void createIndex(){
        Student stu = new Student();
        stu.setAge(18);
        stu.setName("小心");
        stu.setSignDate("2022-01-02");
//        stu.setDescription("明天要上班");
//        stu.setDescription("我想明天去上班了");
//        stu.setDescription("你昨天已经在上班的路上了");
//        stu.setDescription("不想干什么");
        stu.setDescription("没有什么工作是上班不能解决的");
        stu.setStuId(1006L);
        IndexQuery query = new IndexQueryBuilder().withObject(stu).build();
        //进行添加数据
        es.index(query);
    }

    /**
     * 删除索引
     */
    @Test
    public void deleteIndex(){
        es.deleteIndex(Student.class);
    }




    /**
     * 更新doc的数据
     */
    @Test
    public void updateIndexDoc(){
        Map<String, Object> map = new HashMap<>();
        map.put("stuId", "1001");
        map.put("name", "小狗");
        map.put("age", 54);
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.source(map);
        UpdateQuery query = new UpdateQueryBuilder()
                .withClass(Student.class)
                .withId("1001")
                .withIndexRequest(indexRequest)
                .build();
        es.update(query);
    }

    /**
     * 查询索引中doc的数据
     */
    @Test
    public void queryIndexDoc(){
        GetQuery query = new GetQuery();
        query.setId("1001");
        Student stu = es.queryForObject(query, Student.class);
        System.out.println(stu);
    }

    /**
     * 删除索引中doc的数据
     */
    @Test
    public void deleteIndexDoc(){
        es.delete(Student.class, "1001");
    }

    /**
     * 创建索引
     */
    @Test
    public void createIndexDoc(){
        es.createIndex(Student.class);
    }


//    ------------------分割线--------------------

    /**
     * 分页查询
     */
    @Test
    public void queryPageList(){
        Pageable pageable = PageRequest.of(0, 10);
        SearchQuery query = new NativeSearchQueryBuilder()
                    .withQuery(QueryBuilders.matchQuery("name", "小"))
                    .withPageable(pageable)
                    .build();
        AggregatedPage<Student> result = es.queryForPage(query, Student.class);
        List<Student> stu = result.getContent();
        stu.forEach( v ->{
            System.out.println(v);
        });
    }


    @Test
    public void queryPageListForHighLight(){
        String preTag = "<font color='red'>";
        String postTag = "</font>";
        Pageable pageable = PageRequest.of(0, 10);
        SortBuilder sortBuilder = new FieldSortBuilder("money")
                .order(SortOrder.DESC);
        SearchQuery query = new NativeSearchQueryBuilder()
                //查询
                .withQuery(QueryBuilders.matchQuery("description", "上班"))
                //高亮
                .withHighlightFields(new HighlightBuilder.Field("description")
                        .preTags(preTag)
                        .postTags(postTag))
                //分页
                .withPageable(pageable)
                //排序
                .withSort(sortBuilder)
                .build();
        AggregatedPage<Student> result = es.queryForPage(query, Student.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                SearchHits hits = response.getHits();
                List<Student> result = Lists.newArrayList();
                hits.forEach(v ->{
                    //高亮的数据是自己的查询的数据才可以。
                    HighlightField highlightField = v.getHighlightFields().get("description");
                    String description = highlightField.getFragments()[0].toString();
                    Map<String, Object> sourceAsMap = v.getSourceAsMap();
                    sourceAsMap.put("description", description);
                    String json = JsonUtils.objectToJson(sourceAsMap);
                    Student student = JsonUtils.jsonToPojo(json, Student.class);
                    result.add(student);
                });
                if(result.size() > 0){
                    return new AggregatedPageImpl<>((List<T>)result);
                }
                return null;
            }
        });
        result.getContent().forEach( v ->{
            System.out.println(v);
        });
    }





}
