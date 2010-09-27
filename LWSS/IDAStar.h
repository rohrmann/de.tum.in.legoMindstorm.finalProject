/*
 * IDAStar.h
 *
 *  Created on: Sep 26, 2010
 *      Author: rohrmann
 */

#ifndef IDASTAR_H_
#define IDASTAR_H_

#include "defs.h"
#include <unordered_set>
#include <queue>
#include "HashValue.h"
#include "HashValueHasher.h"
#include "HashValueEqual.h"
#include "GameStateComparator.h"

class GameState;
class PrintableMap;

template<int L>
class ZobristHashing;

class IDAStar {
private:
	GameState* initialState;
	ZobristHashing<HASHLENGTH>* hashing;
	std::unordered_set< HashValue<HASHLENGTH>, HashValueHasher<HASHLENGTH>,HashValueEqual<HASHLENGTH> > pruning;

public:
	IDAStar(const PrintableMap& map);

	virtual ~IDAStar();

	void solve( int costs,  int increment);
	void recSolve(GameState* currentState,  int costBoundary);
	unsigned int findNextStates(GameState* currentState,std::priority_queue<GameState*, std::vector<GameState*>, GameStateComparator>& queue);

	bool insertState(GameState* currentState,GameState* state,std::priority_queue<GameState*, std::vector<GameState*>, GameStateComparator>& queue);

	void generateMovements(GameState* currentState,GameState* tempState, const BoxMovement& movement,std::priority_queue<GameState*, std::vector<GameState*>, GameStateComparator>& queue);
};


#endif /* IDASTAR_H_ */
