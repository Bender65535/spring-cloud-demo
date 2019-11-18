package com.demo.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "item", type = "docs", shards = 1, replicas = 0)  //映射到索引库的名称
@AllArgsConstructor
@NoArgsConstructor
@ToString
//type为表名,shards为分片,replicas为副本
public class Item {
    @Id
    Long id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    String title;
    @Field(type = FieldType.Keyword)
    String category;
    @Field(type = FieldType.Keyword)
    String brand;
    @Field(type = FieldType.Double)
    Double price;
    @Field(type = FieldType.Keyword,index=false)
    String images;
}
