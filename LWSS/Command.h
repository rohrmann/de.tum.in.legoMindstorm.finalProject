/*
 * Command.h
 *
 *  Created on: Sep 20, 2010
 *      Author: rohrmann
 */

#ifndef COMMAND_H_
#define COMMAND_H_

#include <string>

class GameState;

class Command{
public:
	virtual ~Command();
	virtual void doCommand(GameState * state)=0;
	virtual std::string toString()=0;
};


#endif /* COMMAND_H_ */
