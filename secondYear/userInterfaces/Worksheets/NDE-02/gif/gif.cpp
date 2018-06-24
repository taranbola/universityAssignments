// Example of embedding a GIF animation in a label
// (NDE, 2015-10-01)

#include <QtWidgets>
#include <iostream>

int main(int argc, char *argv[])
{
  QApplication app(argc, argv);

  // Make sure that a filename has been specified

  if (argc == 1) {
    std::cerr << "Usage: ./gif <filename>" << std::endl;
    return 1;
  }

  // Load animation from the given file

  QMovie* gif = new QMovie(argv[1]);
  gif->jumpToFrame(0);

  // Embed animation in a label and display GUI

  QLabel* label = new QLabel();
  label->setWindowTitle("GIF Player");
  label->setMovie(gif);
  label->show();

  // Start the animation

  gif->start();

  return app.exec();
}
