// Signals & slots example - class definition
// (NDE, 2016-09-08)

#pragma once

#include <QtWidgets>

class AgeWidget: public QWidget
{
  public:
    AgeWidget(QWidget* = 0);

  private:
    QSpinBox* spinBox;
    QSlider* slider;
};
