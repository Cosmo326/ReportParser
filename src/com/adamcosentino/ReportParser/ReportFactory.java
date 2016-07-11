package com.adamcosentino.ReportParser;

import com.adamcosentino.ReportParser.Logger.ProgramLog;
import com.adamcosentino.ReportParser.Reports.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 *
 * Author: Adam Cosentino
 * Date: 6/30/2016 11:56 AM
 * Project Name: ReportParser
 * File Description:
 *
 */
final class ReportFactory {

  private static ReportFactory instance = null;
  private final ProgramLog log;

  private ReportFactory(){
    log = ProgramLog.getInstance();
  }

  /**
   * Singleton retrieval
   * @return ReportFactory instance
   */
  static ReportFactory getInstance(){
    if(instance == null) instance = new ReportFactory();
    return instance;
  }

  /**
   * Factory Method for retrieving correct report
   * @param file File to be used to determine the report
   * @return the report that is determined
   */
  RawReport getRawReport(Utilities.REPORT_TYPE type, File file){
    StringBuilder output = new StringBuilder();

    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
      String line;
      while ((line = reader.readLine()) != null) {
        output.append(line);
        if(type == Utilities.REPORT_TYPE.BeB) output.append("\n");
      }
      reader.close();
    } catch (Exception e) {
      log.logError(e.getMessage());
    }

    if(type == Utilities.REPORT_TYPE.CeB){
      if(RawCeBJSReport.isReport(output.toString())) return new RawCeBJSReport(output.toString());
      else if(RawCeBHTMLReport.isReport(output.toString())) return new RawCeBHTMLReport(output.toString());
    } else if (type == Utilities.REPORT_TYPE.BeB){
      return new RawBeBReport(output.toString(), file.getName());
    }
    return null;
  }

  OutputReport getOutputReport(Utilities.OUTPUT_STYLE style, Utilities.REPORT_TYPE type, Report report){
    if(style == Utilities.OUTPUT_STYLE.SYNERGY){
      return new OutputSynergyReport(report, type.Heading);
    } else if(style == Utilities.OUTPUT_STYLE.DEBUG){
      return new OutputDebugReport(report);
    }
    return null;
  }
}
