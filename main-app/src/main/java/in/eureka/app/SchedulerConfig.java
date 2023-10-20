package in.eureka.app;


import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
//@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
@Order(2)
public class SchedulerConfig {

}
