"""Fairness analysis test for the R-R assignment.

This file is separate from main.py so it does not affect the original
three-matchup results table.

It focuses only on first-player advantage by running mirror matchups:
- Heuristic vs Heuristic
- Minimax vs Minimax

In these matchups, both players use the same strategy. That means any
consistent difference in results should mainly come from turn order
(first player X vs second player O).
"""

from board import Board
from game import Game
from player import HeuristicPlayer
from player import MinimaxPlayer


def create_same_type_player(player_type, token):
    """Create a Heuristic or Minimax player for fairness analysis."""
    if player_type == "Heuristic":
        return HeuristicPlayer(token)
    if player_type == "Minimax":
        return MinimaxPlayer(token, 2)

    raise ValueError("Unknown player type: " + str(player_type))



def run_same_type_matchup(player_type, games):
    """Run a same-strategy matchup to measure first-player advantage.

    Player X always moves first.
    Player O always moves second.
    """
    results = {
        "matchup": player_type + " vs " + player_type,
        "first_player_wins": 0,
        "second_player_wins": 0,
        "ties": 0
    }

    print("Starting", player_type, "vs", player_type)

    game_number = 0
    while game_number < games:
        board = Board()
        player_x = create_same_type_player(player_type, "X")
        player_o = create_same_type_player(player_type, "O")
        game = Game(board, player_x, player_o)

        winner = game.play()

        if winner == "X":
            results["first_player_wins"] += 1
        elif winner == "O":
            results["second_player_wins"] += 1
        else:
            results["ties"] += 1

        if (game_number + 1) % 10 == 0:
            print("Finished game", game_number + 1, "of", games)

        game_number += 1

    results["first_player_ratio"] = results["first_player_wins"] / games
    results["second_player_ratio"] = results["second_player_wins"] / games

    return results



def print_results_table(results_list):
    """Print the fairness-analysis results table."""
    print(
        "Matchup".ljust(24) +
        "First Player Win Ratio".ljust(24) +
        "Second Player Win Ratio".ljust(25) +
        "Ties"
    )

    index = 0
    while index < len(results_list):
        result = results_list[index]
        print(
            str(result["matchup"]).ljust(24) +
            ("{:.2f}".format(result["first_player_ratio"])).ljust(24) +
            ("{:.2f}".format(result["second_player_ratio"])).ljust(25) +
            str(result["ties"])
        )
        index += 1



def main():
    """Run the fairness-analysis matchups."""
    matchups = ["Heuristic", "Minimax"]

    results_list = []
    index = 0

    while index < len(matchups):
        player_type = matchups[index]
        results = run_same_type_matchup(player_type, 50) # Only doing 50 games as its enough to decide fairness and Minimax vs Minimax takes a long time to fully execute
        results_list.append(results)
        index += 1

    print_results_table(results_list)


if __name__ == "__main__":
    main()
