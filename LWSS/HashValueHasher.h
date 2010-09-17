/*
 * StringHasher.h
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#ifndef STRINGHASHER_H_
#define STRINGHASHER_H_

#include "HashValue.h"

template<int L>
class HashValueHasher{
public:
	long int operator()(const HashValue<L>& a)const{
		unsigned long int hash = 5381;

		for(unsigned int i =0; i< L ;i++)
			hash = ((hash << 5) + hash) + a.at(i);

		return hash;
	}
};


#endif /* STRINGHASHER_H_ */
