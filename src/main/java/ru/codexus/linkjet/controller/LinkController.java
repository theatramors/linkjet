package ru.codexus.linkjet.controller;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.codexus.linkjet.configuration.properties.AppProperties;
import ru.codexus.linkjet.dto.Link;
import ru.codexus.linkjet.persistence.LinkRepository;
import ru.codexus.linkjet.utils.RandomStringGenerator;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class LinkController {

    private static final Integer MAX_ATTEMPTS = 5;

    private final AppProperties appProperties;
    private final LinkRepository linkRepository;

    public LinkController(AppProperties appProperties, LinkRepository linkRepository) {
        this.appProperties = appProperties;
        this.linkRepository = linkRepository;
    }

    @GetMapping(path = "/")
    public String home() {
        return "home";
    }

    @ResponseBody
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUrl(@PathVariable(name = "id", required = false) String linkId) {
        if (StringUtils.isEmpty(linkId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Link> linkOptional = linkRepository.findById(linkId);

        if (linkOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Link link = linkOptional.get();

        // if (linkOptional.getExpiresIn().isAfter(LocalDateTime.now())) {
        //     throw new IllegalStateException();
        // }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.LOCATION, link.getUrl());

        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }

    @ResponseBody
    @PostMapping(path = "/")
    public String shortenUrl(
        @RequestParam(name = "url")
        String url,

        @RequestParam(name = "expires_in", required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        LocalDateTime expiresIn
    ) {
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            String linkId = RandomStringGenerator.generate(appProperties.getLink().getLength());

            try {
                if (linkRepository.createLink(linkId, url, expiresIn) > 0) {
                    return linkId;
                }
            } catch (DuplicateKeyException exception) {
                System.out.println("Generated already existing short link");
            }
        }

        // FIXME: Add error handling
        throw new RuntimeException("Max attempts reached");
    }
}
