package com.alpiq.citiessuggestions;

import com.alpiq.citiessuggestions.dao.CitiesDao;
import com.alpiq.citiessuggestions.dto.Suggestions;
import com.alpiq.citiessuggestions.scorer.impl.LocationSignificanceScorer;
import com.alpiq.citiessuggestions.service.SuggestionService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class SuggestionTest {

  private static SuggestionService suggestionService;

  @BeforeClass
  public static void setUp() throws IOException {
    CitiesDao citiesDao = new CitiesDao();
    citiesDao.init();
    suggestionService = new SuggestionService(citiesDao, List.of(new LocationSignificanceScorer(), new LocationSignificanceScorer()));
  }

  @Test
  public void zurichNumberOne() {
    Suggestions suggestions = suggestionService.query("Zurich");
    Assert.assertEquals("Zürich, ZH, CHE", suggestions.getSuggestions().get(0).getName());
    Assert.assertEquals(1, suggestions.getSuggestions().get(0).getScore(), 0);
  }


  @Test
  public void londonUKNumberOne() {
    Suggestions suggestions = suggestionService.query("London");
    Assert.assertEquals("London, ENG, GBR", suggestions.getSuggestions().get(0).getName());
    Assert.assertEquals(1, suggestions.getSuggestions().get(0).getScore(), 0);
  }

  @Test
  public void lonSearch() {
    Suggestions suggestions = suggestionService.query("Lond");
    Assert.assertEquals("London, ENG, GBR", suggestions.getSuggestions().get(0).getName());
    Assert.assertEquals(0.933, suggestions.getSuggestions().get(0).getScore(), 0.03);
  }

  @Test
  public void londonOnlyOnce() {
    Suggestions suggestions = suggestionService.query("Lond");
    Assert.assertEquals(1, suggestions.getSuggestions().stream().filter(suggestion -> suggestion.getName().equals("London, ENG, GBR")).count());
  }

  @Test
  public void empty() {
    Suggestions suggestions = suggestionService.query("SomeRandomCityThatDoesntExist");
    Assert.assertEquals(0, suggestions.getSuggestions().size());
  }
}
