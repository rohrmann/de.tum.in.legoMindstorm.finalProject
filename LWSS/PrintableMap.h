/*
 * PrintableMap.h
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#ifndef PRINTABLEMAP_H_
#define PRINTABLEMAP_H_

#include "Map.h"
#include "XSB.h"

class PrintableMap : public Map<XSB> {
public:
	PrintableMap();
	PrintableMap(XSB* map, std::pair<int,int> dims);
	virtual ~PrintableMap();
};

#endif /* PRINTABLEMAP_H_ */
