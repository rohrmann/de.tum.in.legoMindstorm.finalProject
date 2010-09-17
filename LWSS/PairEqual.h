/*
 * PairEqual.h
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#ifndef PAIREQUAL_H_
#define PAIREQUAL_H_

#include <utility>

class PairEqual{
public:
	bool operator()(const std::pair<int,int>& a, const std::pair<int,int>& b) const{
		return a.first == b.first && a.second == b.second;
	}
};


#endif /* PAIREQUAL_H_ */
