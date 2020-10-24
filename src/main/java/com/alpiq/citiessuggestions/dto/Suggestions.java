package com.alpiq.citiessuggestions.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public class Suggestions {

  private List<Suggestion> suggestions = new ArrayList<>();

  public Suggestions(Collection<SearchResult> searchResults) {
    searchResults.forEach(searchResult -> suggestions.add(new Suggestion(searchResult.getCityInformation().toString(), searchResult.getStringMatchPercent())));
    suggestions.sort(Comparator.comparingDouble(Suggestion::getScore).reversed());
  }

  public List<Suggestion> getSuggestions() {
    return suggestions;
  }

  public void setSuggestions(List<Suggestion> suggestions) {
    this.suggestions = suggestions;
  }
}
