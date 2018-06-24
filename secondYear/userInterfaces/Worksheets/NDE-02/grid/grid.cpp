// QGridLayout example
// (NDE, 2015-10-08)

#include <QtWidgets>

int main(int argc, char* argv[])
{
  QApplication app(argc, argv);

  QWidget window;
  window.setWindowTitle("QGridLayout Example");

  QLineEdit* name = new QLineEdit();
  QFont font("DejaVu Sans", 14);
  name->setFont(font);

  QLabel* nameLabel = new QLabel("&Name:");
  nameLabel->setFont(font);
  nameLabel->setBuddy(name);

  QSpinBox* age = new QSpinBox();
  age->setFont(font);
  age->setRange(1, 100);
  //age->setSizePolicy(QSizePolicy::Maximum, QSizePolicy::Maximum);

  QLabel* ageLabel = new QLabel("&Age:");
  ageLabel->setFont(font);
  ageLabel->setBuddy(age);

  QGridLayout* layout = new QGridLayout();
  layout->addWidget(nameLabel, 0, 0);
  layout->addWidget(name, 0, 1);
  layout->addWidget(ageLabel, 1, 0);
  layout->addWidget(age, 1, 1);

  window.setLayout(layout);
  window.setMinimumWidth(320);
  window.show();

  return app.exec();
}
