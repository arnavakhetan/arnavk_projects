"""Board logic for the R-R game."""


class Board:
    """Represents the 16x16 R-R board and its rules."""

    SIZE = 16
    EMPTY = "."
    X = "X"
    O = "O"

    DIRECTIONS = [
        (-1, -1), (-1, 0), (-1, 1),
        (0, -1),           (0, 1),
        (1, -1),  (1, 0),  (1, 1)
    ]

    def __init__(self):
        """Initialize the board with the starting position."""
        self.grid = []
        row_index = 0
        while row_index < self.SIZE:
            row = []
            col_index = 0
            while col_index < self.SIZE:
                row.append(self.EMPTY)
                col_index += 1
            self.grid.append(row)
            row_index += 1

        middle = self.SIZE // 2
        self.grid[middle - 1][middle - 1] = self.X
        self.grid[middle - 1][middle] = self.O
        self.grid[middle][middle - 1] = self.O
        self.grid[middle][middle] = self.X

    def clone(self):
        """Return a deep copy of the board."""
        copied = Board.__new__(Board)
        copied.grid = []

        row_index = 0
        while row_index < self.SIZE:
            copied.grid.append(self.grid[row_index][:])
            row_index += 1

        return copied

    def opponent(self, token):
        """Return the opponent token."""
        if token == self.X:
            return self.O
        return self.X

    def in_bounds(self, row, col):
        """Return whether a position is on the board."""
        return 0 <= row < self.SIZE and 0 <= col < self.SIZE

    def get_cell(self, row, col):
        """Return the board value at row, col."""
        return self.grid[row][col]

    def set_cell(self, row, col, value):
        """Set the board value at row, col."""
        self.grid[row][col] = value

    def has_token(self, token):
        """Return whether the token appears anywhere on the board."""
        row_index = 0
        while row_index < self.SIZE:
            col_index = 0
            while col_index < self.SIZE:
                if self.grid[row_index][col_index] == token: # Written with LLM's help.
                    return True
                col_index += 1
            row_index += 1
        return False

    def is_full(self):
        """Return whether the board has no empty cells."""
        row_index = 0
        while row_index < self.SIZE:
            col_index = 0
            while col_index < self.SIZE:
                if self.grid[row_index][col_index] == self.EMPTY:
                    return False
                col_index += 1
            row_index += 1
        return True

    def count_tokens(self):
        """Count X and O tokens."""
        counts = {self.X: 0, self.O: 0}

        row_index = 0
        while row_index < self.SIZE:
            col_index = 0
            while col_index < self.SIZE:
                value = self.grid[row_index][col_index]
                if value == self.X:
                    counts[self.X] += 1
                elif value == self.O:
                    counts[self.O] += 1
                col_index += 1
            row_index += 1

        return counts

    def _captured_in_direction(self, row, col, token, row_step, col_step):
        """Return captured positions in one direction."""
        if self.get_cell(row, col) != self.EMPTY:
            return []

        opponent_token = self.opponent(token)
        captured = []

        current_row = row + row_step
        current_col = col + col_step

        while self.in_bounds(current_row, current_col):
            current_value = self.get_cell(current_row, current_col)

            if current_value == opponent_token:
                captured.append((current_row, current_col))
            elif current_value == token:
                if len(captured) > 0:
                    return captured
                return []
            else:
                return []

            current_row += row_step
            current_col += col_step

        return []

    def get_bracketed_positions(self, row, col, token):
        """Return all bracketed opponent positions for a move."""
        all_captured = []
        index = 0

        while index < len(self.DIRECTIONS):
            row_step, col_step = self.DIRECTIONS[index]
            captured = self._captured_in_direction(
                row, col, token, row_step, col_step
            )

            capture_index = 0
            while capture_index < len(captured):
                all_captured.append(captured[capture_index])
                capture_index += 1

            index += 1

        return all_captured

    def is_legal_placement(self, row, col, token):
        """Return whether placing at row, col is legal."""
        if not self.in_bounds(row, col):
            return False

        if self.get_cell(row, col) != self.EMPTY:
            return False

        bracketed = self.get_bracketed_positions(row, col, token)
        return len(bracketed) > 0

    def has_any_legal_non_pass_move(self, token):
        """Return whether the player has any legal placement move."""
        row_index = 0
        while row_index < self.SIZE:
            col_index = 0
            while col_index < self.SIZE:
                if self.is_legal_placement(row_index, col_index, token):
                    return True
                col_index += 1
            row_index += 1
        return False

    def get_legal_moves(self, token, move_class):
        """Return all legal moves for the token."""
        moves = []

        row_index = 0
        while row_index < self.SIZE:
            col_index = 0
            while col_index < self.SIZE:
                if self.is_legal_placement(row_index, col_index, token): # Written with LLM's help. 
                    moves.append(move_class(row_index, col_index, "reverse"))
                    moves.append(move_class(row_index, col_index, "remove"))
                col_index += 1
            row_index += 1

        if len(moves) == 0:
            moves.append(move_class(None, None, "pass", True))

        return moves

    def apply_move(self, move, token):
        """Apply a move for the given token."""
        if move.is_pass:
            if self.has_any_legal_non_pass_move(token):
                raise ValueError("Pass is not legal when a normal move exists.")
            return

        if move.row is None or move.col is None:
            raise ValueError("Non-pass move must have row and col.")

        if not self.in_bounds(move.row, move.col):
            raise ValueError("Move out of bounds.")

        if self.get_cell(move.row, move.col) != self.EMPTY:
            raise ValueError("Move must be placed on an empty square.")

        if move.mode != "reverse" and move.mode != "remove":
            raise ValueError("Move mode must be reverse or remove.")

        bracketed = self.get_bracketed_positions(move.row, move.col, token)

        if len(bracketed) == 0:
            raise ValueError("Move must bracket at least one opponent token.")

        self.set_cell(move.row, move.col, token)

        index = 0
        while index < len(bracketed):
            row, col = bracketed[index]

            if move.mode == "reverse":
                self.set_cell(row, col, token)
            else:
                self.set_cell(row, col, self.EMPTY)

            index += 1

    def game_over(self, consecutive_passes):
        """Return whether the game is over."""
        if self.is_full():
            return True

        if consecutive_passes >= 2:
            return True

        if not self.has_token(self.X) or not self.has_token(self.O):
            return True

        if (
            not self.has_any_legal_non_pass_move(self.X)
            and not self.has_any_legal_non_pass_move(self.O)
        ):
            return True

        return False

    def get_winner(self):
        """Return X, O, or None for a tie."""
        counts = self.count_tokens()

        if counts[self.X] > counts[self.O]:
            return self.X
        if counts[self.O] > counts[self.X]:
            return self.O
        return None

    def _count_non_pass_moves(self, token):
        """Count legal placement positions for the token."""
        count = 0

        row_index = 0
        while row_index < self.SIZE:
            col_index = 0
            while col_index < self.SIZE:
                if self.is_legal_placement(row_index, col_index, token):
                    count += 1
                col_index += 1
            row_index += 1

        return count

    def evaluate_for(self, token):
        """Return a simple heuristic score for the token."""
        opponent_token = self.opponent(token)
        counts = self.count_tokens()

        piece_score = counts[token] - counts[opponent_token]

        corners = [
            (0, 0),
            (0, self.SIZE - 1),
            (self.SIZE - 1, 0),
            (self.SIZE - 1, self.SIZE - 1)
        ]   

        corner_score = 0
        index = 0
        while index < len(corners):
            row, col = corners[index]
            value = self.get_cell(row, col)

            if value == token:
                corner_score += 10
            elif value == opponent_token:
                corner_score -= 10

            index += 1

        return piece_score + corner_score

    def __str__(self):
        """Return a display string for the board."""
        header = "  "
        col_index = 0
        while col_index < self.SIZE:
            header += chr(ord("A") + col_index) + " "
            col_index += 1

        lines = [header.rstrip()] # Written with LLM's help.

        row_index = 0
        while row_index < self.SIZE:
            line = str(row_index).rjust(2) + " "
            col_index = 0
            while col_index < self.SIZE:
                line += self.grid[row_index][col_index] + " "
                col_index += 1
            lines.append(line.rstrip())
            row_index += 1

        return "\n".join(lines)