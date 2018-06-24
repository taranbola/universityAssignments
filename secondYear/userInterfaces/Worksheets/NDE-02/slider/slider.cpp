// Simple example of QSlider
// (NDE, 2015-10-01)

#include <QtWidgets>

int main(int argc, char* argv[])
{
  QApplication app(argc, argv);

  QSlider* slider = new QSlider(Qt::Horizontal);
  slider->setRange(1, 100);
  slider->setTickPosition(QSlider::TicksBothSides);

  slider->show();

  return app.exec();
}
