package com.adamcosentino.ReportParser.Reports;

import com.adamcosentino.ReportParser.Utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * Author: Adam Cosentino
 * Date: 7/5/2016 11:57 AM
 * Project Name: ReportParser
 * File Description:
 *
 */
public class OutputSynergyReport extends OutputReport {

  private static final int CHAR_MAX = 130;
  private static final int LINE_MAX = 60;

  private String heading = "";

  public OutputSynergyReport(Report report, String heading) {
    super(report);
    this.heading = heading;
  }

  @Override
  public void cleanReport() {
    finalReport = new StringBuilder();
    /** DETERMINE COLUMN SPACING **/
    int[] spacing = new int[getColumnHeadings().length];
    String[] temp = getColumnHeadings();
    for(int i = 0; i < temp.length; i++){
      spacing[i] = temp[i].length();
    }

    String tempData[][] = getReportData();
    for (String[] aTempData : tempData) {
      for (int j = 0; j < aTempData.length; j++) {
        int cellSpace = spacing[j] < aTempData[j].length() ? aTempData[j].length() : spacing[j];
        spacing[j] = cellSpace > CHAR_MAX ? CHAR_MAX : cellSpace;
      }
    }

    /** SET COLUMN SPACING **/
    temp = getColumnHeadings();
    for(int i = 0; i < temp.length; i++){
      temp[i] += Utilities.insertSpaces(spacing[i] - temp[i].length() + 1);
    }
    setColumnHeadings(temp);

    tempData = getReportData();
    for(int i = 0; i < tempData.length; i++){
      for(int j = 0; j < tempData[i].length; j++){
        if(tempData[i][j].length() > CHAR_MAX - 1) tempData[i][j] = tempData[i][j].substring(0,CHAR_MAX - 2);
        tempData[i][j] += Utilities.insertSpaces(spacing[j] - tempData[i][j].length() + 1);
      }
    }
    setReportData(tempData);

    /** DETERMINE LINE BREAKS **/
    ArrayList<Integer> colBreaks = new ArrayList<>();
    int cur = 0;
    for(int i = 0; i < getColumnHeadings().length; i++){
      if(cur + getColumnHeadings()[i].length() > CHAR_MAX) {
        colBreaks.add(i);
        cur = getColumnHeadings()[i].length();
      } else {
        cur += getColumnHeadings()[i].length();
      }
    }

    /** SET PAGE HEADER **/
    int headerLines = 3;
    int ff = 12;
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    StringBuilder header = new StringBuilder();
    header.append(((char) ff))
        .append("Wayne Savings Community Bank")
        .append(Utilities.insertSpaces(36 - heading.length() / 2))
        .append(heading)
        .append(Utilities.insertSpaces(57 - heading.length() / 2))
        .append("Page <PgNum>\r\n")
        .append("RUN ")
        .append(df.format(new Date()))
        .append(Utilities.insertSpaces(46 - getTitle().length() / 2))
        .append(getTitle())
        .append("\r\n\r\n");

    for(int i = 0; i < getColumnHeadings().length; i++){
      if(colBreaks.contains(i)) header.append("\r\n");
      header.append(getColumnHeadings()[i]);
    }
    headerLines += 1 + colBreaks.size();

    /** SET PAGE BREAKS **/
    int lines = headerLines;
    finalReport.append(header).append("\r\n");
    for(int i = 0; i < getReportData().length; i++){
      for(int j = 0; j < getReportData()[i].length; j++){
        if(colBreaks.contains(j)) {
          finalReport.append("\r\n");
          lines++;
          if(lines >= LINE_MAX) {
            finalReport.append(header).append("\r\n");
            lines = headerLines;
          }
        }
        finalReport.append(getReportData()[i][j]);
      }
      finalReport.append("\r\n");
      lines++;
      if(lines == LINE_MAX) {
        finalReport.append(header).append("\r\n");
        lines = headerLines;
      }
    }
  }
}
