/*
 * GameStateComparator.h
 *
 *  Created on: Sep 18, 2010
 *      Author: rohrmann
 */

#ifndef GAMESTATECOMPARATOR_H_
#define GAMESTATECOMPARATOR_H_

#include "GameState.h"

typedef GameState * pGameState;

class GameStateComparator{
public:
	bool operator()(const pGameState& a, const pGameState& b) const {
		return b->getCosts() <= a->getCosts();
	}
};


#endif /* GAMESTATECOMPARATOR_H_ */
