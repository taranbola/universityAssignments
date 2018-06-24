// QFormLayout example
// (NDE, 2015-10-08)

#include <QtWidgets>

int main(int argc, char* argv[])
{
  QApplication app(argc, argv);

  QLineEdit* name = new QLineEdit();
  QFont font("DejaVu Sans", 14);
  name->setFont(font);

  QSpinBox* age = new QSpinBox();
  age->setRange(1, 100);
  age->setFont(font);
  //age->setSizePolicy(QSizePolicy::Maximum, QSizePolicy::Maximum);

  QFormLayout* layout = new QFormLayout();
  layout->addRow("&Name:", name);
  layout->addRow("&Age:", age);
  layout->labelForField(name)->setFont(font);
  layout->labelForField(age)->setFont(font);

  QWidget* window = new QWidget();
  window->setWindowTitle("QFormLayout Example");

  window->setLayout(layout);
  window->setMinimumWidth(320);
  window->show();

  return app.exec();
}
