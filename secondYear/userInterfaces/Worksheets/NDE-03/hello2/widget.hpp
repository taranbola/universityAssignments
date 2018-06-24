// Widget subclassing example - class definition
// (NDE, 2016-09-08)

#pragma once

#include <QWidget>   // superclass for this widget

class QLabel;        // forward ref to contained widget

class HelloWidget: public QWidget
{
  public:
    HelloWidget(QWidget* = 0);

  private:
    QLabel* label;
};
