/*
 * StringEqual.h
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#ifndef STRINGEQUAL_H_
#define STRINGEQUAL_H_

#include "HashValue.h"

template<int L>
class HashValueEqual{
public:
	bool operator()(const HashValue<L>& a, const HashValue<L>& b)const{
		for(int i=0;i<L;i++){
			if(a.at(i)!=b.at(i))
				return false;
		}

		return true;
	}
};


#endif /* STRINGEQUAL_H_ */
