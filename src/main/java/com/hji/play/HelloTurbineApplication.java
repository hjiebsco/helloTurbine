package com.hji.play;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;

import com.netflix.appinfo.AmazonInfo;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableTurbine
public class HelloTurbineApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloTurbineApplication.class, args);
	}

	@Value("${server.port}")
	private int port;

	@Bean
	public EurekaInstanceConfigBean eurekaInstanceConfig() {
		EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(new InetUtils(new InetUtilsProperties()));
		AmazonInfo info = AmazonInfo.Builder.newBuilder().autoBuild("turbine");
		config.setDataCenterInfo(info);
		info.getMetadata().put(AmazonInfo.MetaDataKey.publicHostname.getName(),
				info.get(AmazonInfo.MetaDataKey.publicIpv4));
		config.setHostname(info.get(AmazonInfo.MetaDataKey.publicHostname));
		config.setIpAddress(info.get(AmazonInfo.MetaDataKey.publicIpv4));
		config.setNonSecurePort(port);
		return config;
	}
}
