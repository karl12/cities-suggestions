package com.alpiq.citiessuggestions.scorer;

import com.alpiq.citiessuggestions.dto.SearchResult;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public interface Scorer {

  void apply(Collection<SearchResult> results);
}
