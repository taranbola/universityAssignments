// Simple example of QSpinBox
// (NDE, 2015-10-01)

#include <QtWidgets>

int main(int argc, char* argv[])
{
  QApplication app(argc, argv);

  QSpinBox* spinbox = new QSpinBox();
  spinbox->setRange(-10, 10);
  spinbox->setFont(QFont("DejaVu Sans", 24));

  spinbox->setMinimumWidth(200);
  spinbox->show();

  return app.exec();
}
