// Signal & slot example - class implementation
// (NDE, 2016-09-08)

#include "controller.hpp"
#include <QtWidgets>

Controller::Controller(QWidget* parent): QWidget(parent)
{
  // Create internal widgets of the Controller

  number = new QLCDNumber(3);
  number->setMinimumHeight(75);
  number->display(50);

  slider = new QSlider(Qt::Horizontal);
  slider->setRange(0, 100);
  slider->setTickPosition(QSlider::TicksBelow);
  slider->setTickInterval(10);
  slider->setValue(50);

  // Arrange the widgets

  QVBoxLayout* layout = new QVBoxLayout();
  layout->addWidget(number);
  layout->addWidget(slider);

  setLayout(layout);

  // Connect slider to number so that adjustments update the number

  connect(slider, SIGNAL(valueChanged(int)), number, SLOT(display(int)));
}
