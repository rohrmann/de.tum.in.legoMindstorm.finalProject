/*
 * Map.h
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#ifndef MAP_H_
#define MAP_H_

#include <utility>
#include "defs.h"
#include "InvalidArgumentException.h"

template<typename T>
class Map{
public:
	T* map;
	point dims;

	Map();
	Map(T* map, std::pair<dimension,dimension> dims);
	virtual ~Map();

	T at(std::pair<dimension,dimension> coord)const{
		return at(coord.first,coord.second);
	}

	T at(dimension x, dimension y) const;

	bool validCoords(dimension x, dimension y)const;
	bool validCoords(std::pair<dimension,dimension> coords)const{
		return validCoords(coords.first,coords.second);
	}

	unsigned int addr(std::pair<dimension,dimension> coords)const{
		return addr(coords.first,coords.second);
	}
	unsigned int addr(dimension x, dimension y)const{
		return x*dims.second+y;
	}

	void set(dimension x, dimension y, T value){
		map[addr(x,y)] = value;
	}

	void set(point coordinates, T value){
		set(coordinates.first,coordinates.second, value);
	}
};

template<typename T>
Map<T>::Map(){
	map = NULL;
	dims =std::make_pair(0,0);
}

template<typename T>
Map<T>::Map(T* map, std::pair<dimension,dimension> dims){
	this->map = map;
	this->dims = dims;
}

template<typename T>
Map<T>::~Map(){
	delete [] map;
}

template<typename T>
T Map<T>::at(dimension x, dimension y) const{
	if(!validCoords(x,y)){
		throw InvalidArgumentException();
	}

	return map[x*dims.second+y];
}

template<typename T>
bool Map<T>::validCoords(dimension x, dimension y)const{
	return 0 <= x && 0 <= y && y < dims.second && x < dims.first;
}


#endif /* MAP_H_ */
