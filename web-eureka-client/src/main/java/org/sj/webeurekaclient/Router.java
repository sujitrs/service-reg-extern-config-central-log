package org.sj.webeurekaclient;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/*@Bean
@LoadBalanced
public WebClient.Builder loadBalancedWebClientBuilder() {
    return WebClient.builder();
}*/


@RestController
public class Router {
	
	  @Autowired private DiscoveryClient discoveryClient;
	  
	  public URI serviceUrl(String serviceName) { List<ServiceInstance> list =
	  discoveryClient.getInstances(serviceName); if (list != null && list.size() >
	  0 ) { return list.get(0).getUri(); } return null; }
	  
	  @GetMapping("/") public Object call() { URI
	  uri=serviceUrl("ms-api-gateway"); 
	  RestTemplate restTemplate = new 	  RestTemplate(); 
	  Object result = restTemplate.getForObject( uri+"/user/getAllUsers", Object.class);
	  return result;
	  
	  }
	 
	
}
