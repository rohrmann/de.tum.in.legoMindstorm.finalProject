/*
 * RobotMovement.h
 *
 *  Created on: Sep 20, 2010
 *      Author: rohrmann
 */

#ifndef ROBOTMOVEMENT_H_
#define ROBOTMOVEMENT_H_

#include "defs.h"
#include "Robot.h"
#include <string>
#include <sstream>
#include "Helper.h"

class GameState;

class RobotMovement{
public:
	RobotMovement* next;
	point dest;
	Robot type;

	RobotMovement(Robot type, point dest, RobotMovement * next);
	~RobotMovement();

	point getPuller(point def);
	point getPusher(point def);

	void insert(RobotMovement* movement);

	unsigned int count();

	void doMove(GameState* state);

	std::string toString();
	std::string toConvertedString(int dim);
};



#endif /* ROBOTMOVEMENT_H_ */
