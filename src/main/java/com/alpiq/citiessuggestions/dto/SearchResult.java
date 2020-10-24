package com.alpiq.citiessuggestions.dto;

public class SearchResult {

  private CityInformation cityInformation;
  private double stringMatchPercent;

  public SearchResult(CityInformation cityInformation, double stringMatchPercent) {
    this.cityInformation = cityInformation;
    this.stringMatchPercent = stringMatchPercent;
  }

  public CityInformation getCityInformation() {
    return cityInformation;
  }

  public void setCityInformation(CityInformation cityInformation) {
    this.cityInformation = cityInformation;
  }

  public double getStringMatchPercent() {
    return stringMatchPercent;
  }

  public void setStringMatchPercent(double stringMatchPercent) {
    this.stringMatchPercent = stringMatchPercent;
  }
}
