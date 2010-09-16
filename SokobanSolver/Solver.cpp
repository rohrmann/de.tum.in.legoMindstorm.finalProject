/*
 * Solver.cpp
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#include "Solver.h"
#include "AbstractGameMap.h"
#include "Move.h"
#include <vector>
#include <queue>
#include "MoveComparator.h"
#include "StartMove.h"
#include <utility>
#include <iostream>
#include <unordered_set>
#include <bitset>
#include "HashValueHasher.h"
#include "HashValueEqual.h"
#include "defs.h"
#include "MapUtils.h"
#include <stack>
#include <list>

Solver::Solver() {

}

Solver::~Solver() {
}

Move* Solver::solve(AbstractGameMap& map){
	std::priority_queue< Move*,std::vector<Move*>, MoveComparator> q;
	std::unordered_set< HashValue<ZOBRISTHASHLENGTH>, HashValueHasher<ZOBRISTHASHLENGTH>,HashValueEqual<ZOBRISTHASHLENGTH> > pruning;

	Move* startMove = new StartMove();
	Move* lastMove = startMove;

	q.push(startMove);

	int counter = 10;

	while(!q.empty()){
		Move* nextMove = q.top();
		//nextMove->print(std::cout);
		//std::cout << std::endl;
		q.pop();

		Move* ancestor = nextMove->findCommonAncestor(lastMove);
		lastMove->undoMoves(map,ancestor);
		nextMove->doMoves(map,ancestor);

		lastMove = nextMove;

		if(lastMove->getLevel() > counter){
			std::cout << lastMove->getLevel() << std::endl;
			counter += 10;
		}

		//MapUtils::printMap(map,std::cout);

		if(pruning.find(map.getHash()) == pruning.end()){
			pruning.insert(map.getHash());

			if(map.solved()){
				std::cout << "sokoban solved" << std::endl;
				std::stack<Move*> solution;

				Move* current = lastMove;

				while(current != startMove){
					solution.push(current);
					current= current->getPrev();
				}

				lastMove->undoMoves(map,startMove);
				MapUtils::printMap(map,std::cout);
				while(!solution.empty()){
					current = solution.top();
					solution.pop();
					current->doMove(map);
					MapUtils::printMap(map,std::cout);
				}

				exit(0);
			}

			std::list<Move*> pMoves = map.possibleMoves(lastMove);

			for(std::list<Move*>::iterator it= pMoves.begin(); it != pMoves.end();++it){
				q.push(*it);
			}
		}
	}

	std::cout << "not solved" << std::endl;



	return NULL;
}
