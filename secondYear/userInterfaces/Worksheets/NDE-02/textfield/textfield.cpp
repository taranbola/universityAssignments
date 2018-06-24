// Example of using QLineEdit
// (NDE, 2015-10-01)

#include <QtWidgets>

int main(int argc, char* argv[])
{
  QApplication app(argc, argv);

  QLineEdit* textField = new QLineEdit();

  textField->setFont(QFont("DejaVu Sans", 24));
  textField->setPlaceholderText("Your name");
  //textField->setReadOnly(true);

  textField->show();

  return app.exec();
}
