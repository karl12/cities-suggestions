package com.alpiq.citiessuggestions;

import com.alpiq.citiessuggestions.dto.CityInformation;
import com.alpiq.citiessuggestions.util.CitiesFileLoader;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParserTest {

  List<CityInformation> lines;

  @Before
  public void setUp() throws IOException {
    lines = CitiesFileLoader.loadFile("cities15000.txt");
  }

  @Test
  public void testRowCount() {
    assertEquals(24540, lines.size());
  }

  @Test
  public void firstCityLocalName() {
    assertEquals("les Escaldes", lines.get(0).getLocalName());
  }

  @Test
  public void oltenInSwitzerland() {
    assertEquals("Switzerland",
      lines.stream()
        .filter(cityLine -> cityLine.getEnglishName().equals("Olten"))
        .findFirst()
        .orElseThrow()
        .getCountry()
        .getDisplayCountry());
  }

  @Test
  public void zurichInZH() {
    assertEquals("ZH",
      lines.stream()
        .filter(cityLine -> cityLine.getEnglishName().equals("Zurich"))
        .findFirst()
        .orElseThrow()
        .getIsoSubdivision());
  }
}
