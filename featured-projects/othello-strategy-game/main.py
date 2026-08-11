"""Main simulation file for the R-R assignment."""

from board import Board
from game import Game
from player import HeuristicPlayer
from player import MinimaxPlayer
from player import RandomPlayer


def create_player(player_type, token):
    """Create the correct player object."""
    if player_type == "Random":
        return RandomPlayer(token)
    if player_type == "Heuristic":
        return HeuristicPlayer(token)
    if player_type == "Minimax":
        return MinimaxPlayer(token, 2) # Took depth from this conversation: https://edstem.org/us/courses/95003/discussion/7772205

    raise ValueError("Unknown player type: " + str(player_type)) # This error will mostly never trigger in this code.


def run_matchup(player_a_type, player_b_type, games):
    """Run several games for one matchup."""
    results = {
        "player_a": player_a_type,
        "player_b": player_b_type,
        "player_a_wins": 0,
        "player_b_wins": 0,
        "ties": 0
    }

    first_half = games // 2
    second_half = games - first_half

    game_number = 0
    while game_number < first_half:
        board = Board()
        player_x = create_player(player_a_type, "X")
        player_o = create_player(player_b_type, "O")
        game = Game(board, player_x, player_o)

        winner = game.play()

        if winner == "X":
            results["player_a_wins"] += 1
        elif winner == "O":
            results["player_b_wins"] += 1
        else:
            results["ties"] += 1

        game_number += 1

    game_number = 0
    while game_number < second_half:
        board = Board()
        player_x = create_player(player_b_type, "X")
        player_o = create_player(player_a_type, "O")
        game = Game(board, player_x, player_o)

        winner = game.play()

        if winner == "X":
            results["player_b_wins"] += 1
        elif winner == "O":
            results["player_a_wins"] += 1
        else:
            results["ties"] += 1

        game_number += 1

    results["player_a_ratio"] = results["player_a_wins"] / games
    results["player_b_ratio"] = results["player_b_wins"] / games

    return results


def print_results_table(results_list):
    """Print the final results table."""
    print(
        "Player A".ljust(12) +
        "Player A Win Ratio".ljust(22) + # Written with LLM's help. Wasn't sure about the spacing.
        "Player B".ljust(12) +
        "Player B Win Ratio".ljust(22) +
        "Ties"
    )

    index = 0
    while index < len(results_list):
        result = results_list[index]
        print(
            str(result["player_a"]).ljust(12) +
            ("{:.2f}".format(result["player_a_ratio"])).ljust(22) +
            str(result["player_b"]).ljust(12) +
            ("{:.2f}".format(result["player_b_ratio"])).ljust(22) +
            str(result["ties"])
        )
        index += 1


def main():
    """Run all required matchups."""
    matchups = [
        ("Random", "Heuristic"),
        ("Heuristic", "Minimax"),
        ("Random", "Minimax")
    ]

    results_list = []
    index = 0

    while index < len(matchups):
        player_a_type, player_b_type = matchups[index]
        results = run_matchup(player_a_type, player_b_type, 100)
        results_list.append(results)
        index += 1

    print_results_table(results_list)


if __name__ == "__main__":
    main()