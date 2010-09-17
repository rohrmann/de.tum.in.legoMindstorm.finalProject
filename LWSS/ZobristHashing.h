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
#include "Robot.h"
#include "InvalidArgumentException.h"
#include "HashValue.h"
#include "Helper.h"

template<int L>
class ZobristHashing {
private:
	int length;
	HashValue<L>* field[2];
	HashValue<L>** bot;

	std::pair<int,int> dims;
	int bots;

	bool validCoords(std::pair<int,int> coords)const{
		return validCoords(coords.first,coords.second);
	}

	bool validCoords(int x, int y)const{
		return 0 <= x && 0 <= y && x < dims.first && y < dims.second;
	}
public:
	ZobristHashing(std::pair<int,int> dims, int bots);
	virtual ~ZobristHashing();

	const HashValue<L>& getField(std::pair<int,int> coords, Game gameField)const throw(InvalidArgumentException){
		return getField(coords.first,coords.second,gameField);
	}
	const HashValue<L>& getField(int x, int y, Game gameField)const  throw(InvalidArgumentException);

	const HashValue<L>& getBot(std::pair<int,int> coords, int botNum)const throw(InvalidArgumentException){
		return getBot(coords.first,coords.second,botNum);
	}
	const HashValue<L>& getBot(int x, int y, int botNum)const throw(InvalidArgumentException);

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
const HashValue<L>& ZobristHashing<L>::getField(int x, int y, Game gameField) const throw(InvalidArgumentException){
	if(!validCoords(x,y)){
		throw InvalidArgumentException("ZobristHashing::getField invalid coordinates" + Helper::pair2Str(x,y));
	}

	if(gameField == GWALL){
		throw InvalidArgumentException("ZobristHashing::getField wrong game field");
	}

	return field[gameField][x*dims.second+y];
}

template<int L>
const HashValue<L>& ZobristHashing<L>::getBot(int x, int y, int botNum)const throw(InvalidArgumentException){
	if(!validCoords(x,y)){
		throw InvalidArgumentException("ZobristHashing::getBot invalid coordinates" + Helper::pair2Str(x,y));
	}

	return bot[botNum][x*dims.second+y];
}



#endif /* ZOBRISTHASHING_H_ */
