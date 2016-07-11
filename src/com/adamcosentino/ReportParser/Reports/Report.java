package com.adamcosentino.ReportParser.Reports;

/**
 *
 * Author: Adam Cosentino
 * Date: 6/29/2016 11:56 AM
 * Project Name: ReportParser
 * File Description:
 *
 */
public abstract class Report implements Cloneable{

  private String title;
  private String[] columnHeadings;
  private String[][] reportData;

  /** GETTERS **/
  String getTitle() { return title; }
  String[] getColumnHeadings() { return columnHeadings; }
  String[][] getReportData() { return reportData; }

  /** SETTERS **/
  void setTitle(String title) { this.title = title; }
  void setColumnHeadings(String[] columnHeadings) { this.columnHeadings = columnHeadings; }
  void setReportData(String[][] reportData) { this.reportData = reportData; }

}
