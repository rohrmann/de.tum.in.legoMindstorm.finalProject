/*
 * GraphMap.hpp
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#ifndef GRAPHMAP_HPP_
#define GRAPHMAP_HPP_

#include "AbstractMap.h"

class GraphMap{
private:
	Field field;
public:
	Field& get(int x, int y) throw(InvalidArgumentException);
	void set(int x, int y, Field type) throw(InvalidArgumentException);
	std::pair<int,int> dimensions();
};

#endif /* GRAPHMAP_HPP_ */
