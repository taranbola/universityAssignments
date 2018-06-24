// "Hello World!" in C++ for Qt 5

#include <QtWidgets>

int main(int argc, char *argv[])
{
  QApplication app(argc, argv);

  QLabel* label = new QLabel("<h1>Hello World!</h1>");
  label->show();

  return app.exec();
}
