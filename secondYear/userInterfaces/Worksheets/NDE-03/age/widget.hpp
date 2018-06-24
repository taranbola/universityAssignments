// Signals & slots example - class definition
// (NDE, 2016-09-08)

#pragma once

#include <QWidget>

class QSpinBox;
class QSlider;

class AgeWidget: public QWidget
{
  public:
    AgeWidget(QWidget* = 0);

  private:
    QSpinBox* spinBox;
    QSlider* slider;
};
