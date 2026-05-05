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