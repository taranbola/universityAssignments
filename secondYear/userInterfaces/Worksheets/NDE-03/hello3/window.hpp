// Window subclassing example - class definition
// (NDE, 2016-09-08)

#pragma once

#include <QMainWindow>

class QLabel;
class QWidget;

class HelloWindow: public QMainWindow
{
  public:
    HelloWindow();

  private:
    QLabel* label;
    QWidget* mainWidget;

    void createWindowContent();
    void createMenuAndStatusBar();
};
