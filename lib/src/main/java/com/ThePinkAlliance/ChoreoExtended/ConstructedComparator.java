package com.ThePinkAlliance.ChoreoExtended;

import java.util.Comparator;

import com.ThePinkAlliance.ChoreoExtended.actions.ConstructedAction;

public class ConstructedComparator implements Comparator<ConstructedAction> {

  @Override
  public int compare(ConstructedAction arg0, ConstructedAction arg1) {
    return arg0.getTimestamp() > arg1.getTimestamp() ? 1 : 0;
  }

}
