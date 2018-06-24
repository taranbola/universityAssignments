#include <QtWidgets>
#include "ui_volume.h"

int main(int argc, char* argv[])
{
  QApplication app(argc, argv);

  QWidget* window = new QWidget;

  Ui::VolumeControl control;
  control.setupUi(window);

  window->show();

  return app.exec();
}
