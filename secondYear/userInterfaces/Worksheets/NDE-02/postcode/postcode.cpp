// Example of component customisation via subclassing
// (NDE, 2015-10-01)

#include "postcode.hpp"

// Regular expression for UK postcodes (based on rules at Wikipedia)
static const QRegExp REG_EXP("^(GIR ?0AA|[A-PR-UWYZ]([0-9]{1,2}|([A-HK-Y][0-9]([0-9ABEHMNPRV-Y])?)|[0-9][A-HJKPS-UW]) ?[0-9][ABD-HJLNP-UW-Z]{2})$");

PostcodeLineEdit::PostcodeLineEdit(QWidget* parent): QLineEdit(parent)
{
  QRegExpValidator* validator = new QRegExpValidator(REG_EXP, this);
  this->setValidator(validator);
}
