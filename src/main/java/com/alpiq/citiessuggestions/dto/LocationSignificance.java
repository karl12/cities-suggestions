package com.alpiq.citiessuggestions.dto;

public enum LocationSignificance {
  PPL(0.5),
  PPLA(0.9),
  PPLA2(0.8),
  PPLA3(0.7),
  PPLA4(0.6),
  PPLA5(0.5),
  PPLC(1),
  PPLCH(0.9),
  PPLF(0.1),
  PPLG(0.9),
  PPLH(0.01),
  PPLL(0.1),
  PPLQ(0.01),
  PPLR(0.1),
  PPLS(0.1),
  PPLW(0.01),
  PPLX(0.01),
  STLMT(0.01);

  LocationSignificance(double weighting) {
    this.weighting = weighting;
  }

  private double weighting;

  public double getWeighting() {
    return weighting;
  }
}