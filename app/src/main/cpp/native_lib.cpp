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