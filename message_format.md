# Message Format

## General format
use all small letters
separate with whitespace
end message with newline

## Player action (info to server)
Code            Meaning
x t 123\n       Mister X moves to station 123 by taxi
a b 12\n        Player A moves to station 12 by bus
b u 1\n         Player B moves to station 1 by underground
x f 115\n       Mister X moves to station 115 by ferry

where a, b, c, d, e are all players except mister x, and they can be either detectives or bobbies (will be decided when starting the game)

## Server broadcast (info to players)
after each round the players will be presented the state of the game and whether there is a winner yet

Code                    Meaning              
x 115 a 12 b 1 0\n      Mister X is in station 115, Player A in 12,
                        Player B in 1 and the current 'winning state' is 0
                        where 'winning state' is:
                            -1, if the players win,
                            0, if there is no winner yet
                            1, if mister x wins

I also suggest that the server 'remembers' all current and previous states in a file and is able to reproduce them as necessary
This can be achieved using a similar or even the same lines as the messages which are added to the file

## Ideas
all ideas are here for discussion!

