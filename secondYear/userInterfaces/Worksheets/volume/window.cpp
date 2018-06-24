#include "window.hpp"

VolumeControl::VolumeControl()
{
  setupUi(this);
  connect(slider, SIGNAL(valueChanged(int)), this, SLOT(numberColour(int)));
}

void VolumeControl::numberColour(int value)
{
  if (value > 75) {
    number->setStyleSheet("color:red");
  }
  else if (value < 25) {
    number->setStyleSheet("color:green");
  }
  else {
    number->setStyleSheet("color:black");
  }
}
