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
		long result = 0;

		for(int i =0; i< L;i++){
			result += a.at(i);
		}

		return result;
	}
};


#endif /* STRINGHASHER_H_ */
