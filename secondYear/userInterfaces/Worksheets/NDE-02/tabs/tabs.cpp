// Example of using QTabWidget
// (NDE, 2015-10-07)

#include <QtWidgets>

int main(int argc, char* argv[])
{
  QApplication app(argc, argv);

  // Create and configure the widget

  QTabWidget* window = new QTabWidget();

  window->setWindowTitle("QTabWidget Example");
  window->setTabPosition(QTabWidget::South);
  window->setTabShape(QTabWidget::Triangular);

  QString filename[3] = {
    "bugs1.png",
    "bugs2.png",
    "coderage.png"
  };

  for (int i = 0; i < 3; ++i) {
    // Load image and assign it to a label

    QPixmap image(filename[i]);
    QLabel* label = new QLabel();
    label->setPixmap(image);

    // Create a scrollable view of the image

    QScrollArea* view = new QScrollArea();
    view->setBackgroundRole(QPalette::Dark);
    view->setWidget(label);

    // Add view to a new tab

    window->addTab(view, filename[i]);
  }

  window->show();

  return app.exec();
}
