/*
 * ArrayMapFactory.h
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#ifndef ARRAYMAPFACTORY_H_
#define ARRAYMAPFACTORY_H_

#include "MapFactory.h"
#include "ArrayMap.h"

class ArrayMapFactory : public MapFactory {
public:
	ArrayMap* createMap(const PrintableMap& map);
};

#endif /* ARRAYMAPFACTORY_H_ */
