/*
 * Solver.h
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#ifndef SOLVER_H_
#define SOLVER_H_

#include "defs.h"
#include <vector>

template<int L>
class ZobristHashing;

class Move;
class GameMap;

class Solver {
public:
	Solver();
	virtual ~Solver();

	void solve(GameMap* initialGameMap,const ZobristHashing<HASHLENGTH>& hashing);

	std::vector<Move*> findPossibleMoves(GameMap* map);
};

#endif /* SOLVER_H_ */
