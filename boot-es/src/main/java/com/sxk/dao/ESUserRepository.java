package com.sxk.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.sxk.model.ESUser;

public interface ESUserRepository extends ElasticsearchRepository<ESUser, String> {
}
