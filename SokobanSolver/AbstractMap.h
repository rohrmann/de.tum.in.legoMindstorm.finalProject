/*
 * abstractMap.hpp
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#ifndef ABSTRACTMAP_HPP_
#define ABSTRACTMAP_HPP_

#include "Field.h"
#include "Robot.h"
#include "InvalidArgumentException.h"
#include <utility>

class AbstractMap{
public:

	virtual ~AbstractMap(){};

	virtual Field at(int x, int y) const =0;
	Field at(std::pair<int,int> coords) {
		return at(coords.first, coords.second);
	}
	virtual Field& get(int x, int y) throw(InvalidArgumentException)= 0;
	Field& get(std::pair<int,int> coords) throw(InvalidArgumentException){
		return get(coords.first,coords.second);
	}

	virtual void set(int x, int y, Field type) throw(InvalidArgumentException)= 0;
	void set(std::pair<int,int> coords, Field type) throw(InvalidArgumentException){
		set(coords.first,coords.second,type);
	}


	virtual std::pair<int,int> dimensions()const = 0;
	virtual bool contains(int x, int y)const = 0;
	bool contains(std::pair<int,int> coords)const{
		return contains(coords.first,coords.second);
	}

	virtual bool validCoords(int x, int y) const= 0;
	bool validCoords(std::pair<int,int> coords) const{
		return validCoords(coords.first,coords.second);
	}
};


#endif /* ABSTRACTMAP_HPP_ */
