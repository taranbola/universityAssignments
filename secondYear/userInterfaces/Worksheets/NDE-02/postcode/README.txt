One advantage of an object-oriented GUI toolkit is that you
can create new and more specialised widgets by subclassing the
existing widgets in the toolkit.

Start by looking at postcode.hpp and postcode.cpp.  Together,
these files define a new custom widget called PostcodeLineEdit,
inheriting from QLineEdit.  This new widget will do everything
that a regular QLineEdit can do but will add in the capability to
validate input as a UK postcode.

Validation is done by simply setting the validator of the
widget to be an instance of QRegExpValidator.  This validator
allows you to specify a pattern for valid input as a regular
expression.  The very complex regexp used here comes from
Wikipedia and is claimed to validate UK postcodes successfully.

The program that uses the new widget is in main.cpp.  Notice
line 18, which associates a descriptive label with the postcode
entry widget.  Notice also how the two widgets are laid out
horizontally by means of a QHBoxLayout container on lines 22-24.
This container is then added to a QWidget, which acts as the
window for the application.
