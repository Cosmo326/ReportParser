package com.adamcosentino.ReportParser.Reports;

/**
 *
 * Author: Adam Cosentino
 * Date: 6/30/2016 11:55 AM
 * Project Name: ReportParser
 * File Description:
 *
 */
public class RawCeBJSReport extends RawReport {

  public RawCeBJSReport(String raw) {
    super(raw.replaceAll("(&nbsp;)"," "));
  }

  @Override
  protected void createReport(String raw){
    /** SET TITLE **/
    String[] sample = raw.split("B>");
    setTitle(sample[1].replaceAll("</","").trim());

    /** SET COLUMN HEADINGS **/
    sample = raw.split("Array\\(");
    setColumnHeadings((sample[1].substring(0, sample[1].indexOf(");"))).replaceAll("[\"']","").split(","));

    /** SET DATA **/
    sample = raw.split("SortableRow\\(");
    String[][] data = new String[sample.length - 1][getColumnHeadings().length];
    for (int i = 1; i < sample.length; i++) {
      String[] line = sample[i].substring(sample[i].indexOf("\""), sample[i].indexOf("),")-1).split("(\",\")|(,)");
      for(int j = 0; j < getColumnHeadings().length; j++){
        data[i - 1][j] = line[j].replaceAll("(\")|( {12}+)","");
      }
    }
    setReportData(data);
  }

  public static boolean isReport(String raw) {
    return raw.contains("SortableRow");
  }
}
