/*
 * Solver.h
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#ifndef SOLVER_H_
#define SOLVER_H_

#include "defs.h"
#include <unordered_set>
#include <vector>
#include "PairEqual.h"
#include "PairHasher.h"

namespace std{
	template<typename T, typename U>
	class pair;
}

template<int L>
class ZobristHashing;

class Move;
class GameMap;

namespace std{
	template<typename T, typename U>
	class pair;
}

class Solver {
private:
	GameMap* map;
	ZobristHashing<HASHLENGTH> * hashing;
	void insertPath(std::pair<int,int> pos, std::pair<int,int> * prev, std::unordered_set<std::pair<int,int>, PairHasher, PairEqual>& nodes);
public:
	Solver(GameMap * map,ZobristHashing<HASHLENGTH>* hashing) : map(map),hashing(hashing){
	}
	virtual ~Solver();

	void solve();

	void findPossibleMoves(std::vector<Move*, std::allocator<Move*> >& result,Move* lastMove);

	bool checkCollision(unsigned int botNum, const std::unordered_set<std::pair<int,int>, PairHasher, PairEqual>& nodes);

	int calcEstimatedCosts(Move* move);
	int calcEstimatedCosts();

	int getManhattanDist(std::pair<int,int> a, std::pair<int,int> b){
		return getManhattanDist(a.first,a.second,b.first,b.second);
	}

	int getManhattanDist(int a, int b, int x, int y);

	int getManhattanDist(std::pair<int,int> a, int x, int y){
		return getManhattanDist(a.first,a.second,x,y);
	}

	int getManhattanDist(int a, int b, std::pair<int,int> c){
		return getManhattanDist(a,b,c.first,c.second);
	}

	Move* deleteBranch(Move* branch);
};

#endif /* SOLVER_H_ */
