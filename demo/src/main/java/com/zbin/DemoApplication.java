package com.zbin;

import com.zbin.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RefreshScope
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
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
		HttpUtil.httpPostWithJSONString(url,null,3000);
		return "ok";
	}
}
