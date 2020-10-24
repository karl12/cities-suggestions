package com.alpiq.citiessuggestions.controller.impl;

import com.alpiq.citiessuggestions.controller.SuggestionsController;
import com.alpiq.citiessuggestions.dto.Suggestions;
import com.alpiq.citiessuggestions.service.SuggestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;

@RestController
@RequestMapping("suggestions")
public class SuggestionsControllerImpl implements SuggestionsController {

  private static final Logger log = LoggerFactory.getLogger(SuggestionsController.class);

  private final SuggestionService suggestionService;

  public SuggestionsControllerImpl(SuggestionService suggestionService) {
    this.suggestionService = suggestionService;
  }

  @Override
  public Suggestions query(String query) {
    log.info("Received query request: {}", query);
    return suggestionService.query(query);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

}
