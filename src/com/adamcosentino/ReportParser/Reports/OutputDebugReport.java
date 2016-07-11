package com.adamcosentino.ReportParser.Reports;

import java.util.Arrays;

/**
 * Author: Adam Cosentino
 * Date: 7/8/2016 8:08 AM
 * Project Name: ReportParser
 * File Description:
 */
public class OutputDebugReport extends OutputReport {

  public OutputDebugReport(Report report) {
    super(report);
  }

  @Override
  public void cleanReport() {
    finalReport = new StringBuilder("Title: ");
    finalReport.append(getTitle())
        .append("\n")
        .append("Column Headings: ")
        .append(Arrays.toString(getColumnHeadings()))
        .append("\n")
        .append("Report Data: ")
        .append(Arrays.deepToString(getReportData()));
  }
}
