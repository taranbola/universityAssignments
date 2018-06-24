// Example of using QCDNumber
// (NDE, 2015-10-01)

#include <QtWidgets>

int main(int argc, char *argv[])
{
  QApplication app(argc, argv);

  QLCDNumber* number = new QLCDNumber(6);

  number->setSegmentStyle(QLCDNumber::Flat);
  number->display(123);
  number->resize(200, 85);
  number->show();

  return app.exec();
}
