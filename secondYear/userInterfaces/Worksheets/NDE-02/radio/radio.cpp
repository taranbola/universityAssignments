// Example of using QRadioButton
// (NDE, 2015-10-01)

#include <QtWidgets>

int main(int argc, char *argv[])
{
  QApplication app(argc, argv);

  // Create some radio buttons and select one of them

  QRadioButton* apple = new QRadioButton("Apple");
  QRadioButton* banana = new QRadioButton("Banana");
  QRadioButton* kiwi = new QRadioButton("Kiwi");

  apple->setChecked(true);

  // Arrange them vertically (discussed later)

  QVBoxLayout* layout = new QVBoxLayout();
  layout->addWidget(apple);
  layout->addWidget(banana);
  layout->addWidget(kiwi);
  layout->addStretch(1);

  // Create an enclosing box with title (not essential)

  QGroupBox* window = new QGroupBox("Fruit");
  //window->setCheckable(true);
  window->setLayout(layout);
  window->setMinimumWidth(180);
  window->show();

  return app.exec();
}
