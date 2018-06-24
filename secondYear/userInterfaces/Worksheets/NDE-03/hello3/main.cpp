// Window subclassing example - main program
// (NDE, 2016-09-08)

#include <QApplication>
#include "window.hpp"

int main(int argc, char *argv[])
{
  QApplication app(argc, argv);

  HelloWindow window;
  window.show();

  return app.exec();
}
