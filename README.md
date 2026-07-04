# Solitaire Project Status

This document outlines the current state of the Solitaire project and the remaining tasks.

## Current Status
*   **Architecture:** Hybrid architecture using **Kotlin/Jetpack Compose** for the UI and **C++** for the game engine, connected via **JNI (Native Bridge)**.
*   **Data Models:** `Card` and `GameState` are synchronized between Kotlin and C++. The JNI bridge handles complex data types like nested lists for the tableau and foundations.
*   **Engine:** The C++ `GameEngine` can initialize a deck, shuffle it, and deal the initial 7-column tableau according to standard Solitaire rules.
*   **UI:** Basic rendering of the tableau columns is implemented. A "New Game" button triggers the native engine and updates the Compose state.

---

## What's Left to Do

### 1. Core Game Logic (C++)
*   **Movement Rules:** Implement logic to validate and execute moves (e.g., placing a Red 9 on a Black 10, moving Aces to foundations).
*   **Stock & Waste Management:** Implement the logic for drawing cards from the stock pile to the waste pile.
*   **Win Detection:** Add a check to determine when all cards are moved to the foundations.
*   **Automatic Moves:** (Optional) Logic to automatically move cards to foundations when safe.

### 2. User Interaction (Kotlin/Compose)
*   **Drag and Drop:** Implement `PointerInput` modifiers to allow users to drag cards between columns and piles.
*   **Interactive UI:**
    *   Clicking the **Stock pile** to draw cards.
    *   Double-tapping to move a card to the **Foundation**.
*   **Layout Improvements:**
    *   **Card Stacking:** Modify the tableau columns so cards overlap vertically (offset) instead of sitting in a standard list.
    *   **Full Board:** Add UI components for the Stock, Waste, and the 4 Foundation piles.

### 3. Bridge & State Management
*   **Action Methods:** Add new `external` functions to `NativeBridge` like `moveCard(fromLocation, toLocation)` or `drawCard()`.
*   **ViewModel:** Move the logic out of `MainActivity` and into a `ViewModel` for better separation of concerns and to survive configuration changes.

### 4. Visual Polish
*   **Animations:** Use Compose animations for smooth card movements.
*   **Graphics:** Replace text-based suit icons with vector assets or custom drawing.
*   **Card Backs:** Implement a proper design for face-down cards.

### 5. Persistence
*   **Save/Load:** Implement state persistence to allow users to resume games.
