#include <QtWidgets>

int main(int argc, char* argv[])
{
  QApplication app(argc, argv);

  QLabel* widget = new QLabel("<h1>Hello World!</h1>");
  widget->show();

  return app.exec();
}
