/*
 * Empty.h
 *
 *  Created on: Sep 17, 2010
 *      Author: rohrmann
 */

#ifndef EMPTY_H_
#define EMPTY_H_

#include "Game.h"

class Empty{
public:
	bool operator()(Game game){
		return game == GEMPTY;
	}
};


#endif /* EMPTY_H_ */
