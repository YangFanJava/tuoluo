package io.renren.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA
 * Created By YangF
 * Date: 2017/7/21
 * Time: 15:17
 */
@Configuration
@PropertySource("classpath:dubbo/dubbo.properties")
@ImportResource({ "classpath:dubbo/*.xml" })
public class DubboConfig {
}