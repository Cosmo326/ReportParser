package com.adamcosentino.ReportParser;

import com.adamcosentino.ReportParser.Logger.ProgramLog;
import com.adamcosentino.ReportParser.Reports.Report;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 *
 * Author: Adam Cosentino
 * Date: 6/21/2016 11:55 AM
 * Project Name: ReportParser
 * File Description:
 *
 */

class ReportParser {

  private final ProgramLog log;

  public ReportParser(){
    log = ProgramLog.getInstance();
  }

  void parseReport(Utilities.REPORT_TYPE reportType, Utilities.OUTPUT_STYLE style){
    StringBuilder report = new StringBuilder();
    log.logInfo("*** PARSING STARTED ***");
    ReportFactory reportFactory = ReportFactory.getInstance();
    String archivePath = reportType.RootPath + "\\" + reportType.ArchiveLoc;
    File file = new File(reportType.RootPath + "\\" + reportType.InputDir);
    File[] inputs = file.listFiles();
    if(inputs != null && inputs.length > 0) {
      Report[] reports = new Report[inputs.length];
      for (int i = 0; i < inputs.length; i++) {
        try{ reports[i] = reportFactory.getRawReport(reportType, inputs[i]); }
        catch (Exception e){ log.logError(inputs[i].getName() + " >> " + e.getMessage()); }
        try { reports[i] = reportFactory.getOutputReport(style, reportType, reports[i]); }
        catch (Exception e){ log.logError(inputs[i].getName() + " >> " + e.getMessage()); }
        report.append(reports[i]);
        if(!reportType.ArchiveLoc.equals("")) ArchiveFile(inputs[i], new File(archivePath +"\\" + inputs[i].getName()));
      }

      String output = report.toString();
      int pageNum = 1;
      while (output.contains("<PgNum>")) {
        output = output.replaceFirst("<PgNum>", ("" + pageNum++));
      }
      report.replace(0,report.length() - 1, output);

      exportReport(report.toString(), reportType.RootPath + "\\" + reportType.OutputFile);

      log.logInfo(inputs.length + " files compiled successfully");
    } else {
      log.logWarning("No input Files found");
    }
    log.logInfo("*** PARSING COMPLETE ***");
  }

  private void exportReport(String report, String path){
    log.logInfo("Exporting Report");
    File file = new File(path);
    try {
      if(!file.createNewFile()) {
        log.logWarning("Deleting path");
        file.delete();
      }
      file.createNewFile();
      OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file));
      writer.write(report);
      writer.flush();
      writer.close();
    } catch (IOException e) {
      log.logError(e.getMessage());
    }
  }

  private void ArchiveFile(File source, File destination){
    log.logInfo("Archiving " + source.getName());
    try {
      Files.move(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    } catch (IOException e) {
      log.logError(e.getMessage());
    }
  }
}
