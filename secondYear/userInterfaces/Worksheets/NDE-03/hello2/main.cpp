// Widget subclassing example - main program
// (NDE, 2016-09-08)

#include <QApplication>
#include "widget.hpp"

int main(int argc, char *argv[])
{
  QApplication app(argc, argv);

  HelloWidget widget;   // top-level widget can be created on stack
  widget.show();

  return app.exec();
}
