package io.javabrains.springbootquickstart.courseapi.controller;

import io.javabrains.springbootquickstart.courseapi.resource.TopicResource;
import io.javabrains.springbootquickstart.courseapi.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @RequestMapping("/topics")
    public HttpEntity<List<TopicResource>> getAllTopicResources() {
        List<TopicResource> allTopicResources = topicService.getAllTopicResources();
        allTopicResources.forEach(resource -> resource
                .add(linkTo(methodOn(TopicController.class)
                        .findTopicResource(resource.getId()))
                        .withSelfRel()));
        return new ResponseEntity<>(allTopicResources, HttpStatus.OK);
    }

    @RequestMapping("/topics/{topicId}")
    public HttpEntity<TopicResource> findTopicResource(@PathVariable String topicId) {
        return new ResponseEntity<>(topicService.findTopicResource(topicId)
                .map(resource -> resource
                        .add(linkTo(methodOn(TopicController.class)
                                .findTopicResource(resource.getId()))
                                .withSelfRel()))
                .orElse(null), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/topics")
    public void addTopicResource(@RequestBody TopicResource topicResource) {
        topicService.addTopicResource(topicResource);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/topics/{id}")
    public void updateTopicResource(@PathVariable String topicId,
                                    @RequestBody TopicResource topicResource) {
        topicResource.setId(topicId);
        topicService.updateTopicResource(topicResource);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/topics/{id}")
    public void deleteTopicById(@PathVariable String id) {
        topicService.deleteTopicById(id);
    }
}
