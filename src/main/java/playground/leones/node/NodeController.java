package playground.leones.node;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class NodeController {

    @Value("${loadbalancer.url:http://localhost:8080/register}")
    private String loadBalancerUrl;

    @Value("${server.ip:127.0.0.1}")
    private String serverIp;

    @Value("${server.port:5000}")
    private String serverPort;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/process")
    public Map<String, String> processRequest() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Request processed by node");
        return response;
    }

    @Scheduled(fixedDelay = 5000)
    public void registerWithLoadBalancer() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Forwarded-For", serverIp);
            headers.set("X-Forwarded-Port", serverPort);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            restTemplate.exchange(loadBalancerUrl, HttpMethod.GET, entity, String.class);
            log.debug("Registered with load balancer");
        } catch (Exception e) {
            log.debug("Error connecting to load balancer: " + e.getMessage());
        }
    }
}