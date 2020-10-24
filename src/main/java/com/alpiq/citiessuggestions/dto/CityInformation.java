package com.alpiq.citiessuggestions.dto;

import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

public class CityInformation {
  private int id;
  private String localName;
  private String englishName;
  private List<String> alternativeNames;
  private double latitude;
  private double longitude;
  private String p;
  private LocationSignificance locationSignificance;
  private Locale country;
  private String isoSubdivision;
  private int population;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getLocalName() {
    return localName;
  }

  public void setLocalName(String localName) {
    this.localName = localName;
  }

  public String getEnglishName() {
    return englishName;
  }

  public void setEnglishName(String englishName) {
    this.englishName = englishName;
  }

  public List<String> getAlternativeNames() {
    return alternativeNames;
  }

  public void setAlternativeNames(List<String> alternativeNames) {
    this.alternativeNames = alternativeNames;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }


  public String getP() {
    return p;
  }

  public void setP(String p) {
    this.p = p;
  }

  public Locale getCountry() {
    return country;
  }

  public void setCountry(Locale country) {
    this.country = country;
  }

  public String getIsoSubdivision() {
    return isoSubdivision;
  }

  public void setIsoSubdivision(String isoSubdivision) {
    this.isoSubdivision = isoSubdivision;
  }

  public LocationSignificance getLocationSignificance() {
    return locationSignificance;
  }

  public void setLocationSignificance(LocationSignificance locationSignificance) {
    this.locationSignificance = locationSignificance;
  }

  public int getPopulation() {
    return population;
  }

  public void setPopulation(int population) {
    this.population = population;
  }

  public String toString(){
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(localName);
    stringBuilder.append(", ");
    if(!getIsoSubdivision().isBlank()) {
      stringBuilder.append(getIsoSubdivision());
      stringBuilder.append(", ");
    }
    try{
      stringBuilder.append(getCountry().getISO3Country());
    } catch (MissingResourceException e){
      stringBuilder.append(getCountry().getDisplayCountry());
    }
    return stringBuilder.toString();
  }
}
