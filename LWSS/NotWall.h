/*
 * NotWall.h
 *
 *  Created on: Sep 17, 2010
 *      Author: rohrmann
 */

#ifndef NOTWALL_H_
#define NOTWALL_H_

#include "Game.h"

class NotWall{
public:
	bool operator()(Game game){
		return game != GWALL;
	}
};

#endif /* NOTWALL_H_ */
