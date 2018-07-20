package io.pivotal.pal.tracker.timesheets;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestOperations;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

public class ProjectClient {
	private final Logger log = LoggerFactory.getLogger(ProjectClient.class);
    private final RestOperations restOperations;
    private final String endpoint;
    private final ConcurrentMap<Long, ProjectInfo> cache;
    
    public ProjectClient(RestOperations restOperations, String registrationServerEndpoint) {
        this.restOperations = restOperations;
        this.endpoint = registrationServerEndpoint;
        cache = new ConcurrentHashMap<>();
    }

    @HystrixCommand(fallbackMethod = "getProjectFromCache")
    public ProjectInfo getProject(long projectId) {
    	ProjectInfo info = restOperations.getForObject(endpoint + "/projects/" + projectId, ProjectInfo.class);
        cache.put(projectId, info);
        log.info("Obtained and cached projectInfo for projectId '{}'", projectId);
        return info;
    }
    
    public ProjectInfo getProjectFromCache(long projectId){
    	log.info("Fell back to using cached project lookup for projectId '{}'", projectId);
        return cache.get(projectId);
    }
}
