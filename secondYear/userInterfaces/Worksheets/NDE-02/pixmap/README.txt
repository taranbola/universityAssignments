Example of how a QLabel can use a QPixmap as its content.

A label's pixmap is specified by a property called 'pixmap' - which
means that you set the value of the property using a method
called setPixmap().  Once you realise that all Qt widgets adopt
this convention, you'll find using them much easier!

Try compiling and running the demo as-is, then try again after
uncommenting the call to setAlignment().  You should find that this
centres the pixmap within the label (and hence within the window).
