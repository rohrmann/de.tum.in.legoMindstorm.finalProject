/*
 * IsAccessible.h
 *
 *  Created on: Sep 19, 2010
 *      Author: rohrmann
 */

#ifndef ISACCESSIBLE_H_
#define ISACCESSIBLE_H_

#include "defs.h"
#include "PrintableMap.h"
#include <utility>

class IsAccessible{
private:
	const PrintableMap& map;
public:
	IsAccessible(const PrintableMap& map): map(map){
	}

	bool operator()(std::pair<dimension,dimension> coords) const{
		return this->operator ()(coords.first,coords.second);
	}

	bool operator()(dimension x, dimension y)const{
		if(!map.validCoords(x,y) || map.at(x,y) == UNDEFINED || map.at(x,y)==BOX || map.at(x,y)==WALL)
			return false;

		return true;
	}

	unsigned int addr(std::pair<dimension,dimension> coords)const{
		return map.addr(coords);
	}
};


#endif /* ISACCESSIBLE_H_ */
