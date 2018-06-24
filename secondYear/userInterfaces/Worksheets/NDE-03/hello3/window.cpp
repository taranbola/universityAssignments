// Window subclassing example - class implementation
// (NDE, 2016-09-08)

#include "window.hpp"
#include <QtWidgets>


HelloWindow::HelloWindow()
{
  createWindowContent();
  createMenuAndStatusBar();
}


void HelloWindow::createWindowContent()
{
  // Create layout

  label = new QLabel("Hello World!");
  label->setStyleSheet(
    "font: bold 24pt; color: red; margin: 10px;");

  QHBoxLayout* layout = new QHBoxLayout();
  layout->addStretch();
  layout->addWidget(label);
  layout->addStretch();

  // Put layout in the middle of the window

  mainWidget = new QWidget();
  mainWidget->setLayout(layout);

  setCentralWidget(mainWidget);
}


void HelloWindow::createMenuAndStatusBar()
{
  statusBar()->showMessage("This is the status bar");

  QMenu* fileMenu = menuBar()->addMenu("&File");

  QAction* open = fileMenu->addAction("&Open");
  open->setShortcuts(QKeySequence::Open);
  open->setStatusTip("Open an existing file");

  QAction* quit = fileMenu->addAction("Quit");
  quit->setShortcuts(QKeySequence::Quit);
  quit->setStatusTip("Quit the application");

  connect(quit, SIGNAL(triggered()), qApp, SLOT(quit()));
}
