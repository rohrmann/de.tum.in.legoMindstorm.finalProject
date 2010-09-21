/*
 * PairEqual.h
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#ifndef PAIREQUAL_H_
#define PAIREQUAL_H_

#include "defs.h"

class PointEqual{
public:
	bool operator()(const point& a, const point& b) const{
		return a.first == b.first && a.second == b.second;
	}
};


#endif /* PAIREQUAL_H_ */
