/*
 * Solver.cpp
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#include "Solver.h"
#include "GameMap.h"
#include <queue>
#include "Move.h"
#include "HashValueEqual.h"
#include "HashValueHasher.h"
#include "MoveComparator.h"
#include "MapUtils.h"

Solver::Solver() {

}

Solver::~Solver() {
}


void Solver::solve(GameMap* map,const ZobristHashing<HASHLENGTH>& hashing){
	std::unordered_set< HashValue<HASHLENGTH>, HashValueHasher<HASHLENGTH>,HashValueEqual<HASHLENGTH> > pruning;
	std::priority_queue<Move*,std::vector<Move*>, MoveComparator> q;

	Move* startMove= new Move();
	Move* lastMove= startMove;
	q.push(startMove);

	while(!q.empty()){
		Move* nextMove = q.top();
		q.pop();

		Move* ancestor = nextMove->findCommonAncestor(lastMove);
		lastMove->undoMoves(*map,hashing,ancestor);
		nextMove->doMoves(*map,hashing,ancestor);

		if(map->solved()){
			std::cout << "sokoban solved" << std::endl;
			exit(0);
		}

		lastMove = nextMove;

		HashValue<HASHLENGTH> hashValue = map->getHash(hashing);

		if(pruning.find(hashValue) == pruning.end()){
			pruning.insert(hashValue);

			std::vector<Move*> moves = findPossibleMoves(map);

			for(std::vector<Move*>::iterator it = moves.begin(); it != moves.end(); ++it){
				q.push(*it);
			}
		}
	}

	std::cout << "not solved" << std::endl;
}

std::vector<Move*> Solver::findPossibleMoves(GameMap* map){
	return std::vector<Move*>();
}
