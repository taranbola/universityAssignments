// Signals & slots example - class implementation
// (NDE, 2016-09-08)

#include "widget.hpp"
#include <QtWidgets>

AgeWidget::AgeWidget(QWidget* parent): QWidget(parent)
{
  // Create and configure widgets

  spinBox = new QSpinBox();
  spinBox->setFont(QFont("DejaVu Sans", 14));
  spinBox->setRange(0, 130);

  slider = new QSlider(Qt::Horizontal);
  slider->setRange(0, 130);

  // Arrange widgets

  QHBoxLayout* layout = new QHBoxLayout();
  layout->addWidget(spinBox);
  layout->addWidget(slider);
  setLayout(layout);

  // Connect spinbox and slider so that interacting
  // with one will update the other

  connect(spinBox, SIGNAL(valueChanged(int)),
          slider, SLOT(setValue(int)));

  connect(slider, SIGNAL(valueChanged(int)),
          spinBox, SLOT(setValue(int)));

  spinBox->setValue(35);
}
