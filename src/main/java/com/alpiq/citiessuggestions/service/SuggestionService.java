package com.alpiq.citiessuggestions.service;

import com.alpiq.citiessuggestions.dao.CitiesDao;
import com.alpiq.citiessuggestions.dto.SearchResult;
import com.alpiq.citiessuggestions.dto.Suggestions;
import com.alpiq.citiessuggestions.scorer.Scorer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SuggestionService {
  private static final Logger log = LoggerFactory.getLogger(SuggestionService.class);

  private final CitiesDao citiesDao;
  private final Collection<Scorer> scorers;

  public SuggestionService(CitiesDao citiesDao, Collection<Scorer> scorers) {
    this.citiesDao = citiesDao;
    this.scorers = scorers;
  }

  public Suggestions query(String query) {
    log.info("Starting search for {}", query);
    long startTime = System.currentTimeMillis();
    Collection<SearchResult> results = citiesDao.getMatchesFast(query);
    scorers.forEach(scorer -> scorer.apply(results));
    log.info("Search for {} took {}ms", query, System.currentTimeMillis() - startTime);
    return new Suggestions(results);
  }
}
