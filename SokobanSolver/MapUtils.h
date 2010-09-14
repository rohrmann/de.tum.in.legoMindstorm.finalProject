/*
 * MapUtils.h
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#ifndef MAPUTILS_H_
#define MAPUTILS_H_

#include <iostream>

class AbstractMap;

class MapUtils{
public:
	virtual AbstractMap* read( std::istream& input) = 0;
	virtual bool write(const AbstractMap& map, std::ostream& output);
};


#endif /* MAPUTILS_H_ */
