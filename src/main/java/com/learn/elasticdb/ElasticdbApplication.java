package com.learn.elasticdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
public class ElasticdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElasticdbApplication.class, args);
	}

}
