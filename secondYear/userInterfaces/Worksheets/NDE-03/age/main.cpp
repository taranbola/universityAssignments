// Signals & slots example - main program
// (NDE, 2016-09-08)

#include <QApplication>
#include "widget.hpp"

int main(int argc, char* argv[])
{
  QApplication app(argc, argv);

  QWidget* window = new AgeWidget();
  window->setWindowTitle("Enter Your Age");
  window->setMinimumWidth(270);
  window->show();

  return app.exec();
}
