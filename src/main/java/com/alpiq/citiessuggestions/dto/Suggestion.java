package com.alpiq.citiessuggestions.dto;

public class Suggestion {

  private String name;
  private double score;

  public Suggestion() {
  }

  public Suggestion(String name, double score) {
    this.name = name;
    this.score = score;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }
}
