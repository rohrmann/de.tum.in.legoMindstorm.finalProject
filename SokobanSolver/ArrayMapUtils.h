/*
 * ArrayMapUtils.h
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#ifndef ARRAYMAPUTILS_H_
#define ARRAYMAPUTILS_H_

#include "MapUtils.h"

class AbstractMap;
class ArrayMap;

class ArrayMapUtils: public MapUtils{
public:
	AbstractMap* read( std::istream& input);
};


#endif /* ARRAYMAPUTILS_H_ */
