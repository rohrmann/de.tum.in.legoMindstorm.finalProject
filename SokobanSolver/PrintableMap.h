/*
 * PrintableMap.h
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#ifndef PRINTABLEMAP_H_
#define PRINTABLEMAP_H_

#include "AbstractMap.h"

class PrintableMap : public AbstractMap{
private:
	Field* map;
	std::pair<int,int> dims;
public:
	PrintableMap(Field * map, std::pair<int,int> dims);
	virtual ~PrintableMap();

	bool validCoords(int x, int y)const{
		return 0 <= x && 0<=y && x < dims.first && y < dims.second;
	}

	Field at(int x, int y) const{
		if(!validCoords(x,y))
			return UNDEFINED;

		return map[x*dims.second + y];
	}

	Field& get(int x, int y) throw(InvalidArgumentException){
		if(!validCoords(x,y))
			throw InvalidArgumentException("PrintableMap::get invalid coords");

		return map[x*dims.second + y];
	}

	void set(int x, int y, Field field) throw(InvalidArgumentException){
		if(!validCoords(x,y))
			throw InvalidArgumentException("PrintableMap::set invalid coords");

		map[x*dims.second +y] = field;
	}

	bool contains(int x, int y)const {
		if(!validCoords(x,y) || map[x*dims.second + y] == UNDEFINED)
			return false;

		return true;
	}

	std::pair<int,int> dimensions() const{
		return dims;
	}
};

#endif /* PRINTABLEMAP_H_ */
