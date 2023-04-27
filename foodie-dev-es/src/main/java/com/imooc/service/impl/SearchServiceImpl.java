package com.imooc.service.impl;

import com.google.common.collect.Lists;
import com.imooc.pojo.SearchItem;
import com.imooc.service.SearchService;
import com.imooc.utils.PagedGridResult;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author Mengdexin
 * @date 2022 -01 -08 -16:31
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private ElasticsearchTemplate es;

    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        String preTag = "<font color='red'>";
        String postTag = "</font>";
        Pageable pageable = PageRequest.of(page, pageSize);
//        SortBuilder sortBuilder = new FieldSortBuilder("money")
//                .order(SortOrder.DESC);
        String ItemsName = "itemName";
        SearchQuery query = new NativeSearchQueryBuilder()
                //查询
                .withQuery(QueryBuilders.matchQuery(ItemsName, keywords))
                //高亮
                .withHighlightFields(new HighlightBuilder.Field(ItemsName)
                        .preTags(preTag)
                        .postTags(postTag))
                //分页
                .withPageable(pageable)
                //排序
//                .withSort(sortBuilder)
                .build();
        AggregatedPage<SearchItem> esItems = es.queryForPage(query, SearchItem.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> aClass, Pageable pageable) {
                SearchHits hits = response.getHits();
                List<SearchItem> result = Lists.newArrayList();
                hits.forEach(v ->{
                    //高亮的数据是自己的查询的数据才可以。
                    HighlightField highlightField = v.getHighlightFields().get(ItemsName);
                    String itemName = highlightField.getFragments()[0].toString();
                    Map<String, Object> sourceAsMap = v.getSourceAsMap();
                    String itemId = (String) sourceAsMap.get("itemId");
                    String imgUrl = (String) sourceAsMap.get("imgUrl");
                    Integer price = (Integer) sourceAsMap.get("price");
                    Integer sellCounts = (Integer) sourceAsMap.get("sellCounts");
                    SearchItem searchItem = new SearchItem();
                    searchItem.setItemId(itemId);
                    searchItem.setImgUrl(imgUrl);
                    searchItem.setItemName(itemName);
                    searchItem.setPrice(price);
                    searchItem.setSellCounts(sellCounts);
                    result.add(searchItem);
                });
                return new AggregatedPageImpl<>((List<T>)result, pageable, response.getHits().totalHits);
            }
        });

        PagedGridResult pagedGridResult = new PagedGridResult();
        pagedGridResult.setPage(page + 1);
        pagedGridResult.setRows(esItems.getContent());
        pagedGridResult.setTotal(esItems.getTotalPages());
        pagedGridResult.setRecords(esItems.getTotalElements());
        return pagedGridResult;
    }

}
