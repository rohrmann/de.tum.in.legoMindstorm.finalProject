/*
 * RobotMove.h
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#ifndef ROBOTMOVE_H_
#define ROBOTMOVE_H_

#include "Move.h"
#include "Robot.h"

class RobotMove: public Move {
private:
	std::pair<int,int> src;
	std::pair<int,int> dest;
	Robot type;
	Move* innerMove;
public:
	RobotMove(std::pair<int,int> source, std::pair<int,int> dest, Robot type,Move* innerMove, int addEstimatedCost);
	virtual ~RobotMove();

	std::pair<int,int> source(){
		return src;
	}

	std::pair<int,int> destination(){
		return dest;
	}

	bool changedHash(){
		if(innerMove == NULL)
			return false;
		else
			return innerMove->changedHash();
	}

	bool doMove(AbstractGameMap& map);
	bool undoMove(AbstractGameMap& map);

	int getBot() const{
		return 0;
	}
};

#endif /* ROBOTMOVE_H_ */
