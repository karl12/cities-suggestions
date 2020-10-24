package com.alpiq.citiessuggestions.controller;

import com.alpiq.citiessuggestions.dto.Suggestions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Size;

@RequestMapping("suggestions")
@Validated
public interface SuggestionsController {

  @GetMapping
  Suggestions query(@RequestParam(value = "q")
                    @Size(min = 3, message = "Query contain must be at least 3 characters.") String query);
}
