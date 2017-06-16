package com.adnanbal.fxdedektifi.sample.presentation.util;

/**
 * Converter class, transforming {@link Double} value into {@link String}
 */
public class DoubleToString {


  public DoubleToString(double doubleValue) {

  }

  /**
   * Transform a {@link Double} into an {@link String}.
   *
   * @param doubleVal Object to be transformed.
   * @return {@link String}.
   */
  public static String convertFrom(double doubleVal) {
    String string = Double.toString(doubleVal);
    return string;
  }
}
