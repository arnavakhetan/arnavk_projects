"""Move class for the R-R game."""


class Move:
    """Represents one move in the R-R game."""

    def __init__(self, row, col, mode, is_pass=False):
        """Initialize a move."""
        self.row = row
        self.col = col
        self.mode = mode
        self.is_pass = is_pass

    def __str__(self):
        """Return a readable string form of the move."""
        if self.is_pass:
            return "pass"
        return "(" + str(self.row) + ", " + str(self.col) + ", " + self.mode + ")"