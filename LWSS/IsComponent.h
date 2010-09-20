/*
 * IsComponent.h
 *
 *  Created on: Sep 19, 2010
 *      Author: rohrmann
 */

#ifndef ISCOMPONENT_H_
#define ISCOMPONENT_H_

class IsComponent{
private:
	GameState* state;
public:
	IsComponent(GameState* state):state(state){
	}

	bool operator()(std::pair<dimension,dimension> coords) const{
		if(!state->validCoords(coords) || state->at(coords) == 0)
			return false;

		return true;
	}

	unsigned int addr(std::pair<dimension,dimension> coords) const{
		return state->addr(coords);
	}
};


#endif /* ISCOMPONENT_H_ */
