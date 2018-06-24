// Signals & slots example - class implementation
// (NDE, 2016-09-08)

#include "widget.hpp"

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

  connect(
    // Horrible syntax necessary here because valueChanged signal in
    // QSpinBox is overloaded with two versions and we must disambiguate
    // (see http://doc.qt.io/qt-5/signalsandslots-syntaxes.html)
    spinBox, static_cast<void (QSpinBox::*)(int)>(&QSpinBox::valueChanged),
    slider, &QSlider::setValue);

  connect(slider, &QSlider::valueChanged,
          spinBox, &QSpinBox::setValue);

  spinBox->setValue(35);
}
