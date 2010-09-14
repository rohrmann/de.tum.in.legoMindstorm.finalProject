/*
 * ArrayMap.cpp
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#include "ArrayMap.h"
#include <utility>

Field& ArrayMap::get(int x, int y) throw(InvalidArgumentException){
	if(!validCoords(x,y)){
		throw InvalidArgumentException("ArrayMap::get invalid coords");
	}

	return map[x*dims.second+y];
}

void ArrayMap::set(int x, int y, Field type) throw(InvalidArgumentException){

	if(!validCoords(x,y))
		throw InvalidArgumentException("ArrayMap::set invalid coords");

	map[x*dims.second+y] = type;
}

std::pair<int,int> ArrayMap::dimensions()const{
	return dims;
}

ArrayMap::ArrayMap(){
	map = NULL;
	dims = std::pair<int,int>(0,0);
}

ArrayMap::~ArrayMap(){
	delete [] map;
}

ArrayMap::ArrayMap(Field* map, std::pair<int,int> dims){
	init(map,dims);
}

void ArrayMap::clear(){
	AbstractMap::clear();
	delete [] map;
	map = NULL;
}

void ArrayMap::init(Field* map, std::pair<int,int> dims){
	clear();
	this->map = map;
	this->dims = dims;

	for(int i=0; i< dims.first;i++){
		for(int j =0;j<dims.second;j++){
			switch(at(i,j)){
			case PULLER:
				puller.first= i;
				puller.second = j;
				break;
			case PUSHER:
				pusher.first = i;
				pusher.second = j;
				break;
			case BOX:
				boxes.push_back(std::pair<int,int>(i,j));
				break;
			case TARGET:
				targets.push_back(std::pair<int,int>(i,j));
				break;
			case BOXONTARGET:
				targets.push_back(std::pair<int,int>(i,j));
				boxes.push_back(std::pair<int,int>(i,j));
				break;
			case EMPTY:
			case WALL:
			case UNDEFINED:
			default:
				break;
			}
		}
	}
}

bool ArrayMap::contains(int x, int y) const{
	if(at(x,y) == UNDEFINED)
		return false;

	return true;
}

Field ArrayMap::at(int x, int y) const{
	if(!validCoords(x,y))
		return UNDEFINED;

	return map[x * dims.second + y ];
}

bool ArrayMap::validCoords(int x, int y) const{
	return 0<=x && 0 <= y && x< dims.first && y < dims.second;
}

