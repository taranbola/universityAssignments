// Custom slot example - main program
// (NDE, 2016-09-08)

#include <QApplication>
#include "controller.hpp"

int main(int argc, char* argv[])
{
  QApplication app(argc, argv);

  Controller* control = new Controller();
  control->setMinimumWidth(200);
  control->show();

  return app.exec();
}
