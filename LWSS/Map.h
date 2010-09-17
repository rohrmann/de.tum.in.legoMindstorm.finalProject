/*
 * Map.h
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#ifndef MAP_H_
#define MAP_H_

#include <utility>
#include "InvalidArgumentException.h"

template<typename T>
class Map{
public:
	T* map;
	std::pair<int,int> dims;

	Map();
	Map(T* map, std::pair<int,int> dims);
	virtual ~Map();
	T at(std::pair<int,int> coord)const{
		return at(coord.first,coord.second);
	}

	T at(int x, int y) const;

	bool validCoords(int x, int y)const;
	bool validCoords(std::pair<int,int> coords)const{
		return validCoords(coords.first,coords.second);
	}

	int addr(std::pair<int,int> coords)const{
		return addr(coords.first,coords.second);
	}
	int addr(int x, int y)const{
		return x*dims.second+y;
	}

	void set(int x, int y, T value){
		map[addr(x,y)] = value;
	}

	void set(std::pair<int,int> coordinates, T value){
		set(coordinates.first,coordinates.second, value);
	}
};

template<typename T>
Map<T>::Map(){
	map = NULL;
	dims =std::make_pair(0,0);
}

template<typename T>
Map<T>::Map(T* map, std::pair<int,int> dims){
	this->map = map;
	this->dims = dims;
}

template<typename T>
Map<T>::~Map(){
	delete [] map;
}

template<typename T>
T Map<T>::at(int x, int y) const{
	if(!validCoords(x,y)){
		throw InvalidArgumentException();
	}

	return map[x*dims.second+y];
}

template<typename T>
bool Map<T>::validCoords(int x, int y)const{
	return 0 <= x && 0 <= y && y < dims.second && x < dims.first;
}


#endif /* MAP_H_ */
