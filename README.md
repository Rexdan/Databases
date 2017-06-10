The entire purpose of this program is to convert a set of relations into BCNF form.

In order to be a little more efficient, I decided to use a stack instead of recursion.

The user simply needs to create a text document (.txt) and add relations like so:

  A B C D E
  A B -> C
  C -> D
  D -> B E

This text document must be in the root directory of the program.
The output of the program will be shown through terminal with some progress messages.
End result will be a set of relations that are in BCNF form.
