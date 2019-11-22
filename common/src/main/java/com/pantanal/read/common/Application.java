package com.pantanal.read.common;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Controller 定义控制层Bean,如Action
 * @Service 定义业务层Bean
 * @Repository 定义DAO层Bean
 * @Component 定义Bean, 不好归类时使用（不推荐使用）
 * @Transactional,注解在public方法上，可以对方法的runtimeException 做事务回滚
 * @Scheduled(cron="0 0/1 * * * *"),注解在public方法上,就可以实现spring自带的schedule
 *
 *
 *                    Spring还提供了更加细化的注解形式：@Repository、@Service、@Controller，它们分别对应存储层Bean，业务层
 *                    Bean，和展示层Bean。目前版本（2.5）中，这些注解与@Component的语义是一样的，完全通用，在Spring以后的版本中可能会给它们追加更多的语义。所以，推荐使用@Repository、@Service、@Controller来替代@Component。
 *
 */
// 禁用mongoDB自动配置,避免每次启动自动连接mongodb而报错
@SpringBootApplication(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class },scanBasePackages = {"com.pantanal.read"})
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}