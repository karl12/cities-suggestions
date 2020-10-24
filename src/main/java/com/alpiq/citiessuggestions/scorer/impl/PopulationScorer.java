package com.alpiq.citiessuggestions.scorer.impl;

import com.alpiq.citiessuggestions.dto.SearchResult;
import com.alpiq.citiessuggestions.scorer.Scorer;

import java.util.Collection;

public class PopulationScorer implements Scorer {

  @Override
  public void apply(Collection<SearchResult> results) {
    int highestValue = results.stream().map(searchResult -> searchResult.getCityInformation().getPopulation())
      .max(Integer::compareTo).orElse(0);

    results.forEach(searchResult ->
      searchResult.setStringMatchPercent(searchResult.getStringMatchPercent() * ((double) searchResult.getCityInformation().getPopulation() / highestValue)));
  }
}
