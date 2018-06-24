#include <QApplication>
#include "ui_discoverPage.h"

int main(int argc, char* argv[])
{
  QApplication app(argc, argv);
  QWidget* window = new QWidget;

  Ui::Form control;
  control.setupUi(window);

  window->show();

 return app.exec();
}
