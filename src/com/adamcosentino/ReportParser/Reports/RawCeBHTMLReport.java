package com.adamcosentino.ReportParser.Reports;

/**
 *
 * Author: Adam Cosentino
 * Date: 6/30/2016 11:55 AM
 * Project Name: ReportParser
 * File Description:
 *
 */
public class RawCeBHTMLReport extends RawReport {

  public RawCeBHTMLReport(String raw) {
    super(raw.replaceAll("(&nbsp;)"," "));
  }

  @Override
  protected void createReport(String raw){
    /** SET TITLE **/
    String[] sample = raw.split("B>");
    setTitle(sample[1].replaceAll("</","").trim());

    /** SET COLUMN HEADINGS **/
    sample = raw.split("<tr class=\"tlabel\">");
    String[] header = sample[1].substring(0,sample[1].indexOf("</tr>")).split("</td>");
    for(int i = 0; i < header.length; i++){
      header[i] = header[i].substring(header[i].lastIndexOf(">") + 1);
    }
    setColumnHeadings(header);

    /** SET DATA **/
    sample = raw.split("<tr valign=\"top\">");
    String[][] data = new String[sample.length - 1][getColumnHeadings().length];
    for (int i = 1; i < sample.length; i++) {
      String[] sub = sample[i].split("(class=\"data\">)|(class=\"gdata\">)");
      for(int j = 1; j < sub.length; j++){
        data[i - 1][j - 1] = sub[j].substring(0,sub[j].indexOf("</td>"));
      }
    }
    setReportData(data);
  }

  public static boolean isReport(String raw) {
    return !raw.contains("SortableRow");
  }
}
