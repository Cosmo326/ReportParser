package com.adamcosentino.ReportParser;

import java.util.Arrays;

/**
 *
 * Author: Adam Cosentino
 * Date: 6/22/2016 11:57 AM
 * Project Name: ReportParser
 * File Description:
 *
 */
public abstract class Utilities {

  public static String insertSpaces(int number){
    char[] spaces = new char[number];
    Arrays.fill(spaces,' ');

    return new String(spaces);
  }

  public enum REPORT_TYPE{
    CeB("CeB Report","Internet Banking All Activities Report", "\\\\main-pdc\\FileQue\\InternetBanking\\CeB", "Input", "CeBFiche", "Archive","ceb"),
    BeB("BeB Report","Cash Management All Activities Report", "\\\\main-pdc\\FileQue\\InternetBanking\\BeB", "Input", "BeBFiche", "Archive","beb");

    public final String Listing;
    public final String Heading;
    public final String RootPath;
    public final String InputDir;
    public final String OutputFile;
    public final String ArchiveLoc;
    public final String cmdHook;

    REPORT_TYPE(String Listing, String Heading, String RootPath, String InputDir, String OutputFile, String ArchiveLoc, String cmdHook){
      this.Listing = Listing;
      this.Heading = Heading;
      this.RootPath = RootPath;
      this.InputDir = InputDir;
      this.OutputFile = OutputFile;
      this.ArchiveLoc = ArchiveLoc;
      this.cmdHook = cmdHook;
    }
  }

  public enum OUTPUT_STYLE{
    SYNERGY("To Synergry Format","synergy"),
    DEBUG("To log","log");

    public final String Listing;
    public final String cmdHook;

    OUTPUT_STYLE(String Listing, String cmdHook){
      this.Listing = Listing;
      this.cmdHook = cmdHook;
    }
  }
}
