package com.tudog.graphqldemo01;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import com.tudog.graphqldemo01.config.GraphQLAPIPreprocessor;
import com.tudog.graphqldemo01.entity.User;
import com.tudog.graphqldemo01.service.UserService;
import com.tudog.graphqldemo01.tools.EncryptUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class)
@EnableTransactionManagement
public class Application {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Application.class);
		springApplication.addListeners(new GraphQLAPIPreprocessor());
		springApplication.run(args);
	}

	@Bean
    CommandLineRunner initDataGenerate(){
	    return args -> {
            if(userService.count() > 0){
                return;
            }
			User user1 = new User("eric",EncryptUtil.encryptPassword("123456"),"Peter");
			User user2 = new User("zhangsan",EncryptUtil.encryptPassword("123456"),"Gary");
			userService.save(user1);
			userService.save(user2);
	    };
    }

}
