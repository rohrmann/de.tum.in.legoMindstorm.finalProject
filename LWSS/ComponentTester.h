/*
 * ComponentTester.h
 *
 *  Created on: Sep 19, 2010
 *      Author: rohrmann
 */

#ifndef COMPONENTTESTER_H_
#define COMPONENTTESTER_H_

#include "GameState.h"

class ComponentTester{
private:
	GameState* state;
public:
	ComponentTester(GameState* state){
		this->state= state;
	}

	bool operator()(point coords){
		if(!state->validCoords(coords) || state->at(coords)==0){
			return false;
		}

		return true;
	}

	unsigned int addr(point coords){
		return state->addr(coords);
	}
};


#endif /* COMPONENTTESTER_H_ */
