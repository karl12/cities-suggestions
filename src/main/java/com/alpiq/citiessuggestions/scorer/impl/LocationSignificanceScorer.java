package com.alpiq.citiessuggestions.scorer.impl;

import com.alpiq.citiessuggestions.dto.SearchResult;
import com.alpiq.citiessuggestions.scorer.Scorer;

import java.util.Collection;

public class LocationSignificanceScorer implements Scorer {
  @Override
  public void apply(Collection<SearchResult> results) {
    double highestValue = results.stream().map(searchResult -> searchResult.getCityInformation().getLocationSignificance().getWeighting())
      .max(Double::compareTo).orElse(0.);

    results.forEach(searchResult ->
      searchResult.setStringMatchPercent(searchResult.getStringMatchPercent() * (searchResult.getCityInformation().getLocationSignificance().getWeighting() / highestValue)));
  }
}
