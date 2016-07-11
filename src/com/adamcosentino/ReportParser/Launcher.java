package com.adamcosentino.ReportParser;

import com.adamcosentino.ReportParser.Logger.ProgramLog;

/**
 * Author: Adam Cosentino
 * Date: 7/8/2016 3:20 PM
 * Project Name: ReportParser
 * File Description:
 */
public class Launcher {
  public static void main(String[] args){
    ProgramLog log = ProgramLog.getInstance();
    boolean gui = true;

    for(int i = 0; i < args.length; i++){
      switch(args[i].toLowerCase()){
        case "/help":
        case "/?":
          System.out.println("REPORT PARSER HELP");
          System.out.println();
          System.out.println(" /HELP /?\tdisplays this help menu");
          System.out.println(" /LOG /L <NONE,INFO,WARN,ALL>\tSets Logging to NONE, INFO, WARN, ALL respectively");
          System.out.println(" /NOGUI <REPORT TYPE> <OUTPUT STYLE>\tWill allow for command line running");
          break;
        case "/log":
        case "/l":
          i++;
          if(args[i].toLowerCase().equals("none")) log.setLogDisplay(ProgramLog.NONE);
          else if(args[i].toLowerCase().equals("info")) log.setLogDisplay(ProgramLog.INFO);
          else if(args[i].toLowerCase().equals("warn")) log.setLogDisplay(ProgramLog.WARNING);
          else if(args[i].toLowerCase().equals("all")) log.setLogDisplay(ProgramLog.ALL);
          else System.out.println("Incorrect value given");
          break;
        case "/nogui":
          if(args.length > i + 2) {
            gui = false;
            Utilities.REPORT_TYPE type = null;
            i++;
            for (Utilities.REPORT_TYPE t : Utilities.REPORT_TYPE.values()) {
              if (t.cmdHook.equals(args[i].toLowerCase())) type = t;
            }
            Utilities.OUTPUT_STYLE style = null;
            i++;
            for (Utilities.OUTPUT_STYLE s : Utilities.OUTPUT_STYLE.values()) {
              if (s.cmdHook.equals(args[i].toLowerCase())) style = s;
            }
            if (type != null && style != null) {
              new ReportParser().parseReport(type, style);
              System.out.println(log.getLog());
            } else {
              System.out.println("Incorrect values given");
            }
          } else {
            System.out.println("Incorrect number of values given");
          }
          break;
        default:
          System.out.println("Unknown flag.  Please use /HELP or /? for list of flags");
          break;
      }
    }
    if(gui) new ReportParserGUI();
  }
}
