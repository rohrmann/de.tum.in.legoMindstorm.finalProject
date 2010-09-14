/*
 * abstractMap.hpp
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#ifndef ABSTRACTMAP_HPP_
#define ABSTRACTMAP_HPP_

#include "Field.h"
#include "InvalidArgumentException.h"
#include <utility>
#include <vector>

class AbstractMap{
protected:
	std::pair<int,int> puller;
	std::pair<int,int> pusher;
	std::vector<std::pair<int,int> > boxes;
	std::vector<std::pair<int,int> > targets;
public:

	virtual ~AbstractMap(){};

	virtual Field at(int x, int y) const =0;
	virtual Field& get(int x, int y) throw(InvalidArgumentException)= 0;
	virtual void set(int x, int y, Field type) throw(InvalidArgumentException)= 0;
	virtual std::pair<int,int> dimensions()const = 0;
	virtual bool contains(int x, int y)const = 0;

	virtual bool validCoords(int x, int y) const= 0;

	virtual void clear();

};


#endif /* ABSTRACTMAP_HPP_ */
