/*
 * AbstractGameMap.cpp
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#include "AbstractGameMap.h"
#include "ZobristHashing.h"

AbstractGameMap::AbstractGameMap() {
	hashing= NULL;
	hash = 0;
}

AbstractGameMap::~AbstractGameMap() {
}

void AbstractGameMap::set(int x, int y, Field type) throw(InvalidArgumentException){
	if(!validCoords(x,y))
		throw InvalidArgumentException("AbstractGameMap::set invalid coords");

	if(hashing != NULL){
		hash ^= this->hashing->getField(x,y,at(x,y));
		hash ^= this->hashing->getField(x,y,type);
	}

}
