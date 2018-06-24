// Label configuration in Qt
// (NDE, 2015-10-01)

#include <QtWidgets>

int main(int argc, char *argv[])
{
  QApplication app(argc, argv);

  QLabel* label = new QLabel("Hello World!");
  label->setFont(QFont("URW Chancery L", 36));
  label->setMargin(20);
  //label->setEnabled(false);
  label->show();

  return app.exec();
}
