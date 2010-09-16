/*
 * StartMove.h
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#ifndef STARTMOVE_H_
#define STARTMOVE_H_

#include "Move.h"

class StartMove: public Move {
public:

	std::pair<int,int> source()const{
		return std::make_pair(0,0);
	}

	std::pair<int,int> destination()const{
		return std::make_pair(0,0);
	}

	bool doMove(AbstractGameMap& map);
	bool undoMove(AbstractGameMap& map);

	bool changedHash(){
		return false;
	}

	int getBot()const{
		return -1;
	}


};

#endif /* STARTMOVE_H_ */
