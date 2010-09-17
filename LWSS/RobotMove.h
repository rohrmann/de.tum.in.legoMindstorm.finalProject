/*
 * RobotMove.h
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#ifndef ROBOTMOVE_H_
#define ROBOTMOVE_H_

#include "Move.h"
#include <utility>

class RobotMove: public Move {
public:
	std::pair<int,int> src;
	std::pair<int,int> dest;
	unsigned int botNum;
	Move* innerMove;

	RobotMove(std::pair<int,int> source, std::pair<int,int> dest,unsigned int botNum,Move* innerMove, int addEstimatedCost);
	virtual ~RobotMove();

	bool doMove(GameMap& map,const ZobristHashing<HASHLENGTH>& hashing);
	bool undoMove(GameMap& map,const ZobristHashing<HASHLENGTH>& hashing);
};

#endif /* ROBOTMOVE_H_ */
