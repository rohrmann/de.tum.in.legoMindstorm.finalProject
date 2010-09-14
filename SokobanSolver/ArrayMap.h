/*
 * ArrayMap.hpp
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#ifndef ARRAYMAP_HPP_
#define ARRAYMAP_HPP_

#include "AbstractMap.h"


class ArrayMap : public AbstractMap{
private:
	Field* map;
	std::pair<int,int> dims;
public:
	ArrayMap();
	ArrayMap(Field* map, std::pair<int,int> dimensions);

	~ArrayMap();

	Field at(int x, int y) const;
	Field& get(int x, int y) throw(InvalidArgumentException);
	void set(int x,int y, Field type) throw(InvalidArgumentException);

	std::pair<int,int> dimensions() const ;

	bool contains(int x, int y)const;
	bool validCoords(int x, int y)const;

	void init(Field* map, std::pair<int,int> dims);
	void clear();


	friend class ArrayMapUtils;
};

#endif /* ARRAYMAP_HPP_ */
