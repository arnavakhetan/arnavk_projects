"""Player classes for the R-R game."""

from move import Move


class SimpleRNG:
    """Very small pseudo-random generator so no imports are needed."""

    def __init__(self, seed=123456789):
        """Initialize the generator."""
        self.seed = seed

    def next_int(self, limit):
        """Return a pseudo-random integer from 0 to limit - 1."""
        self.seed = (1103515245 * self.seed + 12345) % 2147483648 # Written with LLM's help
        return self.seed % limit


class Player:
    """Base player class."""

    def __init__(self, token):
        """Initialize a player."""
        self.token = token

    def choose_move(self, board):
        """Choose a move."""
        raise NotImplementedError("Subclasses must implement choose_move.")

    def get_name(self):
        """Return the player type name."""
        return "Player"


class RandomPlayer(Player):
    """Player that chooses a legal move at random."""

    rng = SimpleRNG()

    def choose_move(self, board):
        """Choose a pseudo-random legal move."""
        legal_moves = board.get_legal_moves(self.token, Move)
        index = self.rng.next_int(len(legal_moves)) # Written with LLM's help.
        return legal_moves[index]

    def get_name(self):
        """Return player name."""
        return "Random"


class HeuristicPlayer(Player):
    """Player that chooses the move with best immediate board value."""

    def choose_move(self, board):
        """Choose the best one-step move."""
        legal_moves = board.get_legal_moves(self.token, Move)

        if len(legal_moves) == 1 and legal_moves[0].is_pass:
            return legal_moves[0]

        best_move = legal_moves[0]
        best_score = None
        index = 0

        while index < len(legal_moves):
            move = legal_moves[index]

            if not move.is_pass:
                next_board = board.clone()
                next_board.apply_move(move, self.token)
                score = next_board.evaluate_for(self.token)

                if move.mode == "reverse": # Written with LLM's help.
                    score += 1 # The heuristic player has a reverse bias. If it deems that reverse and remove are equally good in any position, it will choose reverse
                                # This is done to help the heuristic come to a decision (without making it too complicated) and avoid infinite games (when Heuristic vs Minimax)
                if best_score is None or score > best_score:
                    best_score = score
                    best_move = move

            index += 1

        return best_move

    def get_name(self):
        """Return player name."""
        return "Heuristic"


class MinimaxPlayer(Player):
    """Depth-limited minimax player with alpha-beta pruning."""

    def __init__(self, token, depth=2):
        """Initialize minimax player."""
        Player.__init__(self, token)
        self.depth = depth

    def choose_move(self, board):
        """Choose the best move using minimax."""
        legal_moves = board.get_legal_moves(self.token, Move)

        if len(legal_moves) == 1 and legal_moves[0].is_pass:
            return legal_moves[0]

        best_move = legal_moves[0]
        best_score = None
        alpha = None # Written with LLM's help.
        beta = None
        index = 0

        while index < len(legal_moves):
            move = legal_moves[index]
            next_board = board.clone()
            next_board.apply_move(move, self.token)

            if move.is_pass:
                next_passes = 1
            else:
                next_passes = 0

            score = self._minimax(
                next_board,
                next_board.opponent(self.token),
                self.depth - 1,
                False,
                next_passes,
                alpha,
                beta
            )

            if best_score is None or score > best_score:
                best_score = score
                best_move = move

            if alpha is None or score > alpha:
                alpha = score

            index += 1

        return best_move

    def _minimax(
        self,
        board,
        current_token,
        depth,
        maximizing,
        consecutive_passes,
        alpha,
        beta
    ):
        """Return minimax score."""
        if depth == 0 or board.game_over(consecutive_passes):
            return board.evaluate_for(self.token)

        legal_moves = board.get_legal_moves(current_token, Move)

        if maximizing:
            best_score = None
            index = 0

            while index < len(legal_moves):
                move = legal_moves[index]
                next_board = board.clone()
                next_board.apply_move(move, current_token)

                if move.is_pass:
                    next_passes = consecutive_passes + 1 # Written with LLM's help.
                else:
                    next_passes = 0

                score = self._minimax(
                    next_board,
                    next_board.opponent(current_token),
                    depth - 1,
                    False,
                    next_passes,
                    alpha,
                    beta
                )

                if best_score is None or score > best_score:
                    best_score = score

                if alpha is None or score > alpha:
                    alpha = score

                if beta is not None and alpha is not None and alpha >= beta:
                    break

                index += 1

            return best_score

        best_score = None
        index = 0

        while index < len(legal_moves):
            move = legal_moves[index]
            next_board = board.clone()
            next_board.apply_move(move, current_token)

            if move.is_pass:
                next_passes = consecutive_passes + 1
            else:
                next_passes = 0

            score = self._minimax(
                next_board,
                next_board.opponent(current_token),
                depth - 1,
                True,
                next_passes,
                alpha,
                beta
            )

            if best_score is None or score < best_score:
                best_score = score

            if beta is None or score < beta:
                beta = score

            if alpha is not None and beta is not None and alpha >= beta:
                break

            index += 1

        return best_score