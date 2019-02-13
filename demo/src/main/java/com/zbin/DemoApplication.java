package com.zbin;

import com.zbin.util.HttpUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class
})
@EnableCaching
@EnableDiscoveryClient
@RestController
@RefreshScope
@MapperScan({"com.zbin.mapper","com.zbin.dao"})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public void run(String... strings) throws Exception {

		System.out.println("1234565");
	}

	@Value("${myfirst}")
	String foo;
	@RequestMapping(value = "/hi")
	public String hi(){
		return foo;
	}

	@RequestMapping(value = "/refreshConfig")
	public String hiConfig(){
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		String url = "";
		url = request.getScheme() +"://" + request.getServerName()
				+ ":" +request.getServerPort()
				+ "/actuator/refresh";
		HttpUtil.httpPostWithJSONString(url,null,10000);
		return "ok";
	}
}
