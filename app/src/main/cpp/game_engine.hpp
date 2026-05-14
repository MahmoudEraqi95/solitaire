#ifndef GAME_ENGINE_HPP#define GAME_ENGINE_HPP

#include <vector>
#include <string>
#include <algorithm>
#include <random>

namespace solitaire {

    struct Card {
        int rank;   // 1-13
        int suit;   // 0-3
        bool isFaceUp;
    };
    struct GameState {
        std::vector<Card> stock;
        std::vector<Card> waste;
        std::vector<Card> foundations[4]; // 4 suit piles
        std::vector<Card> tableau[7];    // 7 columns
    };

    class GameEngine {
    public:
        GameEngine() {
            initializeDeck();
            dealNewGame();
        }

        std::vector<Card> &getCards() {
            return deck;
        }
        void shuffleAndDeal() {
            initializeDeck(); // This re-runs the shuffle logic
        }

        void initializeDeck() {
            deck.clear();
            for (int s = 0; s < 4; s++) {
                for (int r = 1; r <= 13; r++) {
                    deck.push_back({r, s, false});
                }
            }
            std::random_device rd;
            std::mt19937 g(rd());
            std::shuffle(deck.begin(), deck.end(), g);
        }

        int getCardCount() const {
            return static_cast<int>(deck.size());
        }
        void dealNewGame() {
            // 1. Create and Shuffle full deck
            std::vector<Card> deck;
            for (int s = 0; s < 4; s++) {
                for (int r = 1; r <= 13; r++) {
                    deck.push_back({r, s, false});
                }
            }
            std::shuffle(deck.begin(), deck.end(), std::mt19937(std::random_device()()));

            // 2. Clear piles
            state = GameState();

            // 3. Deal Tableau (7 columns)
            for (int i = 0; i < 7; i++) {
                for (int j = 0; j <= i; j++) {
                    Card c = deck.back();
                    deck.pop_back();
                    if (j == i) c.isFaceUp = true; // Only top card is face up
                    state.tableau[i].push_back(c);
                }
            }

            // 4. Remaining cards go to Stock
            state.stock = deck;
        }

        const GameState& getState() const { return state; }


    private:
        std::vector<Card> deck;
        GameState state;
    };

} // namespace solitaire

#endif