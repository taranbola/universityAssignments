// A simple Qt image viewing application
// (NDE, 2015-10-07)

#include <QtWidgets>
#include <iostream>

using namespace std;

int main(int argc, char *argv[])
{
  QApplication app(argc, argv);

  // Check that an image filename has been supplied

  if (argc == 1) {
    cerr << "Usage: ./imgviewer <filename>" << endl;
    return 1;
  }

  // Load image and assign it to a label

  QPixmap image(argv[1]);

  QLabel* label = new QLabel();
  label->setPixmap(image);

  // Create and display a scrollable view of the image

  QScrollArea* view = new QScrollArea();

  view->setBackgroundRole(QPalette::Dark);
  view->setWidget(label);
  view->setWindowTitle(argv[1]);

  view->show();

  return app.exec();
}
