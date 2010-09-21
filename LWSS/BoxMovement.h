/*
 * BoxMovement.h
 *
 *  Created on: Sep 21, 2010
 *      Author: rohrmann
 */

#ifndef BOXMOVEMENT_H_
#define BOXMOVEMENT_H_

#include "defs.h"
#include "Robot.h"
#include <string>

class GameState;

class BoxMovement{
public:
	unsigned int boxNum;
	point source;
	Direction dir;
	Robot bot;

	BoxMovement(unsigned int boxNum, point src, Direction dir, Robot bot){
		this->boxNum = boxNum;
		this->source = src;
		this->dir = dir;
		this->bot = bot;
	}

	point src() const;
	point dest() const;
	point destBot() const;
	point srcBot() const;

	void doMove(GameState* state);

	std::string toString();
	std::string toConvertedString(int dim);
};


#endif /* BOXMOVEMENT_H_ */
