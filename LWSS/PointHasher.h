/*
 * PairHasher.h
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#ifndef PAIRHASHER_H_
#define PAIRHASHER_H_

#include "defs.h"

class PointHasher{
public:
	long int operator()(const point& a) const{
		unsigned long int hash = 5381;

		for(unsigned int i =0; i< sizeof(dimension)  ;i++)
			hash = ((hash << 5) + hash) + ((unsigned char*)&a.first)[i];

		for(unsigned int i =0; i< sizeof(dimension)  ;i++)
					hash = ((hash << 5) + hash) + ((unsigned char*)&a.second)[i];

		return hash;
	}
};

#endif /* PAIRHASHER_H_ */
