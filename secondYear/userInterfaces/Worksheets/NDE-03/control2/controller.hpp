// Custom slot example - class definition
// (NDE, 2016-09-08)

#pragma once

#include <QWidget>

class QLCDNumber;
class QSlider;

class Controller: public QWidget
{
  Q_OBJECT

  public:
    Controller(QWidget* = 0);

  private slots:
    void setValue(int);

  private:
    QLCDNumber* number;
    QSlider* slider;
};
