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

    class GameEngine {
    public:
        GameEngine() {
            initializeDeck();
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

    private:
        std::vector<Card> deck;
    };

} // namespace solitaire

#endif