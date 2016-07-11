package com.adamcosentino.ReportParser.Reports;

import java.util.Arrays;

/**
 *
 * Author: Adam Cosentino
 * Date: 7/7/2016 11:55 AM
 * Project Name: ReportParser
 * File Description:
 *
 */
public final class RawBeBReport extends RawReport {

  public RawBeBReport(String raw, String FileName) {
    super(raw);

    /** SET TITLE **/
    if(FileName.toLowerCase().contains("stepupactivity")) setTitle("Step Up Activity Report");
    else if(FileName.toLowerCase().contains("useractivityreport")) setTitle("User Activity Report");
    else if(FileName.toLowerCase().contains("userinvalidloginreport")) setTitle("User Invalid Login Report");
    else if(FileName.toLowerCase().contains("approvalsandlimitschange")) setTitle("Approvals and Limit Change Report");
    else System.out.println(FileName);
  }

  @Override
  protected void createReport(String raw) {
    String[] arr = raw.split("\\n");
    /** SET COLUMN HEADINGS **/
    setColumnHeadings(arr[0].split("\\t"));

    /** SET DATA **/
    String[][] temp = new String[arr.length - 1][getColumnHeadings().length];
    for(int i = 0; i < arr.length - 1; i++){
      temp[i] = arr[i + 1].split("(\\t)");
    }
    setReportData(temp);

  }
}
