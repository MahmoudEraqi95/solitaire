#include <jni.h>
#include <string>
#include "game_engine.hpp"

// We create a static instance of our engine
static solitaire::GameEngine engine;

extern "C" JNIEXPORT jstring JNICALL
Java_com_eraqi_solitaire_NativeBridge_getEngineMessage(JNIEnv* env, jobject thiz) {
    std::string message = "C++ Engine initialized with " +
                          std::to_string(engine.getCardCount()) +
                          " cards.";
    return env->NewStringUTF(message.c_str());
}

// app/src/main/cpp/native_lib.cpp

extern "C" JNIEXPORT void JNICALL
Java_com_eraqi_solitaire_NativeBridge_shuffleDeck(JNIEnv* env, jobject thiz) {
    engine.shuffleAndDeal();
}


extern "C" JNIEXPORT jobject JNICALL
Java_com_eraqi_solitaire_NativeBridge_getDeck(JNIEnv* env, jobject thiz) {
    // 1. Find the classes we need
    jclass listClass = env->FindClass("java/util/ArrayList");
    jclass cardClass = env->FindClass("com/eraqi/solitaire/Card");

    // 2. Find the constructors
    jmethodID listConstructor = env->GetMethodID(listClass, "<init>", "()V");
    jmethodID listAdd = env->GetMethodID(listClass, "add", "(Ljava/lang/Object;)Z");
    jmethodID cardConstructor = env->GetMethodID(cardClass, "<init>", "(IIZ)V");

    // 3. Create the ArrayList instance
    jobject cardList = env->NewObject(listClass, listConstructor);

    // 4. Get the cards from our C++ engine
    const auto& cards = engine.getCards();

    // 5. Loop and map C++ cards to Kotlin cards
    for (const auto& card : cards) {
        jobject cardObj = env->NewObject(cardClass, cardConstructor,
                                         card.rank, card.suit, card.isFaceUp);
        env->CallBooleanMethod(cardList, listAdd, cardObj);
        env->DeleteLocalRef(cardObj); // Clean up memory
    }

    return cardList;
}

jobject convertToArrayList(JNIEnv* env, const std::vector<solitaire::Card>& cards) {
    jclass listClass = env->FindClass("java/util/ArrayList");
    jmethodID listConstructor = env->GetMethodID(listClass, "<init>", "()V");
    jmethodID listAdd = env->GetMethodID(listClass, "add", "(Ljava/lang/Object;)Z");
    jobject cardList = env->NewObject(listClass, listConstructor);

    jclass cardClass = env->FindClass("com/eraqi/solitaire/Card");
    jmethodID cardConstructor = env->GetMethodID(cardClass, "<init>", "(IIZ)V");

    for (const auto& card : cards) {
        jobject cardObj = env->NewObject(cardClass, cardConstructor, card.rank, card.suit, card.isFaceUp);
        env->CallBooleanMethod(cardList, listAdd, cardObj);
        env->DeleteLocalRef(cardObj);
    }
    return cardList;
}

extern "C" JNIEXPORT jobject JNICALL
Java_com_eraqi_solitaire_NativeBridge_getGameState(JNIEnv* env, jobject thiz) {
    const auto& state = engine.getState();

    // 1. Convert Stock & Waste
    jobject stockList = convertToArrayList(env, state.stock);
    jobject wasteList = convertToArrayList(env, state.waste);

    // 2. Convert Foundations and Tableau (Lists of Lists)
    jclass listClass = env->FindClass("java/util/ArrayList");
    jmethodID listConstructor = env->GetMethodID(listClass, "<init>", "()V");
    jmethodID listAdd = env->GetMethodID(listClass, "add", "(Ljava/lang/Object;)Z");

    jobject foundationsList = env->NewObject(listClass, listConstructor);
    for(int i=0; i<4; i++) {
        jobject p = convertToArrayList(env, state.foundations[i]);
        env->CallBooleanMethod(foundationsList, listAdd, p);
    }

    jobject tableauList = env->NewObject(listClass, listConstructor);
    for(int i=0; i<7; i++) {
        jobject p = convertToArrayList(env, state.tableau[i]);
        env->CallBooleanMethod(tableauList, listAdd, p);
    }

    // 3. Create GameState object
    jclass stateClass = env->FindClass("com/eraqi/solitaire/GameState");
    jmethodID stateConstructor = env->GetMethodID(stateClass, "<init>",
                                                  "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V");

    return env->NewObject(stateClass, stateConstructor, stockList, wasteList, foundationsList, tableauList);
}