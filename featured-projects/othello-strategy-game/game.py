"""Game runner for the R-R game."""


class Game:
    """Runs one full game between two players."""

    def __init__(self, board, player_x, player_o):
        """Initialize the game."""
        self.board = board
        self.player_x = player_x
        self.player_o = player_o
        self.current_token = "X"
        self.consecutive_passes = 0

    def get_current_player(self):
        """Return the player whose turn it is."""
        if self.current_token == "X":
            return self.player_x
        return self.player_o

    def switch_turn(self):
        """Switch the current player."""
        if self.current_token == "X":
            self.current_token = "O"
        else:
            self.current_token = "X"

    def play(self, verbose=False):
        """Play the game and return the winner."""
        while not self.board.game_over(self.consecutive_passes):
            current_player = self.get_current_player()
            move = current_player.choose_move(self.board)

            self.board.apply_move(move, current_player.token)

            if move.is_pass:
                self.consecutive_passes += 1
            else:
                self.consecutive_passes = 0

            if verbose:
                print(current_player.get_name(), current_player.token, move)
                print(self.board)
                print()

            self.switch_turn()

        return self.board.get_winner()