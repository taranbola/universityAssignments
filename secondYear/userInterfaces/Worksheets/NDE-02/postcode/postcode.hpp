// Example of component customisation via subclassing
// (NDE, 2015-10-01)

#pragma once

#include <QtWidgets>

class PostcodeLineEdit: public QLineEdit
{
  public:
    PostcodeLineEdit(QWidget* parent = 0);
};
