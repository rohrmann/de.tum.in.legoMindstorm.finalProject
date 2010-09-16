/*
 * PrintableMap.cpp
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#include "PrintableMap.h"

PrintableMap::PrintableMap(Field * map, std::pair<int,int> dims) {
	this->map = map;
	this->dims = dims;
}

PrintableMap::~PrintableMap() {
	delete [] map;
}
