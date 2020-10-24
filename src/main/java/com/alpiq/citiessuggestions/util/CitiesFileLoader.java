package com.alpiq.citiessuggestions.util;

import com.alpiq.citiessuggestions.dto.CityInformation;
import com.alpiq.citiessuggestions.dto.LocationSignificance;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;

public class CitiesFileLoader {

  public static List<CityInformation> loadFile(String location) throws IOException {
    InputStream inputStream = CitiesFileLoader.class.getClassLoader().getResourceAsStream(location);
    Reader in = new InputStreamReader(Objects.requireNonNull(inputStream));
    Iterable<CSVRecord> records = CSVFormat.newFormat('\t').parse(in);
    List<CityInformation> ret = new ArrayList<>();
    records.forEach(record -> {
      CityInformation cityLine = new CityInformation();
      cityLine.setId(Integer.parseInt(record.get(0)));
      cityLine.setLocalName(record.get(1));
      cityLine.setEnglishName(record.get(2));
      cityLine.setAlternativeNames(List.of(record.get(3).split(",")));
      cityLine.setLatitude(Double.parseDouble(record.get(4)));
      cityLine.setLongitude(Double.parseDouble(record.get(5)));
      cityLine.setP(record.get(6));
      cityLine.setLocationSignificance(LocationSignificance.valueOf(record.get(7)));
      cityLine.setCountry(new Locale("", record.get(8)));
      cityLine.setIsoSubdivision(record.get(10));
      cityLine.setPopulation(Integer.parseInt(record.get(14)));
      ret.add(cityLine);
    });
    return Collections.unmodifiableList(ret);
  }
}
