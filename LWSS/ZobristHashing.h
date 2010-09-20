/*
 * ZobristHashing.h
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#ifndef ZOBRISTHASHING_H_
#define ZOBRISTHASHING_H_

#include <utility>
#include "Robot.h"
#include "InvalidArgumentException.h"
#include "HashValue.h"
#include "Helper.h"
#include "defs.h"

template<int L>
class ZobristHashing {
private:
	int length;
	HashValue<L>* field;
	HashValue<L>** bot;

	std::pair<dimension,dimension> dims;
	int bots;

	bool validCoords(std::pair<dimension,dimension> coords)const{
		return validCoords(coords.first,coords.second);
	}

	bool validCoords(dimension x, dimension y)const{
		return 0 <= x && 0 <= y && x < dims.first && y < dims.second;
	}
public:
	ZobristHashing(std::pair<dimension,dimension> dims, int bots);
	virtual ~ZobristHashing();

	const HashValue<L>& getBox(std::pair<dimension,dimension> coords)const throw(InvalidArgumentException){
		return getBox(coords.first,coords.second);
	}
	const HashValue<L>& getBox(dimension x, dimension y)const throw(InvalidArgumentException);

	const HashValue<L>& getBot(std::pair<dimension,dimension> coords,unsigned int botNum)const throw(InvalidArgumentException){
		return getBot(coords.first,coords.second,botNum);
	}
	const HashValue<L>& getBot(dimension x, dimension y, unsigned int botNum)const throw(InvalidArgumentException);

	const HashValue<L>& getPuller(std::pair<dimension,dimension> coords) const{
		return getBot(coords.first,coords.second,RPULLER);
	}

	const HashValue<L>& getPuller(dimension x, dimension y) const{
		return getBot(x,y,RPULLER);
	}

	const HashValue<L>& getPusher(std::pair<dimension,dimension> coords) const{
		return getBot(coords.first,coords.second,RPUSHER);
	}

	const HashValue<L>& getPusher(dimension x, dimension y) const{
		return getBot(x,y,RPUSHER);
	}

	static HashValue<L> randHashValue();
};


template<int L>
ZobristHashing<L>::ZobristHashing(std::pair<dimension,dimension> dims, int bots) {
	this->dims = dims;
	this->bots = bots;
	field = new HashValue<L>[dims.first*dims.second];

	for(int i=0;i < dims.first*dims.second;i++){
		field[i] = randHashValue();
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

	delete [] field;


	for(int i =0; i< this->bots;i++){
		delete [] bot[i];
	}

	delete [] bot;
}

template<int L>
const HashValue<L>& ZobristHashing<L>::getBox(dimension x, dimension y) const throw(InvalidArgumentException){
	if(!validCoords(x,y)){
		throw InvalidArgumentException("ZobristHashing::getField invalid coordinates" + Helper::pair2Str(x,y));
	}

	return field[x*dims.second+y];
}

template<int L>
const HashValue<L>& ZobristHashing<L>::getBot(dimension x, dimension y,unsigned int botNum)const throw(InvalidArgumentException){
	if(!validCoords(x,y)){
		throw InvalidArgumentException("ZobristHashing::getBot invalid coordinates" + Helper::pair2Str(x,y));
	}

	return bot[botNum][x*dims.second+y];
}



#endif /* ZOBRISTHASHING_H_ */
