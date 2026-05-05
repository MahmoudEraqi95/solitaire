
cmake_minimum_required(VERSION 3.22.1)

project("solitaire")

# Creates and names a library, sets it as either STATIC or SHARED.
add_library(
        solitaire
        SHARED
        native_lib.cpp
        game_engine.hpp
)

# Searches for a specified prebuilt library and stores the path as a variable.
find_library(log-lib log)

# Specifies libraries CMake should link to your target library.
target_link_libraries(
        solitaire
        ${log-lib}
)