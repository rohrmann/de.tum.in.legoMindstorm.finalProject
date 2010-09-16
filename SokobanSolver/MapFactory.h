/*
 * MapFactory.h
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#ifndef MAPFACTORY_H_
#define MAPFACTORY_H_

class AbstractGameMap;
class PrintableMap;

class MapFactory {
public:
	MapFactory();
	virtual ~MapFactory();

	virtual AbstractGameMap * createMap(const PrintableMap& map) =0;
};

#endif /* MAPFACTORY_H_ */
