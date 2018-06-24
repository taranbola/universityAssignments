// Signal & slot example - class definition
// (NDE, 2016-09-08)

#pragma once

#include <QWidget>

class QLCDNumber;
class QSlider;

class Controller: public QWidget
{
  public:
    Controller(QWidget* = 0);

  private:
    QLCDNumber* number;
    QSlider* slider;
};
