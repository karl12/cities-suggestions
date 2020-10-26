package com.alpiq.citiessuggestions.dao;

import com.alpiq.citiessuggestions.dto.CityInformation;
import com.alpiq.citiessuggestions.dto.SearchResult;
import com.alpiq.citiessuggestions.util.CitiesFileLoader;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CitiesDao {
  private final JaroWinklerDistance jaroWinklerDistance = new JaroWinklerDistance();

  // Fast to create and update, but slow to search partial results
  private final Map<String, Set<CityInformation>> nameMap = new HashMap<>();

  // Incredibly slow to initialise or update, but very fast searching.
  private final Map<String, Set<SearchResult>> largeIndexMap = new HashMap<>();

  //@Value("${app.minimumPopulation:100000}")
  private int minimumPopulation = 100000;

  //@Value("${app.minimumLocationWeight:0.7}")
  private double minimumLocationWeight = 0.7;


  @PostConstruct
  public void init() throws IOException {
    List<CityInformation> cityLines = CitiesFileLoader.loadFile("cities15000.txt");
    cityLines.stream()
      .filter(cityInformation -> cityInformation.getLocationSignificance().getWeighting() > minimumLocationWeight)
      .filter(cityInformation -> cityInformation.getPopulation() > minimumPopulation)
      .forEach(cityLine -> {
        nameMap.putIfAbsent(cityLine.getEnglishName(), new HashSet<>());
        nameMap.get(cityLine.getEnglishName()).add(cityLine);


        nameMap.putIfAbsent(cityLine.getLocalName(), new HashSet<>());
        nameMap.get(cityLine.getLocalName()).add(cityLine);


        cityLine.getAlternativeNames().forEach(name -> {
          nameMap.putIfAbsent(name, new HashSet<>());
          nameMap.get(name).add(cityLine);
        });
      });

    nameMap.forEach((key, value) -> {
      for (int i = 0; i < key.length(); i++) {
        for (int j = i + 3; j <= key.length(); j++) {
          String partial = key.substring(i, j);
          largeIndexMap.putIfAbsent(partial, new HashSet<>());
          value
            .forEach(cityInformation -> {
              largeIndexMap.get(partial).add(new SearchResult(cityInformation, jaroWinklerDistance.apply(partial, key)));
              if(!partial.toLowerCase().equals(partial)) largeIndexMap.get(partial).add(new SearchResult(cityInformation, jaroWinklerDistance.apply(partial.toLowerCase(), key)));
            });
        }
      }
    });
  }

  public Collection<SearchResult> getMatchesFast(String query) {
    Map<CityInformation, SearchResult> highestScores = new HashMap<>();
    Optional.ofNullable(largeIndexMap.get(query)).orElse(new HashSet<>())
      .forEach(entry -> {
        if (!highestScores.containsKey(entry.getCityInformation()) || highestScores.get(entry.getCityInformation()).getStringMatchPercent() < entry.getStringMatchPercent()) {
          highestScores.put(entry.getCityInformation(), entry);
        }
      });
    return highestScores.values();
  }

  public Collection<SearchResult> getMatches(String query) {
    Map<CityInformation, Double> highestScores = new HashMap<>();
    nameMap.entrySet().stream()
      .filter(entry -> entry.getKey().toLowerCase().contains(query.toLowerCase()))
      .map(stringSetEntry -> stringSetEntry.getValue()
        .stream()
        .map(cityInformation -> Map.entry(cityInformation, jaroWinklerDistance.apply(query, stringSetEntry.getKey())))
        .collect(Collectors.toList())
      ).flatMap(Collection::stream)
      .forEach(entry -> {
        if (!highestScores.containsKey(entry.getKey()) || highestScores.get(entry.getKey()) < entry.getValue())
          highestScores.put(entry.getKey(), entry.getValue());
      });

    return highestScores.entrySet().stream().map(entry -> new SearchResult(entry.getKey(), entry.getValue())).collect(Collectors.toList());
  }
}
