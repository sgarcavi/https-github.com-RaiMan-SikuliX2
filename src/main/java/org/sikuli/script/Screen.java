/*
 * Copyright (c) 2016 - sikulix.com - MIT license
 */

package org.sikuli.script;

import com.sikulix.core.SX;
import com.sikulix.core.SXLog;

import java.util.ArrayList;
import java.util.List;

public class Screen extends Region implements IScreen {
  private static SXLog log = SX.getLogger("SX.SCREEN");

  private int id = -1;
  private int curID = -1;

  public int getID() {
    return curID;
  }

  public Screen() {
    init(SX.getSXLOCALDEVICE().getMonitor());
    id = 0;
  }

  public Screen(int id) {
    init(SX.getSXLOCALDEVICE().getMonitor(id));
    this.id = id;
  }

  public String toString() {
    return String.format("Screen(%d,%d %dx%d #%d)", x, y, w, h, id);
  }

  public IScreen getScreen() {
    return this;
  }

  private static List<IScreen> screens = new ArrayList<>();

  public static IScreen getScreen(int num) {
    int numScreens = SX.getSXLOCALDEVICE().getNumberOfMonitors();
    if (screens.size() == 0) {
      for (int i = 0; i < numScreens; i++) {
        screens.add(new Screen(i));
      }
    }
    return (num >= 0 && num <= numScreens ? screens.get(num) : screens.get(0));
  }

  public String toStringPlus() {
    return " #" + id;
  }

  /**
   * show the current monitor setup
   */
  public static void showMonitors() {
    log.p("*** monitor configuration [ %s Screen(s)] ***", SX.getSXLOCALDEVICE().getNumberOfMonitors());
    log.p("*** Primary is Screen %d", SX.getSXLOCALDEVICE().getMainMonitorID());
    for (int i = 0; i < SX.getSXLOCALDEVICE().getNumberOfMonitors(); i++) {
      log.p("%d: %s", i, new Screen(i));
    }
    log.p("*** end monitor configuration ***");
  }

  /**
   * re-initialize the monitor setup (e.g. when it was changed while running)
   */
  public static void resetMonitors() {
    showMonitors();
    log.p("*** TRYING *** to reset the monitor configuration");
    SX.getSXLOCALDEVICE().resetMonitors();
    showMonitors();
  }

}
