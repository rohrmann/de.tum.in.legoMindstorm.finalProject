/*
 * ZobristHashing.h
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#ifndef ZOBRISTHASHING_H_
#define ZOBRISTHASHING_H_

#include <bitset>
#include <utility>
#include "Field.h"
#include "Robot.h"
#include "InvalidArgumentException.h"
#include "Helper.h"
#include "HashValue.h"

template<int L>
class ZobristHashing {
private:
	int length;
	HashValue<L>* field[2];
	HashValue<L>** bot;

	std::pair<int,int> dims;
	int bots;

	bool validCoords(std::pair<int,int> coords){
		return 0<= coords.first && 0 <= coords.second && coords.first < dims.first && coords.second < dims.second;
	}
public:
	ZobristHashing(std::pair<int,int> dims, int bots);
	virtual ~ZobristHashing();

	const HashValue<L>& getField(std::pair<int,int> coords, Field type)throw(InvalidArgumentException);
	const HashValue<L>& getField(int x, int y, Field type) throw(InvalidArgumentException){
		return getField(std::make_pair(x,y),type);
	}
	const HashValue<L>& getBot(std::pair<int,int> coords, int botNum)throw(InvalidArgumentException);
	const HashValue<L>& getBot(int x, int y, int botNum) throw(InvalidArgumentException){
		return getBot(std::make_pair(x,y),botNum);
	}

	static HashValue<L> randHashValue();
};


template<int L>
ZobristHashing<L>::ZobristHashing(std::pair<int,int> dims, int bots) {
	this->dims = dims;
	this->bots = bots;
	field[0] = new HashValue<L>[dims.first*dims.second];
	field[1] = new HashValue<L>[dims.first*dims.second];

	for(int i=0;i < dims.first*dims.second;i++){
		field[0][i] = randHashValue();
		field[1][i] = randHashValue();
	}

	bot = new HashValue<L>*[bots];

	for(int i=0;i<bots;i++){
		bot[i] = new HashValue<L>[dims.first*dims.second];

		for(int j =0; j< dims.first*dims.second;j++){
			bot[i][j] = randHashValue();
		}
	}


}

template<int L>
HashValue<L> ZobristHashing<L>::randHashValue(){
	HashValue<L> hash;

	for(int i =0; i< L;i++){
		hash[i] = (char)rand();
	}

	return hash;
}

template<int L>
ZobristHashing<L>::~ZobristHashing() {

	delete [] field[0];
	delete [] field[1];


	for(int i =0; i< this->bots;i++){
		delete [] bot[i];
	}

	delete [] bot;
}

template<int L>
const HashValue<L>& ZobristHashing<L>::getField(std::pair<int,int> coords, Field type) throw(InvalidArgumentException){
	if(!validCoords(coords) || (type != EMPTY && type != BOX)){
		throw InvalidArgumentException("ZobristHashing::getField invalid coords");
	}

	return field[type][coords.first*dims.second + coords.second];
}

template<int L>
const HashValue<L>& ZobristHashing<L>::getBot(std::pair<int,int> coords, int botNum) throw(InvalidArgumentException){
	if(!validCoords(coords)){
		throw InvalidArgumentException("ZobristHashing::getBot invalid coords" + Helper::pair2Str(coords));
	}

	return bot[botNum][coords.first*dims.second + coords.second];
}



#endif /* ZOBRISTHASHING_H_ */
