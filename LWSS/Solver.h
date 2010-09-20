/*
 * Solver.h
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#ifndef SOLVER_H_
#define SOLVER_H_

#include "defs.h"
#include "PairEqual.h"
#include "PairHasher.h"
#include "GameStateComparator.h"
#include "HashValueHasher.h"
#include "HashValueEqual.h"
#include <queue>
#include "ComponentTester.h"


namespace std{
	template<typename T, typename U>
	class pair;
}

template<int L>
class ZobristHashing;

class GameState;
class PrintableMap;

class Solver {
private:
	GameState* initialState;
	GameState* currentState;
	ZobristHashing<HASHLENGTH>* hashing;
	std::priority_queue<GameState*, std::vector<GameState*>, GameStateComparator> queue;
	std::unordered_set< HashValue<HASHLENGTH>, HashValueHasher<HASHLENGTH>,HashValueEqual<HASHLENGTH> > pruning;
	unsigned char connections[256];

	void initConnections();
public:
	Solver(const PrintableMap& map);

	virtual ~Solver();

	void solve();
	unsigned int findNextStates();

	bool insertNewState(GameState* state);

	bool collision(Robot type, point boxStart, point boxEnd,Direction dir);

	void moveBoxNorth(GameState* state,unsigned int boxNum, point boxStart, point boxEnd);

	unsigned int getConnectionIndex(point pos, GameState* state);

	void deleteBranch();

	bool insertState(GameState* state);

};

#endif /* SOLVER_H_ */
