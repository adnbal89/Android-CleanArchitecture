package com.adnanbal.fxdedektifi.sample.presentation.util;

import android.support.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Signal status descriptor class(enum) for using signal's current status.
 */
public class SignalStatusDescriptor {

  public static final String WORKING = "working";
  public static final String CLOSED = "closed";
  public static final String SL_MODIFIED = "sl_modified";
  public static final String TP_MODIFIED = "tp_modified";
  public static final String SL_HIT = "sl_hit";
  public static final String TP_HIT = "tp_hit";

  public final String signalStatus;


  // Describes when the annotation will be discarded
  @Retention(RetentionPolicy.SOURCE)
  // Enumerate valid values for this interface
  @StringDef({WORKING, CLOSED, SL_MODIFIED, TP_MODIFIED, SL_HIT, TP_HIT})
  // Create an interface for validating int types
  public @interface SignalStatusDef {

  }

  // Mark the argument as restricted to these enumerated types
  public SignalStatusDescriptor(@SignalStatusDef String signalStatus) {
    this.signalStatus = signalStatus;
  }
}
