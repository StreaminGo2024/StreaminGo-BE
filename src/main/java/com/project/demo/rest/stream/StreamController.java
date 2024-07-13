package com.project.demo.rest.stream;


import com.project.demo.logic.entity.stream.StreamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("stream")
public class StreamController {

    @Autowired
    private StreamService streamService;

    @GetMapping(value = "{title}", produces = "video/mp4")
    public Mono<Resource> streamContent(@PathVariable String title){
        return streamService.retrieveContent(title);
    }
}
