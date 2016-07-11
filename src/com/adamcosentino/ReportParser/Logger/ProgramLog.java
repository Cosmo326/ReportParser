package com.adamcosentino.ReportParser.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Author: Adam Cosentino
 * Date: 7/7/2016 4:16 PM
 * Project Name: ReportParser
 * File Description:
 */
@SuppressWarnings("unused")
public final class ProgramLog {

  public static final int NONE = 0x00;
  public static final int INFO = 0x01;
  public static final int WARNING = 0x02;
  public static final int ERROR = 0x04;
  public static final int ALL = INFO | WARNING | ERROR;

  private static ProgramLog instance = null;
  private final StringBuilder log = new StringBuilder();
  private final SimpleDateFormat df = new SimpleDateFormat("[MM/dd/yyyy HH:mm:ss]: ");
  private final ArrayList<iLogDisplay> displays = new ArrayList<>();
  private int display = ALL;

  private ProgramLog() {}

  public static ProgramLog getInstance(){
    if(instance == null) instance = new ProgramLog();
    return instance;
  }

  public void setLogDisplay(int display) { this.display = display; }
  public void registerDisplay(iLogDisplay display){ if(display != null) displays.add(display); }
  public void unRegisterDisplay(iLogDisplay display) { if(display != null) displays.remove(display); }

  public void logInfo(String message){
    if(INFO == (display & INFO)) {
      log.append(df.format(new Date()))
          .append("INFO - ")
          .append(message)
          .append("\n");
      updateDisplays();
    }
  }

  public void logWarning(String message){
    if(WARNING == (display & WARNING)) {
      log.append(df.format(new Date()))
          .append("WARN - ")
          .append(message)
          .append("\n");
      updateDisplays();
    }
  }

  public void logError(String message){
    if(ERROR == (display & ERROR)) {
      log.append(df.format(new Date()))
          .append("ERROR - ")
          .append(message)
          .append("\n");
      updateDisplays();
    }
  }

  public void clearLog(){
    log.replace(0,log.length()-1,"");
    updateDisplays();
  }

  private void updateDisplays(){
    for(iLogDisplay display : displays){
      display.updateDisplay(log.toString());
    }
  }

  public String getLog(){ return log.toString(); }
}
