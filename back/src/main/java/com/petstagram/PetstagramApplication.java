package com.petstagram;

import com.petstagram.service.TestService;
import com.petstagram.test.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@SpringBootApplication
public class PetstagramApplication {

	@Autowired
	private MongoTemplate mongoTemplate;

	private TestService testService;

	public static void main(String[] args) {
		SpringApplication.run(PetstagramApplication.class, args);
	}

//	1. 왜 service로 불러오면 에러가 생기는가?
	@Bean
	public ApplicationRunner applicationRunner(){
		return args -> {
			try {
//				mongoTemplate.insert(new Test("", "test"));
				List<Test> test = mongoTemplate.findAll(Test.class);
				for (Test t:test){
					String name = t.getUserName();
					System.out.println(name);
				}
//                testService.update("test", "new22");
//                testService.removeByname("test2345");
//                testService.removeall();
//                Test test = new Test("1", "test");
//                testService.insert(test);
			} catch (Exception e){
				e.printStackTrace();
			}
		};
	}

}
