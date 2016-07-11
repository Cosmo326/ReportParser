package com.adamcosentino.ReportParser.Reports;

/**
 *
 * Author: Adam Cosentino
 * Date: 7/5/2016 11:56 AM
 * Project Name: ReportParser
 * File Description:
 *
 */
public abstract class OutputReport extends Report {

  StringBuilder finalReport = null;

  OutputReport(Report report){
    setTitle(report.getTitle());
    setColumnHeadings(report.getColumnHeadings());
    setReportData(report.getReportData());
  }

  protected abstract void cleanReport();

  public String toString(){
    if(finalReport == null) cleanReport();
    return finalReport.toString();
  }
}
