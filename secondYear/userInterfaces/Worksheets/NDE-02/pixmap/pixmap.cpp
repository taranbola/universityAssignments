// Displaying an image on a label in Qt
// (NDE, 2015-10-01)

#include <QtWidgets>

int main(int argc, char* argv[])
{
  QApplication app(argc, argv);

  QPixmap image("world.png");

  QLabel* widget = new QLabel();
  //widget->setAlignment(Qt::AlignHCenter | Qt::AlignVCenter);
  widget->setPixmap(image);
  widget->show();

  return app.exec();
}
