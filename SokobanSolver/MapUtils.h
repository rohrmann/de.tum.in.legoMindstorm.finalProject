/*
 * MapUtils.h
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#ifndef MAPUTILS_H_
#define MAPUTILS_H_

#include <iostream>

class PrintableMap;
class AbstractGameMap;

class MapUtils{
public:
	static PrintableMap* read( std::istream& input);
	static bool write(const PrintableMap& map, std::ostream& output);
	static PrintableMap* convert(const AbstractGameMap& map);
	static void printMap(const AbstractGameMap& map, std::ostream & output);
};


#endif /* MAPUTILS_H_ */
