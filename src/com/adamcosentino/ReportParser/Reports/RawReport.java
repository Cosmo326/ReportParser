package com.adamcosentino.ReportParser.Reports;

import java.util.Arrays;

/**
 *
 * Author: Adam Cosentino
 * Date: 7/5/2016 11:56 AM
 * Project Name: ReportParser
 * File Description:
 *
 */
public abstract class RawReport extends Report {

  RawReport(String raw) {
    createReport(raw);
  }

  protected abstract void createReport(String raw);

  @Override
  public String toString(){
    return "Class: " + getClass().getName() + "\n" + "Title: " + getTitle() + "\n" +
        "Column Headings: " + Arrays.toString(getColumnHeadings()) + "\n" +
        "Report Data: " + Arrays.deepToString(getReportData()) + "\n";
  }
}
