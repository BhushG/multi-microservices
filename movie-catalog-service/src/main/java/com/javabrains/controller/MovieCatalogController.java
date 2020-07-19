package com.javabrains.controller;

import com.javabrains.exception.ServiceDiscoveryException;
import com.javabrains.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RefreshScope
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Value("${service.ids.movie}")  //this property will be loaded from consul config
    private String movieServiceId;

    @Value("${service.ids.rating}") //this property will be loaded from consul config
    private String ratingServiceId;

    @Bean
    private RestTemplate getRestTemplate() {
        return  new RestTemplate();
    }


    @PostConstruct
    private void printServiceNames() {
        System.out.println("movieServiceId: " + movieServiceId);
        System.out.println("ratingServiceId: " + ratingServiceId);
    }


    private URI discoverService (String serviceId) throws ServiceDiscoveryException {
        Optional<ServiceInstance> service = discoveryClient.getInstances(serviceId).stream().findFirst();
        if(! service.isPresent())
            throw new ServiceDiscoveryException(serviceId);
        return service.get().getUri();
    }


    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) throws ServiceDiscoveryException {

        RatingInfo ratingInfo = restTemplate.getForObject(discoverService(ratingServiceId) + "/ratings/"+userId, RatingInfo.class);

        URI movieServiceURI = discoverService(movieServiceId);


        List<CatalogItem> catalogItems = ratingInfo.getRatings().stream().map(rating -> {
            String movieId = rating.getMovieId();
            Movie movie = restTemplate.getForObject(movieServiceURI+ "/movies/"+movieId, Movie.class);
            return  new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
        }).collect(Collectors.toList());
        return catalogItems;
    }
}
