// Widget subclassing example - class implementation
// (NDE, 2016-09-08)

#include "widget.hpp"
#include <QtWidgets>

HelloWidget::HelloWidget(QWidget* parent): QWidget(parent)
{
  label = new QLabel("Hello World!");
  label->setStyleSheet(
    "font: bold 24pt; color: red; margin: 10px;");

  QHBoxLayout* layout = new QHBoxLayout();
  layout->addStretch();
  layout->addWidget(label);
  layout->addStretch();

  setLayout(layout);
}
