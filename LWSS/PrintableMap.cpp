/*
 * PrintableMap.cpp
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#include "PrintableMap.h"

PrintableMap::PrintableMap() {
}

PrintableMap::PrintableMap(XSB* map, std::pair<int,int> dims):Map<XSB>(map,dims){
}

PrintableMap::~PrintableMap() {
}
