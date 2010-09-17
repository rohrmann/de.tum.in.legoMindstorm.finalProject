/*
 * MoveComparator.h
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#ifndef MOVECOMPARATOR_H_
#define MOVECOMPARATOR_H_

#include "Move.h"

typedef Move* pMove;

struct MoveComparator {
public:
	bool operator()(const pMove & a, const pMove & b) const{
		return b->getCosts() <= a->getCosts();
	}
};

#endif /* MOVECOMPARATOR_H_ */
