package com.adamcosentino.ReportParser;

import com.adamcosentino.ReportParser.Logger.ProgramLog;
import com.adamcosentino.ReportParser.Logger.iLogDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * Author: Adam Cosentino
 * Date: 7/7/2016 11:57 AM
 * Project Name: ReportParser
 * File Description:
 *
 */
class ReportParserGUI implements ActionListener, iLogDisplay{

  private final JTextArea logArea;
  private final JComboBox<String> reportChooser, outputChooser;
  private final JButton parseBut, clearLog, updateLogBut;
  private final ReportParser parser = new ReportParser();
  private final ProgramLog log;

  public ReportParserGUI() {
    log = ProgramLog.getInstance();
    log.registerDisplay(this);

    JFrame frame = new JFrame("Report Parser");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setResizable(false);
    int height = 300;
    int width = 500;
    frame.setMinimumSize(new Dimension(width, height));
    JLayeredPane pane = new JLayeredPane();
    frame.setContentPane(pane);
    frame.pack();

    width = pane.getWidth();
    height = pane.getHeight();

    String[] reports = new String[Utilities.REPORT_TYPE.values().length];
    for(int i = 0; i < reports.length; i++){
      reports[i] = Utilities.REPORT_TYPE.values()[i].Listing;
    }

    reportChooser = new JComboBox<>(reports);
    reportChooser.setBounds(5,5,190,25);
    pane.add(reportChooser);

    String[] outputs = new String[Utilities.OUTPUT_STYLE.values().length];
    for(int i = 0; i < outputs.length; i++){
      outputs[i] = Utilities.OUTPUT_STYLE.values()[i].Listing;
    }

    outputChooser = new JComboBox<>(outputs);
    outputChooser.setBounds(5,35,190,25);
    pane.add(outputChooser);

    logArea = new JTextArea("");
    logArea.setEditable(false);

    parseBut = new JButton("Create Report");
    parseBut.setBounds(width - 195,5,190,55);
    parseBut.addActionListener(this);
    pane.add(parseBut);

    JScrollPane logPane = new JScrollPane(logArea);
    logPane.setBounds(5,65, width - 10 , height - 100);
    pane.add(logPane);

    clearLog = new JButton("Clear Log");
    clearLog.setBounds(5, height - 30,190,25);
    clearLog.addActionListener(this);
    pane.add(clearLog);

    updateLogBut = new JButton("Update Log");
    updateLogBut.setBounds(width -195, height -30,190,25);
    updateLogBut.addActionListener(this);
    pane.add(updateLogBut);

    frame.setVisible(true);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource().equals(parseBut)) {
      int reports = reportChooser.getSelectedIndex();
      int output = outputChooser.getSelectedIndex();
      parser.parseReport(Utilities.REPORT_TYPE.values()[reports], Utilities.OUTPUT_STYLE.values()[output]);
    } else if(e.getSource().equals(clearLog)){
      logArea.setText("");
      log.clearLog();
    } else if(e.getSource().equals(updateLogBut)){
      logArea.setText("");
      logArea.append(log.getLog());
    }
  }

  @Override
  public void updateDisplay(String log) {
    logArea.setText(log);
  }
}
