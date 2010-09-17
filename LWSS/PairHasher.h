/*
 * PairHasher.h
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#ifndef PAIRHASHER_H_
#define PAIRHASHER_H_


class PairHasher{
public:
	long int operator()(const std::pair<int,int>& a) const{
		unsigned long int hash = 5381;

		for(unsigned int i =0; i< sizeof(int)  ;i++)
			hash = ((hash << 5) + hash) + ((unsigned char*)&a.first)[i];

		for(unsigned int i =0; i< sizeof(int)  ;i++)
					hash = ((hash << 5) + hash) + ((unsigned char*)&a.second)[i];

		return hash;
	}
};

#endif /* PAIRHASHER_H_ */
