package com.xu.myWechat;

import com.xu.myWechat.pojo.dao.Visitor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VisitorRepository extends MongoRepository<Visitor,String> {

}